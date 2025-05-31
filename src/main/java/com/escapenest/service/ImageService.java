package com.escapenest.service;

import com.escapenest.entity.*;
import com.escapenest.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import com.escapenest.utils.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;


@Service
public class ImageService {
    private String uploadDir = "upload_image";
    private final ImageUserRepository imageUserRepository;

    private final UserRepository userRepository;
    private final HttpSession session;
    private final HotelRepository hotelRepository;
    private final ImageHotelRepository imageHotelRepository;
    private final ImageRoomRepository imageRoomRepository;
    private final CityRepository cityRepository;

    public ImageService(ImageUserRepository imageUserRepository, UserRepository userRepository, HttpSession session, HotelRepository hotelRepository, ImageHotelRepository imageHotelRepository, ImageRoomRepository imageRoomRepository, CityRepository cityRepository) {
        this.imageUserRepository = imageUserRepository;
        this.userRepository = userRepository;
        this.session = session;
        this.hotelRepository = hotelRepository;
        this.imageHotelRepository = imageHotelRepository;
        this.imageRoomRepository = imageRoomRepository;
        this.cityRepository = cityRepository;
        FileUtils.createDirectory(uploadDir);
    }

    @Transactional
    public ImageHotel uploadImageHotel(Integer idHotel, MultipartFile multipartFile) {
        Hotel hotel = hotelRepository.findById(idHotel).orElseThrow(() -> new RuntimeException("Không tìm thấy homestay nào có id :" + idHotel));
        // tạo một id image
        String idImage = String.valueOf(System.currentTimeMillis());

        // tìm đường dẫn tới folder lưu ảnh
        Path rootPath = Paths.get(uploadDir).resolve("image_hotel");
        creatFolder(rootPath);
        // tạo đường dẫn file mới với tên là  idImage;
        Path filePath = rootPath.resolve(idImage);
        try {
            // sao chép dữ liệu từ input vào 1 pathfile đã được tạo đường dẫn trước đó
            Files.copy(multipartFile.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Không thể upload file " + idImage);
        }
        ImageHotel imageHotel = new ImageHotel();
        imageHotel.setId(idImage);
        // set tên của file được upload
        imageHotel.setName(multipartFile.getOriginalFilename());
        imageHotel.setType(multipartFile.getContentType());
        imageHotel.setSize(multipartFile.getSize() / 1048576.0);
        imageHotel.setUrl("/" + uploadDir + "/image_hotel/" + idImage);
        imageHotel.setHotel(hotel);
        return imageHotelRepository.save(imageHotel);
    }

    public void creatFolder(Path path) {
        if (!Files.exists(path)) {
            try {
                // tạo thư mục
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException("Không thể tạo được ");
            }
        }
    }

// người dùng tự cập nhật
    public ImageUser uploadImageForUser(MultipartFile file) {

        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng trên "));

        // upload file vào folder
        // tạo một id image
        String idImage = String.valueOf(System.currentTimeMillis());

        // tìm đường dẫn tới folder lưu ảnh
        Path rootPath = Paths.get(uploadDir);

        // tạo đường dẫn file mới với tên là  idImage;
        Path filePath = rootPath.resolve(idImage);


        try {
            // sao chép dữ liệu từ input vào 1 pathfile đã được tạo đường dẫn trước đó
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Không thể upload file " + idImage);
        }

        ImageUser imageUser = new ImageUser();
        imageUser.setId(idImage);
        // set tên của file được upload
        imageUser.setName(file.getOriginalFilename());
        imageUser.setType(file.getContentType());
        imageUser.setSize(file.getSize() / 1048576.0);
        imageUser.setUrl("/" + uploadDir + "/" + idImage);
        imageUser.setUser(user);
        user.setAvatar("/" + uploadDir + "/" + idImage);


        // lấy ra avatar trươc đây người dùng dã lưu, sau đó xóa avt đó ra khỏi folder
        ImageUser imageUserOld = imageUserRepository.findAllByUser_Id(user.getId());
        if (imageUserOld != null) {
            // lấy đường dẫn đến avt được tìm thấy
            Path filePathDelete = Paths.get(uploadDir).resolve(imageUserOld.getId());
            try {
                // xóa file đó đi
                Files.deleteIfExists(filePathDelete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // xóa đối tượng image user khỏi db
            imageUserRepository.delete(imageUserOld);
        }
        return imageUserRepository.save(imageUser);
    }

    // admin cập nhật ảnh cho user
    public ImageUser uploadImageForUser(Integer id, MultipartFile file) {

        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng trên "));

        // upload file vào folder
        // tạo một id image
        String idImage = String.valueOf(System.currentTimeMillis());

        // tìm đường dẫn tới folder lưu ảnh
        Path rootPath = Paths.get(uploadDir);

        // tạo đường dẫn file mới với tên là  idImage;
        Path filePath = rootPath.resolve(idImage);


        try {
            // sao chép dữ liệu từ input vào 1 pathfile đã được tạo đường dẫn trước đó
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Không thể upload file " + idImage);
        }

        ImageUser imageUser = new ImageUser();
        imageUser.setId(idImage);
        // set tên của file được upload
        imageUser.setName(file.getOriginalFilename());
        imageUser.setType(file.getContentType());
        imageUser.setSize(file.getSize() / 1048576.0);
        imageUser.setUrl("/" + uploadDir + "/" + idImage);
        imageUser.setUser(user);
        user.setAvatar("/" + uploadDir + "/" + idImage);


        // lấy ra avatar trươc đây người dùng dã lưu, sau đó xóa avt đó ra khỏi folder
        ImageUser imageUserOld = imageUserRepository.findAllByUser_Id(user.getId());
        if (imageUserOld != null) {
            // lấy đường dẫn đến avt được tìm thấy
            Path filePathDelete = Paths.get(uploadDir).resolve(imageUserOld.getId());
            try {
                // xóa file đó đi
                Files.deleteIfExists(filePathDelete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // xóa đối tượng image user khỏi db
            imageUserRepository.delete(imageUserOld);
        }
        return imageUserRepository.save(imageUser);
    }

    // xóa avatar cho user
    public void deleteImageUser(String id) {
        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy "));
        ImageUser imageUserOld = imageUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy image"));

        if (Objects.equals(user.getId(), imageUserOld.getUser().getId())) {
            Path filePathDelete = Paths.get(uploadDir).resolve(id);

            try {

                Files.deleteIfExists(filePathDelete);
                user.setAvatar("/web/assets/image/avata-default.jpg");
                imageUserRepository.delete(imageUserOld);
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        } else {
            throw new RuntimeException("Bạn không có quyền xóa image này ");
        }
    }


    // lấy image của user hiện tại
    public ImageUser getImageCurrentUser(Integer id) {
        return imageUserRepository.findAllByUser_Id(id);
    }

    // lấy tất cả ảnh của homestay
    public List<ImageHotel> getAllImageByIdHotel(int i) {
        return imageHotelRepository.findAllByHotel_Id(i);
    }

    // xóa ảnh của homestay
    public void deleteImageHotel(String id) {
        ImageHotel imageHotel = imageHotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy image"));
        Path filePathDelete = Paths.get(uploadDir).resolve("image_hotel").resolve(id);
        try {
            Files.deleteIfExists(filePathDelete);
            imageHotelRepository.delete(imageHotel);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

    }

    // tải ảnh lên cho thành phố
    public City uploadImageCity(MultipartFile file, Integer id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thành phố nào có id " + id));
        String idImage = String.valueOf(System.currentTimeMillis());

        Path rootPath = Paths.get(uploadDir).resolve("image_city");
        creatFolder(rootPath);
        Path filePath = rootPath.resolve(idImage);

        if (city.getImageCity() != null) {
            FileUtils.deleteFile(city.getImageCity());
        }
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Không thể upload file " + idImage);
        }
        city.setImageCity("/" + uploadDir + "/image_city/" + idImage);
        return cityRepository.save(city);
    }

    public ImageRoom saveImageRoom(Room room , MultipartFile file) {
        String idImage = String.valueOf(System.currentTimeMillis());
         Path rootPath = Paths.get(uploadDir).resolve("image_room");
         creatFolder(rootPath);
          Path path = rootPath.resolve(idImage);

          try {
              Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
          ImageRoom imageRoom = new ImageRoom();
          imageRoom.setName(file.getOriginalFilename());
          imageRoom.setType(file.getContentType());
          imageRoom.setUrl("/"+uploadDir+"/image_room/"+idImage);
          imageRoom.setSize(file.getSize()/1048576.0);
          imageRoom.setRoom(room);

          imageRoom.setId(idImage);

        return imageRoomRepository.save(imageRoom);
    }

    public Page<ImageRoom> getImageRoomPage(Integer id , Integer limit , Integer page) {
        PageRequest pageRequest = PageRequest.of(page-1,limit, Sort.by("createdAt").ascending());
        return imageRoomRepository.findAllByRoom_Id(id,pageRequest);
    }

    public void deleteImageRoom(String id) {
        ImageRoom imageRoom = imageRoomRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy ảnh nào có id :"+id));
        FileUtils.deleteFile(imageRoom.getUrl());
        imageRoomRepository.delete(imageRoom);
    }

    public void getAllImageRoomDelete(Integer id) {
        List<ImageRoom> imageRoomList = imageRoomRepository.findAllByRoom_Id(id);
        imageRoomRepository.deleteAll(imageRoomList);
    }

    public List<ImageRoom> getAllImageRoomByIdRoom(Integer idRoom) {
        return imageRoomRepository.findAllByRoom_Id(idRoom);
    }
}

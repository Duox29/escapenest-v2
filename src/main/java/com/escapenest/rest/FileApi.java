package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Booking;
import com.escapenest.entity.Hotel;
import com.escapenest.repository.BookingRepository;
import com.escapenest.repository.HotelRepository;
import com.escapenest.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileApi {
    private final FileService fileService;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
//    @GetMapping("/excel")
//    public ResponseEntity<InputStreamResource> excelExport(@RequestParam("id") String id) throws IOException {
//        ByteArrayInputStream in = fileService.exportExcel(id);
//        HttpHeaders headers = new HttpHeaders();
//        var hotel = hotelRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new RuntimeException("Không tìm thấy homestay"));
//        headers.add("Content-Disposition","attachment;filename=bao_cao_hoat_dong_"+hotel.getName()+".xlsx");
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(new InputStreamResource(in));
//    }
@GetMapping("/excel")
public ResponseEntity<InputStreamResource> excelExport(
        @RequestParam("id") String id,
        @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws IOException {

    // Validate date range
    if (startDate.isAfter(endDate)) {
        throw new IllegalArgumentException("Ngày bắt đầu phải trước ngày kết thúc");
    }

    Hotel hotel = hotelRepository.findById(Integer.parseInt(id))
            .orElseThrow(() -> new RuntimeException("Không tìm thấy homestay"));

    // Lấy dữ liệu booking với LocalDate
    List<Booking> bookings = bookingRepository.findByHotelAndDateRange(
            hotel.getId(),
            startDate,
            endDate
    );

    ByteArrayInputStream in = fileService.exportExcel(hotel, bookings);

    HttpHeaders headers = new HttpHeaders();
    String fileName = "bao_cao_hoat_dong_" + hotel.getName() + ".xlsx";
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
            .replaceAll("\\+", "%20");

    headers.add("Content-Disposition",
            "attachment; filename*=UTF-8''" + encodedFileName);

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(new InputStreamResource(in));
}
    @GetMapping("/admin_excel")
    public ResponseEntity<InputStreamResource> AdminExcelExport(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws IOException {

        // Validate date range
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Ngày bắt đầu phải trước ngày kết thúc");
        }

        // Lấy dữ liệu booking với LocalDate
        List<Booking> bookings = bookingRepository.findByDateRange(startDate, endDate);

        ByteArrayInputStream in = fileService.adminExportExcel(bookings);

        HttpHeaders headers = new HttpHeaders();
        String fileName = "bao_cao_hoat_dong_.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                .replaceAll("\\+", "%20");

        headers.add("Content-Disposition",
                "attachment; filename*=UTF-8''" + encodedFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
    @GetMapping("/homestay-excel")
    public ResponseEntity<InputStreamResource> excelExportSingle(@RequestParam("id") String id) throws IOException {
        Hotel hotel = hotelRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy homestay"));

        // Lấy dữ liệu booking với LocalDate
        List<Booking> bookings = bookingRepository.findAllByHotel_Id(hotel.getId());
        ByteArrayInputStream in = fileService.exportExcel(hotel, bookings);

        HttpHeaders headers = new HttpHeaders();
        String fileName = "bao_cao_hoat_dong_" + hotel.getName() + ".xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                .replaceAll("\\+", "%20");

        headers.add("Content-Disposition",
                "attachment; filename*=UTF-8''" + encodedFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

}

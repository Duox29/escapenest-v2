package com.escapenest.service;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Support;
import com.escapenest.model.request.UpsertSupportRequest;
import com.escapenest.model.enums.SupportType;
import com.escapenest.repository.SupportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;


    public List<Support> getSupportByType(String typeSupport) {
        List<Support> supportList = supportRepository.findAllByStatusTrue();
        return supportList.stream()
                .filter(support -> support.getSupportType().getValue().equalsIgnoreCase(typeSupport))
                .toList();
    }

    public List<Support> getAllSupport() {
        return supportRepository.findAll().stream()
                .sorted((item1, item2) -> item2.getCreatedAt().compareTo(item1.getCreatedAt()))
                .toList();
    }

    public Support getSupportById (Integer id) {
        return supportRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy support nào có id : "+ id));
    }

    public Support updateSupport(Integer id, UpsertSupportRequest upsertSupportRequest) {
        Support support =  supportRepository.findById(id).orElseThrow(()-> new RuntimeException("Không có bài viết nào có id " + id));
        SupportType supportType = SupportType.valueOf(upsertSupportRequest.getSupportType());
        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(upsertSupportRequest.getTitle());
        boolean status = upsertSupportRequest.getStatus();
        LocalDate publishedAt = null;
        if (status){
            publishedAt=LocalDate.now();
        }
        support.setSupportType(supportType);
        support.setStatus(status);
        support.setSlug(slug);
        support.setPublishedAt(publishedAt);
        support.setUpdatedAt(LocalDate.now());
        support.setContent(upsertSupportRequest.getContent());
        support.setTitle(upsertSupportRequest.getTitle());
        support.setDescription(upsertSupportRequest.getDescription());

        return supportRepository.save(support);

    }

    public Support createSupport(UpsertSupportRequest upsertSupportRequest) {
        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(upsertSupportRequest.getTitle());
        boolean status = upsertSupportRequest.getStatus();
        SupportType supportType = SupportType.valueOf(upsertSupportRequest.getSupportType());
        LocalDate publishedAt = null;
        if (status){
            publishedAt=LocalDate.now();
        }

        Support support = Support.builder()
                .title(upsertSupportRequest.getTitle())
                .description(upsertSupportRequest.getDescription())
                .slug(slug)
                .content(upsertSupportRequest.getContent())
                .status(status)
                .supportType(supportType)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .publishedAt(publishedAt)
                .build();

        return supportRepository.save(support);

    }

    public void deleteSupport(Integer id) {
        Support support =  supportRepository.findById(id).orElseThrow(()-> new RuntimeException("Không có bài viết nào có id " + id));

        supportRepository.delete(support);

    }
}

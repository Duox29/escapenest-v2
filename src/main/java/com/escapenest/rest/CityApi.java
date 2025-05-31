package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.City;
import com.escapenest.model.request.UpsertCityRequest;
import com.escapenest.service.CityService;
import com.escapenest.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/city")
public class CityApi {
    private final CityService cityService;
    private final ImageService imageService;
    @PostMapping()
    public ResponseEntity<?> createCity(@RequestBody UpsertCityRequest request){
       City city= cityService.createCity(request);
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCity(@RequestBody UpsertCityRequest request, @PathVariable Integer id){
        City city= cityService.updateCity(request,id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Integer id){
        cityService.deleteCity(id);
        return  ResponseEntity.noContent().build();
    }
    @PostMapping("/upload-image/{id}")
    public ResponseEntity<?> changeImageCity(@PathVariable Integer id, @RequestParam("file") MultipartFile file){
       imageService.uploadImageCity(file,id);
        return  ResponseEntity.ok(imageService.uploadImageCity(file,id));
    }
}

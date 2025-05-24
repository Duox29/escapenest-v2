package com.escapenest.repository;

import com.escapenest.entity.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUserRepository extends JpaRepository<ImageUser, String> {
   ImageUser findAllByUser_Id(Integer id);
}
//public interface ImageUserRepository extends JpaRepository<ImageUser, String> {
//    List<ImageUser> findAllByUserId (Integer userId);
//}
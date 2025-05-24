package com.escapenest.repository;

import com.escapenest.entity.PolicyHotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<PolicyHotel,Integer> {


}

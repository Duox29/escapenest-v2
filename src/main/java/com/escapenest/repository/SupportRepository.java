package com.escapenest.repository;

import com.escapenest.entity.Support;
import com.escapenest.model.enums.SupportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support,Integer> {

    List<Support> findBySupportType(SupportType supportTypeValue);
    List<Support> findAllByStatusTrue();
}


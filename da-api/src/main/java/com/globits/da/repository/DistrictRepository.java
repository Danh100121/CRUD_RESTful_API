package com.globits.da.repository;

import com.globits.da.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DistrictRepository extends JpaRepository<District, UUID> {
boolean existsByCode(String code);
District getDistrictsById(UUID id);
}

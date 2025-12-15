package com.alvy.inventory.repository;

import com.alvy.inventory.entity.SupplyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRequestRepository extends JpaRepository<SupplyRequest, Long> {
    List<SupplyRequest> findByStatus(String status);

    List<SupplyRequest> findBySchoolName(String schoolName);

    List<SupplyRequest> findByClinicianEmail(String clinicianEmail);

    List<SupplyRequest> findByProgramType(String programType);

    List<SupplyRequest> findAllByOrderBySubmittedDesc();
}

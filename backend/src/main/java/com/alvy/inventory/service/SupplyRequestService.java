package com.alvy.inventory.service;

import com.alvy.inventory.entity.SupplyRequest;
import com.alvy.inventory.repository.SupplyRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyRequestService {

    private final SupplyRequestRepository repository;

    public List<SupplyRequest> getAllRequests() {
        return repository.findAll();
    }

    public List<SupplyRequest> getAllRequestsSortedByDate() {
        return repository.findAllByOrderBySubmittedDesc();
    }

    public SupplyRequest getRequestById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply request not found with id: " + id));
    }

    @Transactional
    public SupplyRequest createRequest(SupplyRequest request) {
        return repository.save(request);
    }

    @Transactional
    public SupplyRequest submitRequest(Long id) {
        SupplyRequest request = getRequestById(id);
        request.submit();
        return repository.save(request);
    }

    @Transactional
    public SupplyRequest approveRequest(Long id) {
        SupplyRequest request = getRequestById(id);
        request.approve();
        return repository.save(request);
    }

    @Transactional
    public SupplyRequest fulfillRequest(Long id) {
        SupplyRequest request = getRequestById(id);
        request.fulfill();
        return repository.save(request);
    }

    @Transactional
    public SupplyRequest cancelRequest(Long id) {
        SupplyRequest request = getRequestById(id);
        request.cancel();
        return repository.save(request);
    }

    public List<SupplyRequest> getRequestsByStatus(String status) {
        return repository.findByStatus(status);
    }

    public List<SupplyRequest> getRequestsBySchool(String schoolName) {
        return repository.findBySchoolName(schoolName);
    }

    public List<SupplyRequest> getRequestsByClinicianEmail(String email) {
        return repository.findByClinicianEmail(email);
    }

    public List<SupplyRequest> getRequestsByProgramType(String programType) {
        return repository.findByProgramType(programType);
    }

    @Transactional
    public void deleteRequest(Long id) {
        SupplyRequest request = getRequestById(id);
        if (!"PENDING".equals(request.getStatus())) {
            throw new RuntimeException("Can only delete pending requests. Use cancel instead.");
        }
        repository.deleteById(id);
    }
}

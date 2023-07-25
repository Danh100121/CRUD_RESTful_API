package com.globits.da.service;

import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.response.Response;

import java.util.List;
import java.util.UUID;

public interface EmployeeCertificateService {
    Response<EmployeeCertificateDto> add(EmployeeCertificateDto dto);
    Response<EmployeeCertificateDto> update(EmployeeCertificateDto dto, UUID id);
    Response<Boolean> delete(UUID id);
    Response<List<EmployeeCertificateDto>> getAll();
    Response<EmployeeCertificateDto> getById(UUID id);
}

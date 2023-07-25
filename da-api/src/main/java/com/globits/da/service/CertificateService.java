package com.globits.da.service;

import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.response.Response;

import java.util.List;
import java.util.UUID;

public interface CertificateService {
    Response<CertificateDto> add(CertificateDto dto);
    Response<CertificateDto> update(CertificateDto dto, UUID id);
    Response<Boolean> delete(UUID id);
    Response<List<CertificateDto>> getAll();
    Response<CertificateDto> getById(UUID id);
}

package com.globits.da.service.impl;

import com.globits.da.converter.Converter;
import com.globits.da.domain.Employee;
import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.repository.EmployeeCertificateRepository;
import com.globits.da.response.Response;
import com.globits.da.service.EmployeeCertificateService;
import com.globits.da.validate.ResponseStatus;
import com.globits.da.validate.ValidateEmployeeCertificate;
import com.globits.da.validate.ValidateEmployees;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeCertificateServiceImpl implements EmployeeCertificateService {
    private final EmployeeCertificateRepository employeeCertificateRepository;
    private final ValidateEmployeeCertificate validateEmployeeCertificate;
    private final Converter converter;
    @Override
    public Response<EmployeeCertificateDto> add(EmployeeCertificateDto dto) {
        ResponseStatus status = validateEmployeeCertificate.validate(null, dto);
        if (status != ResponseStatus.SUCCESS ){
            return new Response<>(status);
        }
        EmployeeCertificate employeeCertificate = new EmployeeCertificate();
        converter.convertEmployeeCertificateDtoToEntity(dto,employeeCertificate);
        employeeCertificateRepository.save(employeeCertificate);

        return new Response<>(new EmployeeCertificateDto(employeeCertificate));
    }

    @Override
    public Response<EmployeeCertificateDto> update(EmployeeCertificateDto dto, UUID id) {
        ResponseStatus status = validateEmployeeCertificate.validate(id, dto);
        if (status != ResponseStatus.SUCCESS ){
            return new Response<>(status);
        }
        EmployeeCertificate employeeCertificate = employeeCertificateRepository.getOne(id);
        converter.convertEmployeeCertificateDtoToEntity(dto,employeeCertificate);
        employeeCertificateRepository.save(employeeCertificate);

        return new Response<>(new EmployeeCertificateDto(employeeCertificate));
    }

    @Override
    public Response<Boolean> delete(UUID id) {
        if (!employeeCertificateRepository.existsById(id)){
            return new Response<>(false,ResponseStatus.EMPLOYEE_CERTIFICATE_ID_NOT_EXIST) ;
        }
        employeeCertificateRepository.deleteById(id);
        return new Response<>(true, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<List<EmployeeCertificateDto>> getAll() {
        List<EmployeeCertificate> employeeCertificateList = employeeCertificateRepository.findAll();
        List<EmployeeCertificateDto> employeeCertificateDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(employeeCertificateList)){
            for (EmployeeCertificate employeeCertificate : employeeCertificateList){
                employeeCertificateDtos.add(new EmployeeCertificateDto(employeeCertificate));
            }
        }
        return new Response<>(employeeCertificateDtos, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<EmployeeCertificateDto> getById(UUID id) {
        if (!employeeCertificateRepository.existsById(id)){
            return new Response<>(ResponseStatus.EMPLOYEE_CERTIFICATE_ID_NOT_EXIST) ;
        }
        EmployeeCertificate employeeCertificate= employeeCertificateRepository.getOne(id);
        return new Response<>(new EmployeeCertificateDto(employeeCertificate),ResponseStatus.SUCCESS);
    }
}

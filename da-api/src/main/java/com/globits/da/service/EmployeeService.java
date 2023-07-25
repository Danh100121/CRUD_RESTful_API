package com.globits.da.service;

import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.EmployeeSearchDto;
import com.globits.da.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    Response<EmployeeDto> add(EmployeeDto dto);
    Response<EmployeeDto> update(EmployeeDto dto, UUID id);
    Response<Boolean> delete(UUID id);
    Response<List<EmployeeDto>> getAll();
    Response<EmployeeDto> getById(UUID id);
    Page<EmployeeDto> search(EmployeeSearchDto searchDto);
    Response<Object> importExcel(MultipartFile file);

    Response<?> export(HttpServletResponse response);
}

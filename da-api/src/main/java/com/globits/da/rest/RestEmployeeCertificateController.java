package com.globits.da.rest;

import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.response.Response;
import com.globits.da.service.EmployeeCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee-certificates")
public class RestEmployeeCertificateController {
    private final EmployeeCertificateService employeeCertificateService;
    @PostMapping("/add")
    public Response<EmployeeCertificateDto> add(@RequestBody EmployeeCertificateDto dto){
        return employeeCertificateService.add(dto);
    }
    @PutMapping("/{id}")
    public Response<EmployeeCertificateDto> update(@PathVariable UUID id, @RequestBody EmployeeCertificateDto dto){
        return employeeCertificateService.update(dto,id);
    }
    @DeleteMapping("/{id}")
    public Response<Boolean> delete(@PathVariable UUID id){
        return employeeCertificateService.delete(id);
    }
    @GetMapping("/{id}")
    public Response<EmployeeCertificateDto> getById(@PathVariable UUID id){
        return employeeCertificateService.getById(id);
    }
    @GetMapping("/all")
    public Response<List<EmployeeCertificateDto>> getAll(){
        return employeeCertificateService.getAll();
    }
}

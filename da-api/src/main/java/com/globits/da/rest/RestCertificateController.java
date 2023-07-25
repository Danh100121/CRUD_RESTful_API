package com.globits.da.rest;

import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.response.Response;
import com.globits.da.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certificates")
public class RestCertificateController {
    private final CertificateService certificateService;
    @PostMapping("/add")
    public Response<CertificateDto> add(@RequestBody CertificateDto dto){
        return certificateService.add(dto);
    }
    @PutMapping("/{id}")
    public Response<CertificateDto> update(@PathVariable UUID id, @RequestBody CertificateDto dto){
        return certificateService.update(dto,id);
    }
    @DeleteMapping("/{id}")
    public Response<Boolean> delete(@PathVariable UUID id){
        return certificateService.delete(id);
    }
    @GetMapping("/{id}")
    public Response<CertificateDto> getById(@PathVariable UUID id){
        return certificateService.getById(id);
    }
    @GetMapping("/all")
    public Response<List<CertificateDto>> getAll(){
        return certificateService.getAll();
    }
}

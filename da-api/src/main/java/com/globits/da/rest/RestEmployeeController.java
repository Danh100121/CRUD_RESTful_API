package com.globits.da.rest;

import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.EmployeeSearchDto;
import com.globits.da.response.Response;
import com.globits.da.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class RestEmployeeController {
    private final EmployeeService employeeService;
    @PostMapping("/add")
    public Response<EmployeeDto> add(@RequestBody EmployeeDto dto){
        return employeeService.add(dto);
    }
    @PutMapping("/{id}")
    public Response<EmployeeDto> update(@PathVariable UUID id, @RequestBody EmployeeDto dto){
        return employeeService.update(dto,id);
    }
    @DeleteMapping("/{id}")
    public Response<Boolean> delete(@PathVariable UUID id){
        return employeeService.delete(id);
    }
    @GetMapping("/{id}")
    public Response<EmployeeDto> getById(@PathVariable UUID id){
        return employeeService.getById(id);
    }
    @GetMapping("/all")
    public Response<List<EmployeeDto>> getAll(){
        return employeeService.getAll();
    }
    @PostMapping("/search")
    public Page<EmployeeDto> search(@RequestBody EmployeeSearchDto searchDto){
        return employeeService.search(searchDto);
    }
    @PostMapping("/import-from-excel")
    public Response<Object> importFromExcel(@RequestParam("file") MultipartFile file){
        return employeeService.importExcel(file);
    }
    @GetMapping("/export-excel")
    public Response<?> exportExcel(HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employees.xlsx";
        httpServletResponse.setHeader(headerKey, headerValue);

        Response<?> response = employeeService.export(httpServletResponse);
        return response;
    }
}

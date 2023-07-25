package com.globits.da.rest;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.response.Response;
import com.globits.da.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/provinces")
public class RestProvinceController {
   private final ProvinceService provinceService;
   @PostMapping("/add")
    public Response<ProvinceDto> add(@RequestBody ProvinceDto provinceDto){

       return provinceService.add(provinceDto);
   }
   @PutMapping("/{id}")
    public Response<ProvinceDto> update(@PathVariable UUID id , @RequestBody ProvinceDto provinceDto){
       return provinceService.update(provinceDto, id);
   }
   @DeleteMapping("/{id}")
    public Response<Boolean> delete(@PathVariable UUID id){
       return provinceService.delete(id);
   }
   @GetMapping("/{id}")
    public Response<ProvinceDto> getById(@PathVariable UUID id){
       return provinceService.getById(id);
   }
   @GetMapping("/all")
    public Response<List<ProvinceDto>> getAll(){
       return provinceService.getAll();
   }
   @GetMapping("/{id}/districts")
    public Response<List<DistrictDto>> getDistrictsById(@PathVariable UUID id){
       return provinceService.getDistrictsById(id);
   }

}

package com.globits.da.rest;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.response.Response;
import com.globits.da.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/districts")
@RequiredArgsConstructor
public class RestDistrictController {
    private final DistrictService districtService;
    @PostMapping("/add")
    public Response<DistrictDto> add(@RequestBody DistrictDto districtDto){
        return districtService.add(districtDto);
    }
    @PutMapping("/{id}")
    public Response<DistrictDto> update(@PathVariable UUID id , @RequestBody DistrictDto districtDto){
        return districtService.update(districtDto, id);
    }
    @DeleteMapping("/{id}")
    public Response<Boolean> delete(@PathVariable UUID id){
        return districtService.delete(id);
    }
    @GetMapping("/{id}")
    public Response<DistrictDto> getById(@PathVariable UUID id){
        return districtService.getById(id);
    }
    @GetMapping("/all")
    public Response<List<DistrictDto>> getAll(){
        return districtService.getAll();
    }
    @GetMapping("/{id}/communes")
    public Response<List<CommuneDto>> getCommunesById(@PathVariable UUID id){
        return districtService.getCommunesById(id);
    }
}

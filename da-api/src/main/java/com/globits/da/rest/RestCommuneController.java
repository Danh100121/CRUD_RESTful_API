package com.globits.da.rest;

import com.globits.da.dto.CommuneDto;
import com.globits.da.response.Response;
import com.globits.da.service.CommuneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communes")
public class RestCommuneController {
    private final CommuneService communeService;
    @PostMapping("/add")
    public Response<CommuneDto> add(@RequestBody CommuneDto communeDto){
        return communeService.add(communeDto);
    }
    @PutMapping("/{id}")
    public Response<CommuneDto> update(@PathVariable UUID id , @RequestBody CommuneDto communeDto){
        return communeService.update(communeDto, id);
    }
    @DeleteMapping("/{id}")
    public Response<Boolean> delete(@PathVariable UUID id){
        return communeService.delete(id);
    }
    @GetMapping("/{id}")
    public Response<CommuneDto> getById(@PathVariable UUID id){
        return communeService.getById(id);
    }
    @GetMapping("/all")
    public Response<List<CommuneDto>> getAll(){
        return communeService.getAll();
    }

}

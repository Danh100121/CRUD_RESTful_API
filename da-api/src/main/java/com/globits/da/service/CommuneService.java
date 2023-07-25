package com.globits.da.service;

import com.globits.da.dto.CommuneDto;
import com.globits.da.response.Response;

import java.util.List;
import java.util.UUID;

public interface CommuneService {
    Response<CommuneDto> add(CommuneDto dto);
    Response<CommuneDto> update(CommuneDto dto, UUID id);
    Response<Boolean> delete(UUID id);
    Response<List<CommuneDto>> getAll();
    Response<CommuneDto> getById(UUID id);

}

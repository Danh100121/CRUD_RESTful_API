package com.globits.da.service;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.response.Response;

import java.util.List;
import java.util.UUID;

public interface DistrictService {
    Response<DistrictDto> add(DistrictDto dto);
    Response<DistrictDto> update(DistrictDto dto, UUID id);

    Response<Boolean> delete(UUID id);
    Response<List<DistrictDto>> getAll();
    Response<DistrictDto> getById(UUID id);
    Response<List<CommuneDto>> getCommunesById(UUID id);
}

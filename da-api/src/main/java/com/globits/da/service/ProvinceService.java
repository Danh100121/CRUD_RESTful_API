package com.globits.da.service;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.response.Response;

import java.util.List;
import java.util.UUID;

public interface ProvinceService {
    Response<ProvinceDto> add(ProvinceDto dto);
    Response<ProvinceDto> update(ProvinceDto dto, UUID id);
    Response<Boolean> delete(UUID id);
    Response<List<ProvinceDto>> getAll();
    Response<ProvinceDto> getById(UUID id);
    Response<List<DistrictDto>> getDistrictsById(UUID id);
}

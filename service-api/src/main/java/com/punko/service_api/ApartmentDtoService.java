package com.punko.service_api;

import com.punko.dto.ApartmentDto;

import java.util.List;

public interface ApartmentDtoService {

    /**
     * find all Apartment Dto
     * @return List<ApartmentDto>
     */
    List<ApartmentDto> findAllWithAvgTime();
}

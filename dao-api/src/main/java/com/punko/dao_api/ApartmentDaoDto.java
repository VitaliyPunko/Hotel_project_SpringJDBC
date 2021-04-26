package com.punko.dao_api;

import com.punko.dto.ApartmentDto;

import java.util.List;

public interface ApartmentDaoDto {

    /**
     * Get all apartment with avg(arrival_time - departure_time)
     * by apartment
     * @return apartment list
     */
    List<ApartmentDto> findAllWithAvgTime();
}

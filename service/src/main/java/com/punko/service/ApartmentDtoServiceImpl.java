package com.punko.service;

import com.punko.dao_api.ApartmentDaoDto;
import com.punko.dto.ApartmentDto;
import com.punko.service_api.ApartmentDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApartmentDtoServiceImpl implements ApartmentDtoService {

    private final ApartmentDaoDto apartmentDaoDto;

    @Autowired
    public ApartmentDtoServiceImpl(ApartmentDaoDto apartmentDaoDto) {
        this.apartmentDaoDto = apartmentDaoDto;
    }

    @Override
    public List<ApartmentDto> findAllWithAvgTime() {
        return apartmentDaoDto.findAllWithAvgTime();
    }
}

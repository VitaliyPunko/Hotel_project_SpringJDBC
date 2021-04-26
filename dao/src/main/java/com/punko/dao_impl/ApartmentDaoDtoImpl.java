package com.punko.dao_impl;

import com.punko.dao_api.ApartmentDaoDto;
import com.punko.dto.ApartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApartmentDaoDtoImpl implements ApartmentDaoDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentDaoDtoImpl.class);

    @Value("${apartment.dto.getAvgTime}")
    private String findAllAvgTime;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ApartmentDaoDtoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ApartmentDto> findAllWithAvgTime() {
        LOGGER.debug("find all apartments with avgTime");
        return namedParameterJdbcTemplate.query(findAllAvgTime, BeanPropertyRowMapper.newInstance(ApartmentDto.class));
    }
}

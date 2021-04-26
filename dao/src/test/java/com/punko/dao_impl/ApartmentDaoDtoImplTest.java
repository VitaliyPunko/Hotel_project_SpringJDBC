package com.punko.dao_impl;

import com.punko.dao_api.ApartmentDaoDto;
import com.punko.dto.ApartmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
class ApartmentDaoDtoImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentDaoDtoImplTest.class);

    @Autowired
    private ApartmentDaoDto apartmentDaoDto;

    @Test
    void findAllWithAvgTime() {
        LOGGER.debug("should find all dto apartments()");
        List<ApartmentDto> apartmentDtoList = apartmentDaoDto.findAllWithAvgTime();
        Assertions.assertNotNull(apartmentDtoList);
        Assertions.assertTrue(apartmentDtoList.size() > 0);
    }


}
package com.punko.dao_impl;

import com.punko.dao_api.ApartmentDaoDto;
import com.punko.dto.ApartmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
class ApartmentDaoDtoImplTest {

    @Autowired
    private ApartmentDaoDto apartmentDaoDto;

    @Test
    void findAllWithAvgTime() {
        List<ApartmentDto> apartmentDtoList = apartmentDaoDto.findAllWithAvgTime();
        Assertions.assertNotNull(apartmentDtoList);
        Assertions.assertTrue(apartmentDtoList.size() > 0);
    }


}
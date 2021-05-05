package com.punko.service.mock;

import com.punko.dao_api.ApartmentDaoDto;
import com.punko.dto.ApartmentDto;
import com.punko.service.ApartmentDtoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApartmentDtoServiceImplTest {

    //simulated ApartmentDaoDto
    @Mock
    private ApartmentDaoDto apartmentDaoDto;

    //as ApartmentDtoService has a constructor with ApartmentDaoDto
    @InjectMocks
    private ApartmentDtoServiceImpl apartmentDtoService;

    @Test
    public void shouldReturnAllApartmentsWithAvgTime() {
        List<ApartmentDto> apartmentDtoList = new ArrayList<>();

        when(apartmentDaoDto.findAllWithAvgTime()).thenReturn(apartmentDtoList);

        Assertions.assertNotNull(apartmentDtoService.findAllWithAvgTime());
        verify(apartmentDaoDto).findAllWithAvgTime();
    }

}

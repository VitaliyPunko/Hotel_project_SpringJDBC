package com.punko.service.mock;

import com.punko.Apartment;
import com.punko.dao_api.ApartmentDao;
import com.punko.service.ApartmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApartmentServiceImplTest {

    @Mock
    private ApartmentDao apartmentDao;

    @InjectMocks
    private ApartmentServiceImpl apartmentService;

    @Test
    void shouldCallFindAll() {
        List<Apartment> apartmentList = new ArrayList<>();

        when(apartmentDao.findAll()).thenReturn(apartmentList);

        Assertions.assertNotNull(apartmentService.findAll());
        verify(apartmentDao).findAll();
    }

    @Test
    void shouldCallFindById() {
        Apartment apartment = new Apartment(10, "MEDIUM");
        apartment.setApartmentId(10);
        when(apartmentDao.findById(10)).thenReturn(apartment);

        Assertions.assertNotNull(apartmentService.findById(10));
        Assertions.assertEquals(apartment, apartmentService.findById(10));
        verify(apartmentDao, times(2)).findById(anyInt());
    }

    @Test
    void shouldCallCreate() {
        Apartment apartment = new Apartment(10, "MEDIUM");
        when(apartmentDao.create(apartment)).thenReturn(4);

        apartmentService.create(apartment);
        verify(apartmentDao).create(ArgumentMatchers.any(Apartment.class));
    }

    @Test
    void shouldCallUpdate() {
        Apartment apartment = new Apartment(10, "MEDIUM");
        when(apartmentDao.update(apartment)).thenReturn(1);

        apartmentService.update(apartment);

        Assertions.assertEquals(1, apartmentService.update(apartment));
        verify(apartmentDao, times(2)).update(ArgumentMatchers.any(Apartment.class));
    }

    @Test
    void shouldCallCount() {
        apartmentService.count();
        verify(apartmentDao).count();
    }

    @Test
    void shouldCallGetAllApartmentClass() {
        List<String> apartmentClass = new ArrayList<>();

        when(apartmentDao.getAllApartmentClass()).thenReturn(apartmentClass);

        Assertions.assertNotNull(apartmentService.getAllApartmentClass());
        verify(apartmentDao).getAllApartmentClass();
    }
}

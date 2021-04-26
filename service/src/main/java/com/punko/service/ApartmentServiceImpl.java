package com.punko.service;

import com.punko.Apartment;
import com.punko.dao_api.ApartmentDao;
import com.punko.service_api.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentDao apartmentDao;

    @Autowired
    public ApartmentServiceImpl(ApartmentDao apartmentDao) {
        this.apartmentDao = apartmentDao;
    }


    @Override
    public List<Apartment> findAll() {
        return apartmentDao.findAll();
    }

    @Override
    public Apartment findById(Integer apartmentId) {
        return apartmentDao.findById(apartmentId);
    }

    @Override
    public Integer create(Apartment apartment) {
        return apartmentDao.create(apartment);
    }

    @Override
    public Integer update(Apartment apartment) {
        return apartmentDao.update(apartment);
    }

    @Override
    public Integer delete(Integer apartmentId) {
        return apartmentDao.delete(apartmentId);
    }

    @Override
    public Integer count() {
        return apartmentDao.count();
    }

    @Override
    public List<String> getAllApartmentClass() {
        return apartmentDao.getAllApartmentClass();
    }
}

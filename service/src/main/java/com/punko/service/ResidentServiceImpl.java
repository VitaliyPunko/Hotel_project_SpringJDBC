package com.punko.service;

import com.punko.Apartment;
import com.punko.Resident;
import com.punko.dao_api.ResidentDao;
import com.punko.service_api.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {

    private final ResidentDao residentDao;

    @Autowired
    public ResidentServiceImpl(ResidentDao residentDao) {
        this.residentDao = residentDao;
    }

    @Override
    public List<Resident> findAll() {
        return residentDao.findAll();
    }

    @Override
    public void create(Resident resident) {
        residentDao.create(resident);
    }

    @Override
    public List<Apartment> getAllApartmentNumber() {
        return residentDao.getAllApartmentNumber();
    }

    @Override
    public Resident findById(Integer id) {
        return residentDao.findById(id);
    }

    @Override
    public void update(Resident resident) {
        residentDao.updateResident(resident);
    }

    @Override
    public Integer delete(Integer id) {
        return residentDao.delete(id);
    }

    @Override
    public List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime) {
        return residentDao.findAllByTime(arrivalTime, departureTime);
    }

    @Override
    public Integer count() {
        return residentDao.count();
    }

    @Override
    public List<Resident> orderByDate() {
        return residentDao.orderByDate();
    }
}

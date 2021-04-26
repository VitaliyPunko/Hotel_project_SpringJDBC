package com.punko.service_api;

import com.punko.Apartment;
import com.punko.Resident;

import java.time.LocalDate;
import java.util.List;

public interface ResidentService {

    List<Resident> findAll();

    void create(Resident resident);

    List<Apartment> getAllApartmentNumber();

    Resident findById(Integer id);

    void update(Resident resident);

    Integer delete(Integer id);

    List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime);

    Integer count();

    List<Resident> orderByDate();

}

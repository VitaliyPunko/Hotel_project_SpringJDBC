package com.punko.dao_api;

import com.punko.Apartment;

import java.util.List;

public interface ApartmentDao {

    List<Apartment> findAll();

    Apartment findById(Integer apartmentId);

    Integer create(Apartment apartment);

    Integer update(Apartment apartment);

    Integer delete(Integer apartmentId);

    Integer count();

    List<String> getAllApartmentClass();
}

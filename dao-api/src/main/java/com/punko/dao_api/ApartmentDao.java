package com.punko.dao_api;

import com.punko.Apartment;

import java.util.List;

/**
 * ApartmentDao DAO Interface.
 */
public interface ApartmentDao {

    /**
     * Get all apartments with average time between
     * arrival time and departure time.
     * @return apartment list.
     */
    List<Apartment> findAll();

    /**
     * Get apartment by id
     * @param apartmentId apartment id
     * @return apartment.
     */
    Apartment findById(Integer apartmentId);

    /**
     * Create new apartment
     * @param apartment new Apartment
     * @return count of added apartments.
     */
    Integer create(Apartment apartment);

    /**
     * Update apartment
     * @param apartment Apartment
     * @return count of updated apartments.
     */
    Integer update(Apartment apartment);

    /**
     * Delete apartment
     * @param apartmentId apartment id
     * @return count of deleted apartments.
     */
    Integer delete(Integer apartmentId);

    /**
     * Count of all apartments
     * @return count of apartments.
     */
    Integer count();

    /**
     * Get all apartment's classes
     * @return apartment's classes list.
     */
    List<String> getAllApartmentClass();
}

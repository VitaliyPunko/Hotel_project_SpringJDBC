package com.punko.dao_api;

import com.punko.Apartment;
import com.punko.Resident;

import java.time.LocalDate;
import java.util.List;

/**
 * ResidentDao DAO Interface.
 */
public interface ResidentDao {

    /**
     * Get all residents
     * @return resident list.
     */
    List<Resident> findAll();

    /**
     * Create new resident
     * @param resident new Resident.
     */
    void create(Resident resident);

    /**
     * Get all exist apartment's number
     * @return apartment list.
     */
    List<Apartment> getAllApartmentNumber();

    /**
     * Get resident by id
     * @param residentId resident id
     * @return resident.
     */
    Resident findById(Integer residentId);

    /**
     * Update resident
     * @param resident Resident
     */
    void updateResident(Resident resident);

    /**
     * Delete resident
     * @param residentId resident id
     * @return count of deleted residents.
     */
    Integer delete(Integer residentId);

    /**
     * Get residents from arrivalTime to departureTime
     * @param arrivalTime resident arrivalTime
     * @param departureTime resident departureTime
     * @return resident list.
     */
    List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime);

    /**
     * Count of all residents
     * @return count of residents.
     */
    Integer count();

    /**
     * Get residents order by min arrival time
     * @return resident list order by min arrival time.
     */
    List<Resident> orderByDate();
}

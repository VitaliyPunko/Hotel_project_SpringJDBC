package com.punko.dto;

import java.time.LocalDate;

public class ApartmentDto {

    private Integer apartmentId;

    private Integer apartmentNumber;

    private String apartmentClass;

    //should be number?
    /**
     average between arrivalTime
     and departureTime form Resident.class
    */
    private Double averageTime;

    public ApartmentDto() {
    }

    public ApartmentDto(Integer apartmentNumber, String apartmentClass) {
        this.apartmentNumber = apartmentNumber;
        this.apartmentClass = apartmentClass;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getApartmentClass() {
        return apartmentClass;
    }

    public void setApartmentClass(String apartmentClass) {
        this.apartmentClass = apartmentClass;
    }

    public Double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Double averageTime) {
        this.averageTime = averageTime;
    }

    @Override
    public String toString() {
        return "ApartmentDto{" +
                "apartmentId=" + apartmentId +
                ", apartmentNumber=" + apartmentNumber +
                ", apartmentClass='" + apartmentClass + '\'' +
                ", averageTime=" + averageTime +
                '}';
    }
}

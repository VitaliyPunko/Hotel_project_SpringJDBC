package com.punko;

//import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Apartment {

    private Integer apartmentId;

    @Min(value = 1, message = "Apartment number should be more than 0")
    @Max(value = 1000, message = "Apartment number should be less than 1001")
    @NotNull(message = "Apartment number is a required field")
    private Integer apartmentNumber;

    @NotBlank(message = "Apartment class is a required field")
    private String apartmentClass;

    public Apartment() {
    }

    public Apartment(Integer apartmentNumber, String apartmentClass) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return Objects.equals(apartmentId, apartment.apartmentId) && Objects.equals(apartmentNumber, apartment.apartmentNumber) && Objects.equals(apartmentClass, apartment.apartmentClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apartmentId, apartmentNumber, apartmentClass);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentId=" + apartmentId +
                ", apartmentNumber=" + apartmentNumber +
                ", apartmentClass='" + apartmentClass + '\'' +
                '}';
    }
}
DROP TABLE IF EXISTS RESIDENT;
DROP TABLE IF EXISTS APARTMENT;


CREATE TABLE APARTMENT
(
    APARTMENT_ID     INT NOT NULL AUTO_INCREMENT,
    APARTMENT_NUMBER INT NOT NULL UNIQUE,
    APARTMENT_CLASS  VARCHAR(30) NOT NULL,
    CONSTRAINT APARTMENT_PK PRIMARY KEY (APARTMENT_ID)
);

/**
  use ON DELETE SET NULL for FK.
  when delete apartment, apartment_id in Resident table become NULL
  use ON UPDATE CASCADE for update in foreign table
 */
CREATE TABLE RESIDENT
(
    RESIDENT_ID         INT NOT NULL AUTO_INCREMENT,
    FIRSTNAME           VARCHAR(30) NOT NULL,
    LASTNAME            VARCHAR(30) NOT NULL,
    EMAIL               VARCHAR(30) NOT NULL UNIQUE,
    ARRIVAL_TIME        DATE NOT NULL,
    DEPARTURE_TIME      DATE NOT NULL,
    APARTMENT_NUMBER    INT ,
    CONSTRAINT RESIDENT_PK PRIMARY KEY (RESIDENT_ID),
--     cascade ON?
    CONSTRAINT RESIDENT_APARTMENT_FK FOREIGN KEY (APARTMENT_NUMBER) REFERENCES APARTMENT(APARTMENT_NUMBER)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

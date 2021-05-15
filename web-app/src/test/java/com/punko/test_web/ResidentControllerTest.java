package com.punko.test_web;

import com.punko.Resident;
import com.punko.service_api.ResidentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
public class ResidentControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    ResidentService residentService;

    @Captor
    private ArgumentCaptor<Resident> captor;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnResidentsPage() throws Exception {
        Resident resident1 = createResident(1, "Stephen", "King", "stephenking@test.com",
                LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101);
        Resident resident2 = createResident(2, "Margaret", "Mitchell", "margaretmitchell@test.com",
                LocalDate.of(2020, 10, 26),
                LocalDate.of(2021, 4, 10), 102);
        Resident resident3 = createResident(3, "Den", "Brown", "denbrown@test.com",
                LocalDate.of(2021, 2, 13),
                LocalDate.of(2021, 2, 26), 101);
        Resident resident4 = createResident(4, "Erich", "Remark", "remark@test.com",
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 6, 1), 102);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/residents")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("Residents_list"))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(resident1.getResidentId())),
                                hasProperty("firstName", is(resident1.getFirstName())),
                                hasProperty("lastName", is(resident1.getLastName())),
                                hasProperty("email", is(resident1.getEmail())),
                                hasProperty("arrivalTime", is(resident1.getArrivalTime())),
                                hasProperty("departureTime", is(resident1.getDepartureTime())),
                                hasProperty("apartmentNumber", is(resident1.getApartmentNumber()))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(resident2.getResidentId())),
                                hasProperty("firstName", is(resident2.getFirstName())),
                                hasProperty("lastName", is(resident2.getLastName())),
                                hasProperty("email", is(resident2.getEmail())),
                                hasProperty("arrivalTime", is(resident2.getArrivalTime())),
                                hasProperty("departureTime", is(resident2.getDepartureTime())),
                                hasProperty("apartmentNumber", is(resident2.getApartmentNumber()))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(resident3.getResidentId())),
                                hasProperty("firstName", is(resident3.getFirstName())),
                                hasProperty("lastName", is(resident3.getLastName())),
                                hasProperty("email", is(resident3.getEmail())),
                                hasProperty("arrivalTime", is(resident3.getArrivalTime())),
                                hasProperty("departureTime", is(resident3.getDepartureTime())),
                                hasProperty("apartmentNumber", is(resident3.getApartmentNumber()))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(resident4.getResidentId())),
                                hasProperty("firstName", is(resident4.getFirstName())),
                                hasProperty("lastName", is(resident4.getLastName())),
                                hasProperty("email", is(resident4.getEmail())),
                                hasProperty("arrivalTime", is(resident4.getArrivalTime())),
                                hasProperty("departureTime", is(resident4.getDepartureTime())),
                                hasProperty("apartmentNumber", is(resident4.getApartmentNumber()))
                        )
                )))
        ;
    }

    @Test
    public void shouldOpenNewResidentPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/resident")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("Resident"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("residentAttribute", isA(Resident.class)));
    }

    @Test
    public void shouldAddNewResident() throws Exception {
        int countBefore = residentService.count();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/resident")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "Stephen")
                        .param("lastName", "King")
                        .param("email", "someEmailforKing@test.com")
                        .param("arrivalTime", String.valueOf(LocalDate.of(2021, 3, 13)))
                        .param("departureTime", String.valueOf(LocalDate.of(2021, 3, 23)))
                        .param("apartmentNumber", String.valueOf(101))
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/residents"))
                .andExpect(redirectedUrl("/residents"));

        int countAfter = residentService.count();

        Assertions.assertEquals(countBefore + 1, countAfter);
    }

    @Test
    public void shouldAddNewResidentWithWrongParam() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/resident")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("email", "someEmailforKing@test.com")
                        .param("arrivalTime", String.valueOf(LocalDate.of(2021, 3, 13)))
                        .param("departureTime", String.valueOf(LocalDate.of(2021, 3, 23)))
                        .param("apartmentNumber", String.valueOf(101))
        ).andExpect(status().isOk())
                .andExpect(view().name("Resident"));

    }

    @Test
    public void shouldOpenEditResidentPageById() throws Exception {
        Resident resident = createResident(1, "Stephen", "King", "stephenking@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/resident/" + resident.getResidentId())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("Resident"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("residentId", is(resident.getResidentId()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("firstName", is(resident.getFirstName()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("lastName", is(resident.getLastName()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("email", is(resident.getEmail()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("arrivalTime", is(resident.getArrivalTime()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("departureTime", is(resident.getDepartureTime()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("apartmentNumber", is(resident.getApartmentNumber()))));
    }

    @Test
    public void shouldReturnResidentPageIfResidentNotFoundById() throws Exception {
        int id = 99999;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/resident/" + id)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof IllegalArgumentException))
                .andExpect(MockMvcResultMatchers.view().name("errorPage"));

    }

    @Test
    public void shouldUpdateResidentAfterEdit() throws Exception {
        //Resident with id = 1 has firstName = Stephen
        String testName = "Alex";
        mockMvc.perform(
                MockMvcRequestBuilders.post("/resident/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("residentId", "1")
                        .param("firstName", testName)
                        .param("lastName", "King")
                        .param("email", "stephenking@test.com")
                        .param("arrivalTime", String.valueOf(LocalDate.of(2021, 3, 13)))
                        .param("departureTime", String.valueOf(LocalDate.of(2021, 3, 23)))
                        .param("apartmentNumber", String.valueOf(101))
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/residents"))
                .andExpect(redirectedUrl("/residents"));

        Resident resident = residentService.findById(1);
        Assertions.assertEquals(testName, resident.getFirstName());
    }

    @Test
    public void shouldEditResidentWithWrongParam() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/resident/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("residentId", "1")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("email", "someEmailforKing@test.com")
                        .param("arrivalTime", String.valueOf(LocalDate.of(2021, 3, 13)))
                        .param("departureTime", String.valueOf(LocalDate.of(2021, 3, 23)))
                        .param("apartmentNumber", String.valueOf(101))
        ).andExpect(status().isOk())
                .andExpect(view().name("Resident"));

    }

    @Test
    public void shouldDeleteResident() throws Exception {
        int countBefore = residentService.count();
        int id = 1;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/resident/" + id + "/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/residents"))
                .andExpect(redirectedUrl("/residents"));

        int countAfter = residentService.count();
        Assertions.assertEquals(countBefore, countAfter + 1);
    }

    /*
    create this method because of Resident class hasn't id field in constructor
     */
    private Resident createResident(int id, String firstName, String lastName, String email,
                                    LocalDate arrivalTime, LocalDate departureTime, int apartmentNumber) {
        Resident resident = new Resident();
        resident.setResidentId(id);
        resident.setFirstName(firstName);
        resident.setLastName(lastName);
        resident.setEmail(email);
        resident.setArrivalTime(arrivalTime);
        resident.setDepartureTime(departureTime);
        resident.setApartmentNumber(apartmentNumber);
        return resident;
    }
}

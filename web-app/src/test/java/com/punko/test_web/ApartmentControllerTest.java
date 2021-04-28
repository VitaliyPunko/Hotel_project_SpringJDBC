package com.punko.test_web;

import com.punko.Apartment;
import com.punko.dto.ApartmentDto;
import com.punko.service_api.ApartmentDtoService;
import com.punko.service_api.ApartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
public class ApartmentControllerTest {

    @Autowired
    private WebApplicationContext wac;

    //позволяет создать замокированный контекст.
    //т.е. мы не поднимаем все приложение, а оно мокируется.
    // мы можем сконфигурировать на основе applicationContext-a
    // все наше приложение получается смоделированно. И мы тестируем веб-прложение
    private MockMvc mockMvc;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ApartmentDtoService apartmentDtoService;

    @Captor
    private ArgumentCaptor<Apartment> captor;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnApartmentsPage() throws Exception {
        ApartmentDto dto1 = createApartmentDto(1, 101, "LUXURIOUS", 11);
        ApartmentDto dto2 = createApartmentDto(2, 102, "CHEAP", 109);
        ApartmentDto dto3 = createApartmentDto(3, 105, "MEDIUM", null);
        Mockito.when(apartmentDtoService.findAllWithAvgTime()).thenReturn(Arrays.asList(dto1, dto2, dto3));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartments")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartments"))
                //дополнительная проверка по значения
                // с помощью hamcrest
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(dto1.getApartmentId())),
                                hasProperty("apartmentNumber", is(dto1.getApartmentNumber())),
                                hasProperty("apartmentClass", is(dto1.getApartmentClass())),
                                hasProperty("avgDifferenceBetweenTime", is(dto1.getAverageTime()))
                        )
                )))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(dto2.getApartmentId())),
                                hasProperty("apartmentNumber", is(dto2.getApartmentNumber())),
                                hasProperty("apartmentClass", is(dto2.getApartmentClass())),
                                hasProperty("avgDifferenceBetweenTime", is(dto2.getAverageTime()))
                        )
                )))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(dto3.getApartmentId())),
                                hasProperty("apartmentNumber", is(dto3.getApartmentNumber())),
                                hasProperty("apartmentClass", is(dto3.getApartmentClass())),
                                hasProperty("avgDifferenceBetweenTime", isEmptyOrNullString())
                        )
                )))
        ;
    }

    @Test
    public void shouldOpenNewApartmentPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartmentPage"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("apartmentAttribute", isA(Apartment.class)));
        verifyNoMoreInteractions(apartmentService, apartmentDtoService);
    }

    @Test
    public void shouldAddNewApartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("apartmentNumber", String.valueOf(10))
                        .param("apartmentClass", "MEDIUM")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        verify(apartmentService).create(captor.capture());

        Apartment apartment = captor.getValue();
        Assertions.assertEquals(10, apartment.getApartmentNumber());
        Assertions.assertEquals("MEDIUM", apartment.getApartmentClass());
    }

    @Test
    public void shouldOpenEditApartmentPageById() throws Exception {
        Apartment apartment = createApartment(1, 101, "LUXURIOUS");
        when(apartmentService.findById(any())).thenReturn(apartment);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/" + apartment.getApartmentId())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartmentPage"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentId", is(apartment.getApartmentId()))))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentNumber", is(apartment.getApartmentNumber()))))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentClass", is(apartment.getApartmentClass()))));
    }

    @Test
    public void shouldReturnApartmentPageIfApartmentNotFoundById() throws Exception {
        int id = 99999;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/" + id)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/apartments"));
        verify(apartmentService).findById(id);
    }

    @Test
    public void shouldUpdateApartmentAfterEdit() throws Exception {

        String testName = "LUXURIOUS";
        mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("apartmentId", "1")
                        .param("apartmentNumber", "101")
                        .param("apartmentClass", testName)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        verify(apartmentService).update(captor.capture());

        Apartment apartment = captor.getValue();
        Assertions.assertEquals(testName, apartment.getApartmentClass());
    }

    @Test
    public void shouldDeleteApartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        verify(apartmentService).delete(3);
    }

    private Apartment createApartment(int id, int number, String apartmentClass) {
        Apartment apartment = new Apartment();
        apartment.setApartmentId(id);
        apartment.setApartmentNumber(number);
        apartment.setApartmentClass(apartmentClass);
        return apartment;
    }

    private ApartmentDto createApartmentDto(int id, int number, String apartmentClass, Integer avgDiffTime) {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setApartmentId(id);
        apartmentDto.setApartmentNumber(number);
        apartmentDto.setApartmentClass(apartmentClass);
        apartmentDto.setAverageTime(avgDiffTime);
        return apartmentDto;
    }
}

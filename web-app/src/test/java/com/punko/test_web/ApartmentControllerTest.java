package com.punko.test_web;

import com.punko.Apartment;
import com.punko.dto.ApartmentDto;
import com.punko.service_api.ApartmentDtoService;
import com.punko.service_api.ApartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
public class ApartmentControllerTest {

    @Autowired
    private WebApplicationContext wac;

    //    Another useful approach is to not start the server at all but to test only the layer below that,
//    where Spring handles the incoming HTTP request and hands it off to your controller.
//    That way, almost of the full stack is used, and your code will be called in exactly
//    the same way as if it were processing a real HTTP request but without the cost of starting the server.
//    https://spring.io/guides/gs/testing-web/
    private MockMvc mockMvc;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ApartmentDtoService apartmentDtoService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnApartmentsPage() throws Exception {
        ApartmentDto dto1 = createApartmentDto(1, 101, "LUXURIOUS", 12);
        ApartmentDto dto2 = createApartmentDto(2, 102, "CHEAP", 109);
        ApartmentDto dto3 = createApartmentDto(3, 105, "MEDIUM", null);
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
                                hasProperty("averageTime", is(dto1.getAverageTime()))
                        )
                )))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(dto2.getApartmentId())),
                                hasProperty("apartmentNumber", is(dto2.getApartmentNumber())),
                                hasProperty("apartmentClass", is(dto2.getApartmentClass())),
                                hasProperty("averageTime", is(dto2.getAverageTime()))
                        )
                )))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(dto3.getApartmentId())),
                                hasProperty("apartmentNumber", is(dto3.getApartmentNumber())),
                                hasProperty("apartmentClass", is(dto3.getApartmentClass())),
                                hasProperty("averageTime", isEmptyOrNullString())
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
    }

    @Test
    public void shouldAddNewApartment() throws Exception {
        int countBefore = apartmentService.count();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("apartmentNumber", String.valueOf(10))
                        .param("apartmentClass", "MEDIUM")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        int countAfter = apartmentService.count();
        Assertions.assertEquals(countBefore + 1, countAfter);
    }

//TODO fix

//    @Test
//    public void shouldAddingNewApartmentWithWrongParam() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/apartment")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("apartmentNumber", String.valueOf(-10))
//                        .param("apartmentClass", "SomeWord")
//        ).andExpect(status().is3xxRedirection())
//                .andExpect(view().name("/apartmentPage"));
//    }

    @Test
    public void shouldOpenEditApartmentPageById() throws Exception {
        Apartment apartment = createApartment(1, 101, "LUXURIOUS");
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof IllegalArgumentException))
                .andExpect(MockMvcResultMatchers.view().name("errorPage"));
    }

    @Test
    public void shouldUpdateApartmentAfterEdit() throws Exception {
        //Apartment with id = 1 has class = LUXURIOUS
        String testName = "CHEAP";
        mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("apartmentId", "1")
                        .param("apartmentNumber", "101")
                        .param("apartmentClass", testName)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        Apartment apartment = apartmentService.findById(1);
        Assertions.assertEquals(testName, apartment.getApartmentClass());
    }

    @Test
    public void shouldDeleteApartment() throws Exception {
        int countBefore = apartmentService.count();
        int id = 3;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/" + id + "/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        int countAfter = apartmentService.count();
        Assertions.assertEquals(countBefore, countAfter + 1);
    }


    /*
  create this method because of Apartment class hasn't id field in constructor
   */
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

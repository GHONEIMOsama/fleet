package com.example.fleet.controllers;

import com.example.fleet.models.Driver;
import com.example.fleet.repositories.DriverRepository;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverRepository driverRepository;

    private final static String BASE_URL = "/drivers";

    private static UUID driverId;
    private static final UUID BAD_ID = UUID.randomUUID();
    private static final String NON_UUID_ID = "123-456-789";
    private final static String DRIVER_NAME = "driver";
    private final static String DRIVER_NAME2 = "driver2";
    private final static String NAME = "name";

    @Before
    public void setUp() {

        Driver driver = new Driver();
        driver.setName(DRIVER_NAME);
        driver = driverRepository.save(driver);
        driverId = driver.getId();

        Driver driver2 = new Driver();
        driver2.setName(DRIVER_NAME2);
        driverRepository.save(driver2);

    }

    @After
    public void tearDown() {
        driverRepository.deleteAll();
    }

    @Test
    public void findOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s/%s", BASE_URL, driverId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(DRIVER_NAME)));
    }

    @Test
    public void findOneWithUnknownId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s/%s", BASE_URL, BAD_ID)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findAll() throws Exception {
        Collection<String> driverNames = new ArrayList<>();
        driverNames.add(DRIVER_NAME);
        driverNames.add(DRIVER_NAME2);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s", BASE_URL)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(Matchers.in(driverNames))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(Matchers.in(driverNames))));
    }

    @Test
    public void create() throws Exception {
        String requestBody = String.format("{ \"name\": \"%s\" }", NAME);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(NAME)));
    }

    @Test
    public void createWithNoName() throws Exception {
        String requestBody = "{}";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createWithBlankName() throws Exception {
        String requestBody = "{ \"name\": \"  \" }";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        String requestBody = String.format("{ \"name\": \"%s\" }", NAME);
        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("%s/%s", BASE_URL, driverId)).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        driverRepository.findById(driverId).ifPresent(value -> Assert.assertEquals(NAME, value.getName()));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", BASE_URL, driverId)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Assertions.assertFalse(driverRepository.findById(driverId).isPresent());
    }

    @Test
    public void deleteWithNonUUIDId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", BASE_URL, NON_UUID_ID)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

package com.example.teamproject.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private JSONObject testJSONObject = new JSONObject();

    @BeforeEach
    void setUp() throws JSONException {
        testJSONObject.put("name", "testPet");
        testJSONObject.put("id", "1");
        testJSONObject.put("type", "test");
        testJSONObject.put("age", "11");
        testJSONObject.put("description", "description");
    }

    @Test
    void test1CreateAnDGetPet() throws Exception {
        mockMvc.
                perform(
                        post("/pets").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("testPet"))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].type").value("test"))
                .andExpect(jsonPath("$[0].age").value("11"))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    @Test
    void test1CreatePetError() throws Exception {
        mockMvc.
                perform(
                        post("/pets").contentType(MediaType.ALL_VALUE).content("yfyiukbh"))
                .andExpect(status().is(415));
    }

    @Test
    void test4UpdatePet() throws Exception {
        mockMvc.
                perform(
                        post("/pets").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());

        JSONObject testUpdateJSONObject = new JSONObject();
        testUpdateJSONObject.put("name", "Update");
        testUpdateJSONObject.put("id", "1");
        testUpdateJSONObject.put("type", "testUpdate");
        mockMvc.
                perform(
                        put("/pets").contentType(MediaType.APPLICATION_JSON).content(testUpdateJSONObject.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Update"))
                .andExpect(jsonPath("$[0].type").value("testUpdate"));
    }

    @Test
    void test3UpdatePetError() throws Exception {
        mockMvc.
                perform(
                        put("/pets").contentType(MediaType.ALL_VALUE).content("yfyiukbh"))
                .andExpect(status().is(415));
    }

    @Test
    void test3UpdateDatePet() throws Exception {
        mockMvc.
                perform(
                        put("/pets/1/21.01.2023"))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Update"))
                .andExpect(jsonPath("$[0].trialPeriod").value("2023-01-21"));
    }

    @Test
    void test2GetPet() throws Exception {
        mockMvc.
                perform(
                        post("/pets").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/pets/?name=testPet"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("testPet"))
                .andExpect(jsonPath("$[0].type").value("test"))
                .andExpect(jsonPath("$[0].age").value("11"))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    @Test
    void test2GetPetError() throws Exception {
        mockMvc.perform(
                        get("/pets/?name=test"))
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void test5DeletePet() throws Exception {
        mockMvc.perform(
                        delete("/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Update"))
                .andExpect(jsonPath("$.type").value("testUpdate"));
    }

    @Test
    void test5DeletePetError() throws Exception {
        mockMvc.perform(
                        delete("/pets/2"))
                .andExpect(status().is(404));

    }
}

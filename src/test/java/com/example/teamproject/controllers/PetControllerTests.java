package com.example.teamproject.controllers;

import org.json.JSONObject;
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

    @Test
    void testCreatePet() throws Exception {
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("name", "testCreatePet");
        testJSONObject.put("id", "1");
        testJSONObject.put("type", "test");
        testJSONObject.put("age", "11");
        testJSONObject.put("description", "description");
        mockMvc.
                perform(
                        post("/pets").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("testCreatePet"))
                .andExpect(jsonPath("$[0].type").value("test"))
                .andExpect(jsonPath("$[0].age").value("11"))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    @Test
    void testCreatePetError() throws Exception {
        mockMvc.
                perform(
                        post("/pets").contentType(MediaType.ALL_VALUE).content("yfyiukbh"))
                .andExpect(status().is(415));
    }

    @Test
    void testUpdatePet() throws Exception {
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("name", "testCreatePet");
        testJSONObject.put("id", "1");
        testJSONObject.put("type", "test");
        testJSONObject.put("age", "11");
        testJSONObject.put("description", "description");
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
    void testUpdatePetError() throws Exception {
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("name", "testCreatePet");
        testJSONObject.put("id", "1");
        testJSONObject.put("type", "test");
        mockMvc.
                perform(
                        put("/pets").contentType(MediaType.ALL_VALUE).content("yfyiukbh"))
                .andExpect(status().is(415));
    }

//    @Test
//    void testUpdateDatePet() throws Exception {
//        JSONObject testJSONObject = new JSONObject();
//        testJSONObject.put("name", "UpdateDate");
//        testJSONObject.put("trialPeriod", "2023-01-01");
//        mockMvc.
//                perform(
//                        put("/pets/2/21.01.2023"))
//                .andExpect(status().isOk());
//        mockMvc.perform(
//                        get("/pets/?name=UpdateDate"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("UpdateDate"))
//                .andExpect(jsonPath("$[0].trialPeriod").value("2023-01-21"));
//    }

    @Test
    void testGetPet() throws Exception {
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("name", "testGetPet");
        testJSONObject.put("id", "1");
        testJSONObject.put("type", "test");
        testJSONObject.put("age", "11");
        testJSONObject.put("description", "description");
        mockMvc.
                perform(
                        post("/pets").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/pets/?name=testGetPet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("testGetPet"))
                .andExpect(jsonPath("$[0].type").value("test"))
                .andExpect(jsonPath("$[0].age").value("11"))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    @Test
    void testGetPetError() throws Exception {
        mockMvc.perform(
                        get("/pets/?name=test"))
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void testDeletePet() throws Exception {
        JSONObject testJSONObjectForDelete = new JSONObject();
        testJSONObjectForDelete.put("name", "testDeletePet");
        testJSONObjectForDelete.put("id", "1");
        testJSONObjectForDelete.put("type", "test");
        testJSONObjectForDelete.put("age", "11");
        testJSONObjectForDelete.put("description", "description");
        mockMvc.
                perform(
                        post("/pets").contentType(MediaType.APPLICATION_JSON).content(testJSONObjectForDelete.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        delete("/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testDeletePet"))
                .andExpect(jsonPath("$.type").value("test"))
                .andExpect(jsonPath("$.age").value("11"))
                .andExpect(jsonPath("$.description").value("description"));
    }

    @Test
    void testDeletePetError() throws Exception {
        JSONObject testJSONObjectForDelete = new JSONObject();
        testJSONObjectForDelete.put("name", "testDeletePet");
        testJSONObjectForDelete.put("id", "1");
        testJSONObjectForDelete.put("type", "test");
        testJSONObjectForDelete.put("age", "11");
        testJSONObjectForDelete.put("description", "description");

        mockMvc.perform(
                        delete("/pets/1"))
                .andExpect(status().is(404));

    }
}

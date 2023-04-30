package com.example.teamproject.controllers;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test1CreateReport() throws Exception {
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("diet", "testDiet");
        testJSONObject.put("id", "1");
        testJSONObject.put("health", "testHealth");
        testJSONObject.put("behavior", "behaviorHealth");
        mockMvc.
                perform(
                        post("/Reports").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/Reports/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].diet").value("testDiet"))
                .andExpect(jsonPath("$[0].health").value("testHealth"))
                .andExpect(jsonPath("$[0].behavior").value("behaviorHealth"));
    }

    @Test
    void testUpdateReport() throws Exception {
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("diet", "testDiet");
        testJSONObject.put("id", "1");
        testJSONObject.put("health", "testHealth");
        testJSONObject.put("behavior", "behaviorHealth");
        mockMvc.
                perform(
                        post("/Reports").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        JSONObject testJSONObject2 = new JSONObject();
        testJSONObject2.put("diet", "testDiet2");
        testJSONObject2.put("id", "1");
        testJSONObject2.put("health", "testHealth2");
        testJSONObject2.put("behavior", "behaviorHealth2");
        mockMvc.
                perform(
                        put("/Reports").contentType(MediaType.APPLICATION_JSON).content(testJSONObject2.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/Reports/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].diet").value("testDiet2"))
                .andExpect(jsonPath("$[0].health").value("testHealth2"))
                .andExpect(jsonPath("$[0].behavior").value("behaviorHealth2"));
    }

    @Test
    void testCreateReportError() throws Exception {
        mockMvc.
                perform(
                        post("/Reports").contentType(MediaType.ALL_VALUE).content("8787887вы"))
                .andExpect(status().is(415));
    }

    @Test
    void test2GetReport() throws Exception {
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("diet", "testDiet1");
        testJSONObject.put("id", "1");
        testJSONObject.put("health", "testHealth1");
        testJSONObject.put("behavior", "behaviorHealth1");
        mockMvc.
                perform(
                        post("/Reports").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/Reports/test/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diet").value("testDiet1"))
                .andExpect(jsonPath("$.health").value("testHealth1"))
                .andExpect(jsonPath("$.behavior").value("behaviorHealth1"));
    }


    @Test
    void testDeleteReport() throws Exception {
        JSONObject testJSONObjectForDelete = new JSONObject();
        testJSONObjectForDelete.put("diet", "testDiet");
        testJSONObjectForDelete.put("id", "1");
        testJSONObjectForDelete.put("health", "testHealth");
        testJSONObjectForDelete.put("behavior", "behaviorHealth");
        mockMvc.
                perform(
                        post("/Reports").contentType(MediaType.APPLICATION_JSON).content(testJSONObjectForDelete.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        delete("/Reports/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diet").value("testDiet"))
                .andExpect(jsonPath("$.health").value("testHealth"))
                .andExpect(jsonPath("$.behavior").value("behaviorHealth"));
    }

    @Test
    void testDeleteReportError() throws Exception {
        JSONObject testJSONObjectForDelete = new JSONObject();
        testJSONObjectForDelete.put("diet", "testDiet");
        testJSONObjectForDelete.put("id", "1");
        testJSONObjectForDelete.put("health", "testHealth");
        testJSONObjectForDelete.put("behavior", "behaviorHealth");

        mockMvc.perform(
                        delete("/Reports/1"))
                .andExpect(status().is(404));

    }
}

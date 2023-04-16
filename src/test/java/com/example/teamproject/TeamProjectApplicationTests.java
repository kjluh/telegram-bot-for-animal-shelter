package com.example.teamproject;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeamProjectApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void testCreatePet() throws Exception{
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("name", "testPet");
        testJSONObject.put("id", "1");
        mockMvc.
                perform(
                post("/load_pet").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                get("/get_pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$.name").value("testPet"));
    }



}

package com.example.teamproject;

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
class TeamProjectApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads(){
    }

    @Test
    void testCreatePet() throws Exception{
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("name", "testCreatePet");
        testJSONObject.put("id", "1");
        testJSONObject.put("type", "test");
        testJSONObject.put("age", "11");
        testJSONObject.put("description", "description");
        mockMvc.
                perform(
                        post("/teamProject8").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                get("/teamProject8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("testCreatePet"))
                .andExpect(jsonPath("$[0].type").value("test"))
                .andExpect(jsonPath("$[0].age").value("11"))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    @Test
    void testGetPet() throws Exception{
        JSONObject testJSONObject = new JSONObject();
        testJSONObject.put("name", "testGetPet");
        testJSONObject.put("id", "1");
        testJSONObject.put("type", "test");
        testJSONObject.put("age", "11");
        testJSONObject.put("description", "description");
        mockMvc.
                perform(
                        post("/teamProject8").contentType(MediaType.APPLICATION_JSON).content(testJSONObject.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/teamProject8/?name=testGetPet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("testGetPet"))
                .andExpect(jsonPath("$[0].type").value("test"))
                .andExpect(jsonPath("$[0].age").value("11"))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    @Test
    void testDeletePet() throws Exception{
        JSONObject testJSONObjectForDelete = new JSONObject();
        testJSONObjectForDelete.put("name", "testDeletePet");
        testJSONObjectForDelete.put("id", "1");
        testJSONObjectForDelete.put("type", "test");
        testJSONObjectForDelete.put("age", "11");
        testJSONObjectForDelete.put("description", "description");
        mockMvc.
                perform(
                        post("/teamProject8").contentType(MediaType.APPLICATION_JSON).content(testJSONObjectForDelete.toString()))
                .andExpect(status().isOk());
        mockMvc.perform(
                        delete("/teamProject8/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testDeletePet"))
                .andExpect(jsonPath("$.type").value("test"))
                .andExpect(jsonPath("$.age").value("11"))
                .andExpect(jsonPath("$.description").value("description"));
    }
}

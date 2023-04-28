package com.example.teamproject.controllers;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.service.AdoptiveParentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdoptiveParentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private AdoptiveParent adoptiveParent = new AdoptiveParent();
    private JSONObject adoptiveParentJSON = new JSONObject();
    private Long chatId;
    private String name;
    private String messageText;
    private String phoneNumber;


    @MockBean
    private AdoptiveParentService adoptiveParentServiceMock;

    @BeforeEach
    void setUp() throws JSONException {
        chatId = 123L;
        name = "Ben";
        messageText = "qwerty";
        phoneNumber = "89000000000";

        adoptiveParent.setChatId(chatId);
        adoptiveParent.setName(name);
        adoptiveParent.setMessage(messageText);
        adoptiveParent.setPhoneNumber(Long.parseLong(phoneNumber));

        adoptiveParentJSON.put("chatId", chatId);
        adoptiveParentJSON.put("name", name);
        adoptiveParentJSON.put("message", messageText);
        adoptiveParentJSON.put("phoneNumber", phoneNumber);
    }

    @Test
    public void saveAdoptiveParentTest() throws Exception {
        when(adoptiveParentServiceMock.save(any(AdoptiveParent.class))).thenReturn(adoptiveParent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adoptive_parents")
                        .content(adoptiveParentJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.message").value(messageText))
                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber));
    }

    @Test
    public void getAllAdoptiveParentsTest() throws Exception {
        ArrayList<AdoptiveParent> adoptiveParentArrayList = new ArrayList<>();
        adoptiveParentArrayList.add(adoptiveParent);
        adoptiveParentArrayList.add(adoptiveParent);

        when(adoptiveParentServiceMock.findAll()).thenReturn(adoptiveParentArrayList);

        mockMvc.perform(
                get("/adoptive_parents/get_all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(adoptiveParentArrayList)));

    }

    @Test
    public void getAdoptiveParentByIdTest() throws Exception {
        when(adoptiveParentServiceMock.findAdoptiveParentById(any(Long.class))).thenReturn(adoptiveParent);

        mockMvc.perform(
                        get("/adoptive_parents/get_by_id?id=123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.message").value(messageText))
                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber));

    }

    @Test
    public void updateAdoptiveParentTest() throws Exception {
        when(adoptiveParentServiceMock.updateAdoptiveParent(any(AdoptiveParent.class))).thenReturn(adoptiveParent);

        mockMvc.perform(
                        put("/adoptive_parents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptiveParentJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.message").value(messageText))
                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber));
    }

    @Test
    public void deleteAdoptiveParentById() throws Exception {
        mockMvc.perform(
                        delete("/adoptive_parents/123"))
                .andExpect(status().isOk());

    }
}

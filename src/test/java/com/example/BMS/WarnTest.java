package com.example.BMS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class WarnTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWarn() throws Exception {
        String jsonString = "[\n" +
                "        {\n" +
                "            \"carId\": 1,\n" +
                "                \"warnId\": 1,\n" +
                "                \"signal\": \"{\\\"Mx\\\":12.0,\\\"Mi\\\":0.6}\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"carId\": 2,\n" +
                "                \"warnId\": 2,\n" +
                "                \"signal\": \"{\\\"Ix\\\":12.0,\\\"Ii\\\":11.7}\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"carId\": 3,\n" +
                "                \"signal\": \"{\\\"Mx\\\":11.0,\\\"Mi\\\":9.6,\\\"Ix\\\":12.0,\\\"Ii\\\":11.7}\"\n" +
                "        }\n" +
                "]";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/warn")
                        .contentType("application/json")
                        .content(jsonString)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

package com.ewd.flightstatus.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ewd.flightstatus.configurations.EntityNotFoundException;
import com.ewd.flightstatus.entity.FlightStatus;
import com.ewd.flightstatus.service.FlightStatusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class FlightStatusControllerTest
{
    private MockMvc mockMvc;

    @Mock
    private FlightStatusService flightStatusService;

    @InjectMocks
    private FlightStatusController controller;

    FlightStatus mocksts;

    @Before
    public void initialize()
    {
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());

        String exampleStsJson =
            "{\"airline\":{\"name\":\"Qantas\",\"iata\":\"QF\",\"icao\":\"QFA\"},\"flight\":{\"number\":\"5671\",\"iata\":\"QF5671\",\"icao\":\"QFA5671\"},\"departure\":{\"airport\":\"Darwin\",\"timezone\":\"Australia/Darwin\",\"iata\":\"DRW\",\"icao\":\"YPDN\",\"scheduled\":\"2022-11-17T00:10:00+00:00\",\"atual\":null,\"delay\":50},\"arrival\":{\"airport\":\"Kingsford Smith\",\"timezone\":\"Australia/Sydney\",\"iata\":\"SYD\",\"icao\":\"YSSY\",\"scheduled\":\"2022-11-17T06:10:00+00:00\",\"atual\":null,\"delay\":0},\"flight_date\":\"2022-11-17\",\"flight_status\":\"scheduled\"}";
        try {
            mocksts = new ObjectMapper().readValue(exampleStsJson, FlightStatus.class);
        } catch (JsonProcessingException e) {
            log.error("Not able to parse the json");
        }
    }

    @Test
    public void givenAvailableFlightNumberwithDt_whengetFlightStatus_then200Ok() throws Exception
    {

        Mockito.when(flightStatusService.getFlightStatus(Mockito.anyString(), Mockito.any())).thenReturn(mocksts);

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.get("/api/v1/5671/2022-11-17/flightStatus").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected =
            "{\"airline\":{\"name\":\"Qantas\",\"iata\":\"QF\",\"icao\":\"QFA\"},\"flight\":{\"number\":\"5671\",\"iata\":\"QF5671\",\"icao\":\"QFA5671\"},\"departure\":{\"airport\":\"Darwin\",\"timezone\":\"Australia/Darwin\",\"iata\":\"DRW\",\"icao\":\"YPDN\",\"scheduled\":\"2022-11-17T00:10:00+00:00\",\"atual\":null,\"delay\":50},\"arrival\":{\"airport\":\"Kingsford Smith\",\"timezone\":\"Australia/Sydney\",\"iata\":\"SYD\",\"icao\":\"YSSY\",\"scheduled\":\"2022-11-17T06:10:00+00:00\",\"atual\":null,\"delay\":0},\"flight_date\":\"2022-11-17\",\"flight_status\":\"scheduled\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void givenNonAvailableFlightNumberwithDt_whengetFlightStatus_then404NotFound() throws Exception
    {

        Mockito.when(flightStatusService.getFlightStatus(Mockito.anyString(), Mockito.any()))
            .thenThrow(new EntityNotFoundException(FlightStatus.class, "5671", "2022-11-17"));

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.get("/api/v1/716/2022-11-17/flightStatus").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        assertEquals(404, result.getResponse().getStatus());
    }
}

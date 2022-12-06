package com.ewd.flightstatus.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FlightStatusServiceTest
{

    @Autowired
    FlightStatusService flightStatusService;

    @Test
    void givenAvailableFlightNumberwithDt_whengetFlightStatus_thenFound() throws ParseException
    {
        Date travelDate = constructTravelDt("2022-11-17");
        assertNotNull(flightStatusService.getFlightStatus("5671", travelDate));
    }

    private Date constructTravelDt(String date) throws ParseException
    {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date travelDate = simpleDateFormat.parse(date);
        return travelDate;
    }

    @Test
    void givenNonAvailableFlightNumberwithDt_whengetFlightStatus_thenNotFound() throws ParseException
    {
        Date travelDate = constructTravelDt("2022-11-11");
        Exception exception =
            assertThrows(RuntimeException.class, () -> flightStatusService.getFlightStatus("56", travelDate));
        assertTrue(exception.getMessage().contains("FlightStatus was not found for parameters 56, 2022-11-11"));

    }
    
    @Test
    void givenAvailableFlightNumberwithNonDt_whengetFlightStatus_thenNotFound() throws ParseException
    {
        Date travelDate = constructTravelDt("2022-11-11");
        Exception exception =
            assertThrows(RuntimeException.class, () -> flightStatusService.getFlightStatus("5671", travelDate));
        assertTrue(exception.getMessage().contains("FlightStatus was not found for parameters 5671, 2022-11-11"));

    }

}

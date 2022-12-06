package com.ewd.flightstatus.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightStatus implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4369666101553812238L;

    @JsonProperty("flight_date")
    private String flightDate;

    @JsonProperty("flight_status")
    private String status;

    private Airline airline;

    private Flight flight;

    private Departure departure;

    private Arrival arrival;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static class Airline implements Serializable
    {
        /**
         * 
         */
        private static final long serialVersionUID = -8512289018130416552L;

        private String name;

        private String iata;

        private String icao;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static class Flight implements Serializable
    {
        /**
         * 
         */
        private static final long serialVersionUID = 578214728597644951L;

        private String number;

        private String iata;

        private String icao;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static class Departure implements Serializable

    {

        /**
         * 
         */
        private static final long serialVersionUID = 8034662268165210604L;

        private String airport;

        private String timezone;

        private String iata;

        private String icao;

        private String scheduled;

        private String atual;

        private int delay;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static class Arrival implements Serializable
    {

        /**
         * 
         */
        private static final long serialVersionUID = 3377759267777482895L;

        private String airport;

        private String timezone;

        private String iata;

        private String icao;

        private String scheduled;

        private String atual;

        private int delay;
    }

}

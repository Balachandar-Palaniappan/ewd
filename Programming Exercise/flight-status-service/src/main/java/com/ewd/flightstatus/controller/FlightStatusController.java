package com.ewd.flightstatus.controller;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ewd.flightstatus.configurations.EntityNotFoundException;
import com.ewd.flightstatus.entity.FlightStatus;
import com.ewd.flightstatus.service.FlightStatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api")
@Tag(name = "FlightStatusController", description = "Get flight status")
public class FlightStatusController
{

    @Autowired
    private FlightStatusService flightStsSservice;

    @GetMapping(value = "/v1/{flightNumber}/{travelDate}/flightStatus")
    @Operation(summary = "Get flight status details by flight number with travel date ", responses = {
        @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public FlightStatus geteQosAttributes(
        @PathVariable @NotBlank(message = "Invalid flight number") @Min(value = 2, message = "Invalid flight number") @Size(max = 10) String flightNumber,
        @PathVariable @NotBlank(message = "Invalid Date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date travelDate)
        throws EntityNotFoundException
    {
        return flightStsSservice.getFlightStatus(flightNumber, travelDate);
    }
}

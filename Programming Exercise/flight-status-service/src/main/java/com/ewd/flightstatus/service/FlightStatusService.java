package com.ewd.flightstatus.service;

import java.util.Date;
import java.util.Objects;

import javax.cache.integration.CacheLoaderException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.ewd.flightstatus.configurations.Constants;
import com.ewd.flightstatus.configurations.EntityNotFoundException;
import com.ewd.flightstatus.configurations.Utils;
import com.ewd.flightstatus.entity.FlightStatus;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlightStatusService {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private Utils utils;

	public FlightStatus getFlightStatus(String flightNumber, Date travelDate) throws EntityNotFoundException {
		String travelDateAsString = utils.convertISODateToStringFormat(travelDate);
		// Access cache instance by name
		Cache cache = cacheManager.getCache(Constants.CAHCE_NAME);
		if (Objects.isNull(cache)) {
			throw new CacheLoaderException(Constants.CAHCE_NAME);
		}
		return findFlightStatusFromCache(flightNumber, travelDateAsString, cache);
	}

	private FlightStatus findFlightStatusFromCache(String flightNumber, String travelDateAsString, Cache cache) {
		FlightStatus flightSts = cache.get(flightNumber, FlightStatus.class);
		log.debug("flightSts Detail: -->{}", flightSts);
		if (isFlightStatusNotAvailable(travelDateAsString, flightSts)) {
			log.info("Not able to find flight status Details for{} and {} ", flightNumber, travelDateAsString);
			throw new EntityNotFoundException(FlightStatus.class, flightNumber, travelDateAsString);
		}
		log.info("Found flight status Details for{} and {} ", flightNumber, travelDateAsString);
		return flightSts;
	}

	private boolean isFlightStatusNotAvailable(String travelDateAsString, FlightStatus flightSts) {
		return (Objects.isNull(flightSts) || !flightSts.getFlightDate().equalsIgnoreCase(travelDateAsString));
	}
}

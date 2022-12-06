package com.ewd.flightstatus.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.ewd.flightstatus.configurations.Constants;
import com.ewd.flightstatus.configurations.Utils;
import com.ewd.flightstatus.entity.FlightStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoadSampleDataService implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private Utils utils;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// Sample file to load the data into cache.
		// It will be used for the application
		Resource resource = resourceLoader.getResource("classpath:FormattedSampleFlightData.txt");
		try {
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
			// Access cache instance by name
			Cache cache = cacheManager.getCache(Constants.CAHCE_NAME);
			if (Objects.nonNull(cache)) {
				List<FlightStatus> flightStss = new ObjectMapper().readValue(data,
						new TypeReference<List<FlightStatus>>() {
						});
				log.debug("flightSts Data: -->{}", flightStss.size());
				flightStss.stream().forEach(flightSts -> {
					String flightDate = utils.convertISODateToStringFormat(new Date());
					flightSts.setFlightDate(flightDate);
					cache.put(flightSts.getFlight().getNumber(), flightSts);
				});
			}
		} catch (IOException e) {
			log.error("IOException", e);
		}

	}

}

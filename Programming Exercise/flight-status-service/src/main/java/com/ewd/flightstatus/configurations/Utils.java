package com.ewd.flightstatus.configurations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	public String convertISODateToStringFormat(Date travelDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(travelDate);
	}
}

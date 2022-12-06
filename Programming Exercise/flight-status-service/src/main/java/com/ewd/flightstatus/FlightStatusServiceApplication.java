package com.ewd.flightstatus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@Configuration
@EnableCaching
public class FlightStatusServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(FlightStatusServiceApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion)
    {
        return new OpenAPI().info(new Info().title("Flight Status Service Rest Api").version(appVersion)
            .description("Rest api of flight status service").contact((new Contact().name("EurowingsDigital")))
            .license(new License().name("MIT")));
    }
}

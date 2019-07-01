package com.ml.solarsystem.config;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.solarsystem.dtos.SolarSystemDTO;

public class SolarSystemDefaultConfig {

	private static final Logger log = LoggerFactory.getLogger(SolarSystemDefaultConfig.class);
	
	private SolarSystemDTO solarSystemDto;
	private static SolarSystemDefaultConfig instance;

	static {
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream input = null;
		try {
			Resource resource = new ClassPathResource("solar-system-default-config.json");
			input = resource.getInputStream();
			SolarSystemDTO solarSystemDTO = objectMapper.readValue(input, SolarSystemDTO.class);
			instance = new SolarSystemDefaultConfig();
			instance.solarSystemDto = solarSystemDTO;
		} catch (IOException e) {
			log.error("Default configuration could not be loaded: {}", e.getMessage());
		}finally{
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error("Fail to close input stream: {}", e.getMessage());
				}
			}
		}
	}
	
	private SolarSystemDefaultConfig() {
	}	
	
	public static final SolarSystemDefaultConfig getInstance() {
		return instance;
	}

	public SolarSystemDTO getSolarSystemDto() {
		return solarSystemDto;
	}

}

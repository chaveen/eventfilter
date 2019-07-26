package org.devchavez.eventfilter;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@org.springframework.context.annotation.Configuration
public class Configuration {
	
	@Bean(name = "jsonMapper")
	public ObjectMapper getJSONMapper() {
		ObjectMapper mapper = new ObjectMapper();

		this.setCommonMapperConfig(mapper);

		return mapper;
	}
	
	@Bean(name = "csvMapper")
	public CsvMapper getCSVMapper() {
		CsvMapper mapper = new CsvMapper();
		
		this.setCommonMapperConfig(mapper);

		return mapper;
	}
	
	@Bean(name = "xmlMapper")
	public XmlMapper getXMLMapper() {
		XmlMapper mapper = new XmlMapper();
		
		this.setCommonMapperConfig(mapper);
		
		return mapper;
	}
	
	private void setCommonMapperConfig(ObjectMapper mapper) {
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z"));
		
		mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	}
}

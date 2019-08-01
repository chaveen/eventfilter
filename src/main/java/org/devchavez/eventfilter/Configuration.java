package org.devchavez.eventfilter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@org.springframework.context.annotation.Configuration
public class Configuration {
	
	@Bean(name = "jsonMapper")
	public ObjectMapper getJSONMapper() {
		ObjectMapper mapper = new ObjectMapper();

		this.setCommonMapperConfig(mapper);
		
		mapper.registerModule(new JavaTimeModule());
		
		mapper.setTimeZone(TimeZone.getTimeZone("ADT"));

		return mapper;
	}
	
	@Bean(name = "csvMapper")
	public CsvMapper getCSVMapper() {
		CsvMapper mapper = new CsvMapper();

		this.setCommonMapperConfig(mapper);

		this.setTextToDateMapper(mapper);
		
		return mapper;
	}
	
	@Bean(name = "xmlMapper")
	public XmlMapper getXMLMapper() {
		XmlMapper mapper = new XmlMapper();
		
		this.setCommonMapperConfig(mapper);
		
		this.setTextToDateMapper(mapper);
		
		return mapper;
	}
	
	private void setCommonMapperConfig(ObjectMapper mapper) {
		mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	}
	
	/**
	 * Attach a customer date/time parser to deserialize text to ZonedDateTime object
	 */
	private void setTextToDateMapper(ObjectMapper mapper) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
		
		JavaTimeModule dateTimeModule = new JavaTimeModule();
		dateTimeModule.addDeserializer(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {

			@Override
			public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				String str = p.getText();
				
				TemporalAccessor ta = dtf.parse(str);
				
				ZonedDateTime dt = ZonedDateTime.from(ta);
				
				return dt;
			}
		});
		
		mapper.registerModule(dateTimeModule);
	}
}

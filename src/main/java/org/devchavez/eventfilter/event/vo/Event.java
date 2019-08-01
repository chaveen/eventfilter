package org.devchavez.eventfilter.event.vo;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This is the VO object for reading and writing Events
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ 
	"client-address",	
	"client-guid",	
	"request-time",	
	"service-guid",	
	"retries-request",	
	"packets-requested",
	"packets-serviced",	
	"max-hole-size"
})
public class Event {
	@JsonProperty("max-hole-size")
	private Integer maxHoleSize;
	
	@JsonProperty("packets-serviced")
	private Integer packetsServiced;
	
	@JsonProperty("packets-requested")
	private Integer packetsRequested;
	
	@JsonProperty("client-guid")
	private UUID clientGuid;

	@JsonProperty("client-address")
	private String clientAddress;
	
	@JsonProperty("request-time")
	private ZonedDateTime requestTime;
	
	@JsonProperty("service-guid")
	private UUID serviceGuid;
	
	@JsonProperty("retries-request")
	private Integer retriesRequest;
	
	public Integer getMaxHoleSize() {
		return maxHoleSize;
	}
	public void setMaxHoleSize(Integer maxHoleSize) {
		this.maxHoleSize = maxHoleSize;
	}
	public Integer getPacketsServiced() {
		return packetsServiced;
	}
	public void setPacketsServiced(Integer packetsServiced) {
		this.packetsServiced = packetsServiced;
	}
	public UUID getClientGuid() {
		return clientGuid;
	}
	public void setClientGuid(UUID clientGuid) {
		this.clientGuid = clientGuid;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public ZonedDateTime getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(ZonedDateTime requestTime) {
		this.requestTime = requestTime;
	}
	public UUID getServiceGuid() {
		return serviceGuid;
	}
	public void setServiceGuid(UUID serviceGuid) {
		this.serviceGuid = serviceGuid;
	}
	public Integer getRetriesRequest() {
		return retriesRequest;
	}
	public void setRetriesRequest(Integer retriesRequest) {
		this.retriesRequest = retriesRequest;
	}
	public Integer getPacketsRequested() {
		return packetsRequested;
	}
	public void setPacketsRequested(Integer packetsRequested) {
		this.packetsRequested = packetsRequested;
	}
}

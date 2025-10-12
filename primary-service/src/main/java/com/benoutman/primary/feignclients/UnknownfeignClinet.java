package com.benoutman.primary.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "api-gateway")
public interface UnknownfeignClinet {
	
	@GetMapping("/unknown-service/api/unknown")
	public String getUnknown();

}

//package com.benoutman.primary.feignclients;
//
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
//import org.springframework.context.annotation.Bean;
//
//import feign.Feign;
//
//@LoadBalancerClient(value = "unknown-service")
//public class UnknownLoadBalancer {
//	
//	@LoadBalanced
//	@Bean
//	public Feign.Builder feignBuilder() {
//		return Feign.builder();
//	}
//
//}

package com.restaurant_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class RestaurantServiceApplication
{
    /*@EnableCaching annotation simply turns ON the caching mechanism in your application
    as soon as you annotate your main class.
     */
    //Caching means storing data in a temporary fast memory so you don't have to fetch it again and again.

	public static void main(String[] args) {
		SpringApplication.run(RestaurantServiceApplication.class, args);
	}

}

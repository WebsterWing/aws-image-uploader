package com.webster.awsimageupload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


@Configuration
public class AmazonConfig {
	@Bean
	public AmazonS3 s3() {
		return AmazonS3ClientBuilder.standard()
				.withRegion(Regions.US_EAST_2)
				.build();
	}
}

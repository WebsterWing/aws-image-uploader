package com.webster.awsimageupload;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
/*
	@Bean
	CommandLineRunner s3BucketList(AmazonS3 s3) {
		return args -> {
			List<Bucket> buckets = s3.listBuckets();
			for (Bucket b : buckets) {
				System.out.println("* " + b.getName());
			}
		};
	}
	*/
}


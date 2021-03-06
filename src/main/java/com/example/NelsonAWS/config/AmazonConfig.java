package com.example.NelsonAWS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;


@Configuration
public class AmazonConfig {

	@Bean
	public AmazonS3 s3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials("", "");
		return AmazonS3Client.builder()
				.withRegion("eu-central-1")
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
	}
}

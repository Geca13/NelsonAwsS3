package com.example.NelsonAWS.buckets;


public enum BucketName {

	PROFILE_IMAGE("nelsonaws");
	
	private final String bucketName;

	 BucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getBucketName() {
		return bucketName;
	}
	
	 
	
}

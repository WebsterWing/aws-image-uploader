package com.webster.awsimageupload.filestore;

public enum BucketName {
	PROFILE_IMAGE("webster-spring-image-tutorial"),
	PROFILE_IMAGE_REGION("us-east-2");
	
	private final String bucketName;

	BucketName(String s) {
		this.bucketName = s;
	}
	
	@Override
	public String toString() {
		return bucketName;
	}
}

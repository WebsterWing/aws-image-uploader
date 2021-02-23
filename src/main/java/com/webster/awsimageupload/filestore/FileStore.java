package com.webster.awsimageupload.filestore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.webster.awsimageupload.exceptions.FileSaveFailureException;

@Service
public class FileStore {
	@Autowired
	private AmazonS3 s3;

	public void save(
			String path,
			String fileName,
			Optional<Map<String, String>> optionalMetadata,
			InputStream inputStream)
	{
		ObjectMetadata metadata = new ObjectMetadata();
		optionalMetadata.ifPresent(map -> {
			map.forEach(metadata::addUserMetadata);
		});
		
		try {
			s3.putObject(path, fileName, inputStream, metadata);
		} catch (AmazonServiceException e) {
			throw new FileSaveFailureException();
		}
	}
	
	public void save (
			String path,
			String fileName,
			MultipartFile file)
	{
		ObjectMetadata metadata = makeMetadata(file);
		
		try {
			s3.putObject(path, fileName, file.getInputStream(), metadata);
		} catch (AmazonServiceException | IOException e) {
			throw new FileSaveFailureException();
		}
	}

	public byte[] download(String path, String key) {
		try {
			S3Object object =  s3.getObject(path, key);
			return IOUtils.toByteArray(object.getObjectContent());
		} catch (AmazonServiceException | IOException e) {
			throw new IllegalStateException("Failed to download file from S3", e);
		} 
	}
	
	private ObjectMetadata makeMetadata(MultipartFile file) {
		ObjectMetadata metadata = new ObjectMetadata();
		
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());
		
		return metadata;
	}
}

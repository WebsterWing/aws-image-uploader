package com.webster.awsimageupload.profile;

import static org.apache.http.entity.ContentType.IMAGE_GIF;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webster.awsimageupload.exceptions.BadFiletypeException;
import com.webster.awsimageupload.exceptions.EmptyFileException;
import com.webster.awsimageupload.exceptions.NoUserException;
import com.webster.awsimageupload.filestore.BucketName;
import com.webster.awsimageupload.filestore.FileStore;

@Service
public class UserProfileService {
	@Autowired
	private UserProfileDataAccessService userProfileDataAccessService;
	
	@Autowired
	private FileStore fileStore;
	
	@Autowired
	private UserProfileChangeTracker userProfileChangeTracker;
	
	private final List<String> imageMimeTypes = Arrays.asList(
			IMAGE_JPEG.getMimeType(),
			IMAGE_PNG.getMimeType(),
			IMAGE_GIF.getMimeType());
	
	public List<UserProfile> getUserProfiles() {
		return userProfileDataAccessService.getUserProfiles();
	}
	
	public List<UserProfile> changedProfiles() {
		List<UUID> ids = userProfileChangeTracker.GetChangedProfiles();
		return userProfileDataAccessService.getUserProfilesByIdList(ids);
	}

	public UserProfile uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
		// 1. Check if file is not empty
		isFileEmpty(file);
		
		// 2. Check if file is image
		isImage(file);
		
		// 3. Check if user exists in data
		UserProfile user = userProfileDataAccessService
				.getUserById(userProfileId)
				.orElseThrow(NoUserException::new);

		// 5. Store the image in s3 and update database with s3 image link
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE, user.getUuid());
		String fileName = String.format("%s-%s", UUID.randomUUID(), file.getOriginalFilename());
		fileStore.save(path, fileName, file);
		
		String link = String.format("http://%s.s3-website.%s.amazonaws.com/%s/%s", 
				BucketName.PROFILE_IMAGE, BucketName.PROFILE_IMAGE_REGION, user.getUuid(), fileName);
		user.setUserProfileImageLink(link);
		userProfileChangeTracker.NotifyUpdate(user);
		return user;
	}

	private void isImage(MultipartFile file) {
		if ( !imageMimeTypes.contains(file.getContentType()) ) {
			throw new BadFiletypeException();
		}
	}

	private void isFileEmpty(MultipartFile file) {
		if ( file.isEmpty() ) {
			throw new EmptyFileException();
		}
	}

	public byte[] downloadUserProfileImage(UUID userProfileId) {
		UserProfile user = userProfileDataAccessService
				.getUserById(userProfileId)
				.orElseThrow(NoUserException::new);
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE, user.getUuid());
		
		return user.getUserProfileImageLink()
			.map(key -> fileStore.download(path, key))
			.orElse(new byte[0]);
	}
}

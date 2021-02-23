package com.webster.awsimageupload.profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webster.awsimageupload.datastore.FakeUserProfileDataStore;

@Repository
public class UserProfileDataAccessService {
	@Autowired
	private FakeUserProfileDataStore userProfileDataStore;
	
	public List<UserProfile> getUserProfiles() {
		return userProfileDataStore.getUserProfiles();
	}
	
	public Optional<UserProfile> getUserById(UUID id) {
		return userProfileDataStore.getUserById(id);
	}
}

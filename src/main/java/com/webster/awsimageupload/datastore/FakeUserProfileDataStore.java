package com.webster.awsimageupload.datastore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.webster.awsimageupload.profile.UserProfile;

@Repository
public class FakeUserProfileDataStore {

	private static final List<UserProfile> USER_PROFILES = new ArrayList<>(
			Arrays.asList(
					new UserProfile(UUID.fromString("30a13365-bc62-462b-84ef-8bc7dd329bfd"), "janetjones", null),
					new UserProfile(UUID.fromString("feca849b-af6c-4160-af57-4080f365c303"), "antoniojunior", null)));
	
	public List<UserProfile> getUserProfiles() {
		return USER_PROFILES;
	}
	
	public Optional<UserProfile> getUserById(UUID id) {
		for (UserProfile p : USER_PROFILES) {
			if ( p.getUuid().equals(id) ) {
				return Optional.of(p);
			}
		}
		
		return Optional.empty();
	}
}

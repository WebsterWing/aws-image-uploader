package com.webster.awsimageupload.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

/*
 * This object knows how long it has been since each user profile has changed, if
 * it has changed at all in the past 5 minutes
 * 
 * Change time is tracked using Unix Time
 */
@Service
public class UserProfileChangeTracker {
	Map<UUID, Long> TimestampMap; // Associates user profile UUID with Unix Timestamps
	
	public UserProfileChangeTracker() {
		TimestampMap = new HashMap<>();
	}
	
	public synchronized void NotifyUpdate(UserProfile user) {
		long now = System.currentTimeMillis() / 1000L; // Unix Time according to https://stackoverflow.com/questions/732034/getting-unixtime-in-java
		TimestampMap.put(user.getUuid(), now);
	}
	
	public synchronized List<UUID> GetChangedProfiles() {
		List<UUID> ChangedProfiles = new ArrayList<>();
		long fiveMinutesAgo = (System.currentTimeMillis() /1000L) - (5 * 60); // subtract 5 minutes worth of seconds from the current timestamp
		TimestampMap.forEach((uuid, time) -> {
			if (time > fiveMinutesAgo) { // change has happened in the past 5 minutes
				ChangedProfiles.add(uuid);
			} else { // no change in the past 5 minutes
				TimestampMap.remove(uuid);
			}
		});
		return ChangedProfiles;
	}
}

package com.webster.awsimageupload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
	private UUID uuid;
	private String username;
	private String userProfileImageLink; // S3 key
	
	public UserProfile(UUID uuid, String username, String userProfileImageLink) {
		super();
		this.uuid = uuid;
		this.username = username;
		this.userProfileImageLink = userProfileImageLink;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Optional<String> getUserProfileImageLink() {
		return Optional.ofNullable(userProfileImageLink);
	}

	public void setUserProfileImageLink(String userProfileImageLink) {
		this.userProfileImageLink = userProfileImageLink;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userProfileImageLink, username, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		return Objects.equals(userProfileImageLink, other.userProfileImageLink)
				&& Objects.equals(username, other.username) && Objects.equals(uuid, other.uuid);
	}
	
	
}

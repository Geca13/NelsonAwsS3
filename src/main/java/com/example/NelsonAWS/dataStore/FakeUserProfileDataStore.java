package com.example.NelsonAWS.dataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.NelsonAWS.profile.UserProfile;

@Repository
public class FakeUserProfileDataStore {
	
	private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

	static {
		USER_PROFILES.add(new UserProfile(UUID.fromString("1783e74e-1fe3-498d-8700-1e09c70c40ce"),"janetjones", null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("bfc3fa7c-24cf-4c7f-bac1-a802e08dcee7"),"MarjanGeca", null));
		}
	
	public List<UserProfile> getUserProfiles() {
		return USER_PROFILES;
	}
}

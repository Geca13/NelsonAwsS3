package com.example.NelsonAWS.profile;

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
import com.example.NelsonAWS.buckets.BucketName;
import com.example.NelsonAWS.filestorage.FileStore;

@Service
public class ProfileService {

	private final UserProfileDataAccessService userProfileDataAccessService;

	private final FileStore fileStore;
	
	
	@Autowired
	public ProfileService(UserProfileDataAccessService userProfileDataAccessService,FileStore fileStore) {
		super();
		this.userProfileDataAccessService = userProfileDataAccessService;
		this.fileStore = fileStore;
	}
	
	List<UserProfile> getUserProfiles(){
		return userProfileDataAccessService.getUserProfiles();
		
	}

	public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {

      if (file.isEmpty()) {
    	  throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
      }
      if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
    	  throw new IllegalStateException("File must be an image");
      }
      
      UserProfile user = getUserProfileOrThrow(userProfileId);
      
    
    
    
    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));
    
    String path =  String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
    String fileName = String.format("%s-%s", file.getOriginalFilename(),UUID.randomUUID());
    
    try {
		fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
		user.setUserProfileImageLink(fileName);
	} catch (IOException e) {
		
		throw new IllegalStateException(e);
	}
		
	}

	private UserProfile getUserProfileOrThrow(UUID userProfileId) {
		return  userProfileDataAccessService
			      .getUserProfiles()
			      .stream()
			      .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
			      .findFirst()
			      .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
		}

	 byte[] downloadUserProfileImage(UUID userProfileId) {
	        UserProfile user = getUserProfileOrThrow(userProfileId);

	        String path = String.format("%s/%s",
	                BucketName.PROFILE_IMAGE.getBucketName(),
	                user.getUserProfileId());

	        return user.getUserProfileImageLink()
	                .map(key -> fileStore.download(path, key))
	                .orElse(new byte[0]);
      }
}

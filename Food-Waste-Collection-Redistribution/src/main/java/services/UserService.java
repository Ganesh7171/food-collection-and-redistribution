package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import entities.NgoDetails;
import entities.Users;
import repositories.NgoDetailsRepository;
import repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	 @Autowired
	  private NgoDetailsRepository ngoRepository;

	public Users authenticateUser(Users user){
			  		 
			  String userName = user.getUsername();
			 Users userObject = userRepository.getPasswordByUsername(userName);
				System.out.println(userObject.getAddress());   
				return userObject;
				  
		  }


	public void updateUserProfile(Users user, MultipartFile file) throws IOException {
	    Users existingUser = userRepository.findById((long) user.getUserId())
	                                       .orElseThrow(() -> new RuntimeException("User not found"));

	    // Update basic fields
	    existingUser.setUsername(user.getUsername());
	    existingUser.setPhoneNumber(user.getPhoneNumber());
	    existingUser.setEmailAddress(user.getEmailAddress());
	    // add other fields you allow to edit

	    // Update NGO image if exists
	    if (file != null && !file.isEmpty() && existingUser.getNgoDetails() != null) {
	        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	        Path path = Paths.get("uploads/" + fileName);
	        Files.createDirectories(path.getParent());
	        Files.write(path, file.getBytes());

	       // existingUser.getNgoDetails().setImagePath(fileName);
	    }

	    userRepository.save(existingUser);
	}
	
	
	
	
	public void updateNgoProfile(int userId, NgoDetails updatedNgo, MultipartFile file) throws IOException {
	    // Step 1: Get the existing user and NGO record
		
		System.out.println("**********************method called***********"  + updatedNgo.toString());
	    Users existingUser = userRepository.findById((long) userId)
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
	    	System.out.println(existingUser);
	    	
	    NgoDetails existingNgo = existingUser.getNgoDetails();
	    
	    System.out.println("before if");
	    if (existingNgo == null) {
	        throw new RuntimeException("No NGO details found for this user");
	    }

	    // Step 2: Update editable NGO fields
	    existingNgo.setOrganizationName(updatedNgo.getOrganizationName());
	    System.out.println(updatedNgo.getOrganizationName());
	    existingNgo.setDonationTypes(updatedNgo.getDonationTypes());
	    existingNgo.setVisitingHours(updatedNgo.getVisitingHours());
	    
	    System.out.println("after update");
	    // Add any other NGO-specific fields you have

	    // Step 3: Update related User fields (address, phone, Google Plus)
	    
	    System.out.println(updatedNgo.getUser());
	    
	    if (updatedNgo.getUser() != null) {
	        existingUser.setAddress(updatedNgo.getUser().getAddress());
	        
	        System.out.println(updatedNgo.getUser().getAddress());
	        
	        System.out.println("*******************************");
	        existingUser.setPhoneNumber(updatedNgo.getUser().getPhoneNumber());
	        existingUser.setGooglePlusCode(updatedNgo.getUser().getGooglePlusCode());
	    }

	    // Step 4: Handle image upload — store in DB as BLOB
	    if (file != null && !file.isEmpty()) {
	    	System.out.println(file.getSize());
	        existingNgo.setImageData(file.getBytes());        // store image bytes
	        System.out.println("file uploded");
	        //existingNgo.setImageType(file.getContentType());  // optional: store MIME type
	    }
	    existingUser.setNgoDetails(existingNgo);
	    // Step 5: Save both User and NGO
	    userRepository.save(existingUser);  // saves both if cascade is configured
	   /* ngoRepository.save(existingNgo);*/
	    
	    System.out.println("After saving");
	   
	}


	}

		
	
	
	
	




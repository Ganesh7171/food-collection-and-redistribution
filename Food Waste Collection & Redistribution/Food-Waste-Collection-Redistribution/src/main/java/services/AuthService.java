package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Users;
import repositories.AuthRepository;

@Service
public class AuthService {
	
	@Autowired
	AuthRepository authRepository;
	

	public Users authenticateUser(Users user){
			  		 
			  String userName = user.getUsername();
			 Users userObject = authRepository.getPasswordByUsername(userName);
				System.out.println(userObject.getAddress());   
				return userObject;
				  
		  }
	
	
	
}



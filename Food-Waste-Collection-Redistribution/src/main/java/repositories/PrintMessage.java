package repositories;

import org.springframework.stereotype.Repository;

@Repository
public class PrintMessage {
	
	
	public String printMe() {
	
		return "Welcome to the new project of spring boot";
	}

}

package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.NgoDetails;
import repositories.NgoDetailsRepository;

@Service
public class NgoService {

    @Autowired
    private NgoDetailsRepository ngoDetailsRepository;

    public List<NgoDetails> getAllNgos() {
        return ngoDetailsRepository.findAll();
        
        
    }
    
    public Optional<NgoDetails> getNgoById(int id) {
    	
    	return ngoDetailsRepository.findById(id);
    }

	
}

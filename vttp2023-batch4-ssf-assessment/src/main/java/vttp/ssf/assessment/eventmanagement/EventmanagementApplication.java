package vttp.ssf.assessment.eventmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner {

	@Autowired
	DatabaseService dbSvc;

	@Autowired
	RedisRepository redisRepo;

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}
	
	// TODO: Task 1
	@Override
    public void run(String... args) throws Exception {
        
		String filePath = "events.json";
		dbSvc.readFile(filePath);

		for(Event e : dbSvc.readFile(filePath))
			redisRepo.saveRecord(e);
		
    }

}

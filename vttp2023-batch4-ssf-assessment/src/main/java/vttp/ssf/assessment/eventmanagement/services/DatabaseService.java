package vttp.ssf.assessment.eventmanagement.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {

    @Autowired
    RedisRepository redisRepo;

    // TODO: Task 1
    public List<Event> readFile(String _fileName) throws FileNotFoundException, JsonMappingException, JsonProcessingException{
        File file = new File(_fileName);
        InputStream is = new FileInputStream(file);

        List<Event> eventList = new ArrayList<>();
        JsonReader jsonReader = Json.createReader(is);
		JsonArray eventJsonArray = jsonReader.readArray();

        for (JsonValue jsonValue : eventJsonArray){
            JsonObject jsonObject = jsonValue.asJsonObject();

            // with object mapper
            ObjectMapper mapper = new ObjectMapper();
            Event e = mapper.readValue(jsonObject.toString(), Event.class);

            // without object mapper
            // Event e = new Event();
            // e.setEventId(Integer.parseInt(jsonObject.get("eventId").toString()));
            // e.setEventName(jsonObject.get("eventId").toString());
            // ... and more
            eventList.add(e);
        }
        return eventList; 
    }

    public boolean isAgeValid(Date dob) {
        return Period.between(dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears() >= 21;
    }

    public boolean isCapacityAvailable(Integer eventId, int ticketCount) {
        
        Event e = redisRepo.getEventByEventId(eventId);
        return e.getParticipants() + ticketCount <= e.getEventSize();
    }


}

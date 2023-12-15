package vttp.ssf.assessment.eventmanagement.repositories;

import java.io.StringReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired @Qualifier("myredis")
	RedisTemplate<String,String> redisStringTemplate;
	// TODO: Task 2
	public void saveRecord(Event event){
		JsonObject JsonEventObj = Json.createObjectBuilder()
				.add("eventId", event.getEventId())
				.add("eventName", event.getEventName())
				.add("eventSize", event.getEventSize())
				.add("eventDate", event.getEventDate())
				.add("participants", event.getParticipants())
				.build();
		String JsonNewsString = JsonEventObj.toString();
		redisStringTemplate.opsForValue().set(event.getEventId().toString(), JsonNewsString);
	}

	// TODO: Task 3
	public int getNumberOfEvents(){
		Set<String> keys = redisStringTemplate.keys("*");
        return keys != null ? keys.size() : 0;
	}

	// TODO: Task 4
	public Event getEvent(Integer index){
		Set<String> keys = redisStringTemplate.keys("*");
        if (keys == null || index < 0 || index >= keys.size()) {
            return null; 
        }

        List<String> sortedKeys = new ArrayList<>(keys);
        String key = sortedKeys.get(index);
        String jsonEventString = redisStringTemplate.opsForValue().get(key);

        if (jsonEventString == null) 
            return null; 

        JsonReader reader = Json.createReader(new StringReader(jsonEventString));
        JsonObject jsonEventObj = reader.readObject();

        Event event = new Event();
        event.setEventId(jsonEventObj.getInt("eventId"));
        event.setEventName(jsonEventObj.getString("eventName"));
        event.setEventSize(jsonEventObj.getInt("eventSize"));
		event.setEventDate(jsonEventObj.getJsonNumber("eventDate").longValue());
        event.setParticipants(jsonEventObj.getInt("participants"));

        return event;	
	}

	public Event getEventByEventId(Integer eventId) {
		String key = eventId.toString();
		String jsonEventString = redisStringTemplate.opsForValue().get(key);

		if (jsonEventString == null) {
			return null;
		}

		JsonReader reader = Json.createReader(new StringReader(jsonEventString));
		JsonObject jsonEventObj = reader.readObject();

		Event event = new Event();
		event.setEventId(jsonEventObj.getInt("eventId"));
		event.setEventName(jsonEventObj.getString("eventName"));
		event.setEventSize(jsonEventObj.getInt("eventSize"));
		event.setEventDate(jsonEventObj.getJsonNumber("eventDate").longValue());
		event.setParticipants(jsonEventObj.getInt("participants"));

		return event;
	}
}

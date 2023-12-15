package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.EventWrapper;
import vttp.ssf.assessment.eventmanagement.models.UserForm;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Controller
@RequestMapping("/events")
public class EventController {

	@Autowired
	RedisRepository redisRepo;

	List<Event> eventList = new ArrayList<Event>();

	//TODO: Task 5
	@GetMapping("/listing")
	public String displayEvents(Model m, HttpSession sess){
		eventList.clear();

		for(Integer i=0; i<(redisRepo.getNumberOfEvents()); i++){
			eventList.add(redisRepo.getEvent(i));
		}

		EventWrapper wrapper = new EventWrapper();
		wrapper.setEventList(eventList);

		m.addAttribute("wrapper", wrapper);
		sess.setAttribute("wrapper", wrapper);
		return "listing";
	}

	@PostMapping("/register/{eventId}")
	public String registerEvent(@Valid @ModelAttribute("wrapper") EventWrapper wrapper, BindingResult result, @PathVariable("eventId") Integer eventId, Model m, HttpSession session) {
        // Store the eventId and user in session after successful registration
		m.addAttribute("user", new UserForm());
        session.setAttribute("eventId", eventId);
        session.setAttribute("user", new UserForm());

        return "eventregister";
    }
}

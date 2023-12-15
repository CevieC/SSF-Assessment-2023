package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.assessment.eventmanagement.models.EventWrapper;
import vttp.ssf.assessment.eventmanagement.models.UserForm;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping("/events")
public class RegistrationController {
    
    @Autowired
    DatabaseService dbSvc;

    // TODO: Task 6
    @GetMapping("/register/{eventId}")
    public String showRegistrationForm(@PathVariable("eventId") Integer eventId, Model m, HttpSession sess) {
        UserForm user = new UserForm(); 

        if (sess.getAttribute("eventId") != null) {
            return "redirect:/";
        } else {
            // Set a new user if id is valid
            m.addAttribute("user", new UserForm());
        }

        m.addAttribute("eventId", eventId);
        m.addAttribute("wrapper", sess.getAttribute("wrapper"));
        sess.setAttribute("user", user);

        return "eventregister";
    }


    // TODO: Task 7
    @PostMapping("/registration/register")
    public String processRegistration(@ModelAttribute UserForm form, Model model) {
        if (!dbSvc.isAgeValid(form.getBirthDate())) {
            return "ErrorRegistration.html";
        }
        // if participants is at limit, throw error with a different message

        // if it is successful, total = event.getEventSize() + form.getTickets();
        // set event.setEventSize(total);
        // update data into redis

        return "SuccessRegistration.html";
    }
}

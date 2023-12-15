package vttp.ssf.assessment.eventmanagement.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserForm {
    
    @NotNull(message="Full Name is mandatory")
    @Size(min=5, max=25, message = "Invalid Name")
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date birthDate;

    @Email
    @NotNull(message="Email is mandatory")
    private String Email;

    @Pattern(regexp = "(\\8|9)[0-9]{7}", message = "Invalid phone number")
    private String phoneNo;

    @Min(value = 1, message="Please buy at least 1 ticket")
    @Max(value = 3, message="Maximum of 3 tickets for sale")
    @NotNull(message = "Please enter amount of tickets")
    private Integer tickets;

    private String gender;

    private Integer eventId;

    public Integer getEventId() {return eventId;}

    public void setEventId(Integer eventId) {this.eventId = eventId;}

    public UserForm(){}

    public String getFullName() {return fullName;}

    public void setFullName(String fullName) {this.fullName = fullName;}

    public Date getBirthDate() {return birthDate;}

    public void setBirthDate(Date birthDate) {this.birthDate = birthDate;}

    public String getEmail() {return Email;}

    public void setEmail(String email) {Email = email;}

    public String getPhoneNo() {return phoneNo;}

    public void setPhoneNo(String phoneNo) {this.phoneNo = phoneNo;}

    public Integer getTickets() {return tickets;}

    public void setTickets(Integer tickets) {this.tickets = tickets;}

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}

}

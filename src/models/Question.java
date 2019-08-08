package models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Question {
	private String title;
	private String link;
	private int creation_date;
	private int answer_count;
	private boolean is_answered;
	private Owner owner;
    
    public String getTitle() {
    	return title;
    }
    
    public String getLink() {
    	return link;
    }
    
    public Date getCreationDate() {
    	return new java.util.Date((long)creation_date*1000);
    }
    
    public int getAnswerCount() {
    	return answer_count;
    }
    
    public Owner getOwner() {
    	return owner;
    }
    
    public String getCreationDateFormat() {
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate date = Instant.ofEpochMilli((long)creation_date*1000)
    			.atZone(ZoneId.systemDefault())
    			.toLocalDate();
    	return date.format(dateFormatter);
    }
}

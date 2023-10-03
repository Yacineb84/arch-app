package myboot.app3.web;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieDTO {


	private int id;

	private String name;

	@Min(value = 2000)
	@Max(value = 2100)
	private int year;
	
	private String description;

	public MovieDTO(int id, String name, @Min(2000) @Max(2100) int year, String description) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.description = description;
	}
	
	public String getCompleteName() {
	    return getName() + " " + getYear();
	}

}

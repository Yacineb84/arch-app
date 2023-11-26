package myboot.myapp.model;

import java.util.LinkedList;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@NotBlank(message = "{user.email}")
    private String email;
    
    @Basic
    private String name;
    
    @Basic
    private String firstName;
    
    @Basic
    private String site;
    
    @Basic
    private String dateOfBirth;
    
    @Basic
    private String password;
    
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user",orphanRemoval = true,cascade = CascadeType.ALL)
    @Exclude
    private Cv cv;

	public User(String email, String name, String firstName, String site, String dateOfBirth, String password) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.site = site;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		this.cv = new Cv(new LinkedList<Activity>(), this);
	}

}
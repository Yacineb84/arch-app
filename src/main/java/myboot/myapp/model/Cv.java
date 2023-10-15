package myboot.myapp.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Table(name = "cv")
@Data
@NoArgsConstructor

public class Cv {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cv")
    @Exclude
    private List<Activity> activities = new LinkedList<>();
    
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @Exclude
    private MyUser user;

	public Cv(List<Activity> activities, MyUser user) {
		super();
		this.activities = activities;
		this.user = user;
	}
    
    

}

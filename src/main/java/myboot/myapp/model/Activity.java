package myboot.myapp.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Table(name = "activity")
@Data
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Basic
    @NotNull
    private int year;
    
    @Basic
    @NotBlank
    private String nature;
    
    @Basic
    @NotBlank
    private String title;
    
    @Basic
    private String description;
    
    @Basic
    private String webAddress;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @Exclude
    private Cv cv;

	public Activity( int year, @NotBlank String nature, @NotBlank String title, String description, String webAddress) {
		super();
		this.year = year;
		this.nature = nature;
		this.title = title;
		this.description = description;
		this.webAddress = webAddress;
	}



	

    

}
package myboot.myapp.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Basic
    private String name;
    
    @Basic
    private String firstName;
    
    @Basic
    private String email;
    
    @Basic
    private String site;
    
    @Basic
    private String dateOfBirth;
    
    @Basic
    private String password;
    
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user",orphanRemoval = true)
    @Exclude
    private Cv cv;

}
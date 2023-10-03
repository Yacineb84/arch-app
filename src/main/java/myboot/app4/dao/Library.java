package myboot.app4.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Data;

@Entity
@Data
public class Library {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id")
    @RestResource(path = "libraryAddress", rel="address")
    private Address address;
    
//    @OneToOne
//    @JoinColumn(name = "secondary_address_id")
//    @RestResource(path = "libraryAddress", rel="address")
//    private Address secondaryAddress;
    
    // standard constructor, getters, setters
}

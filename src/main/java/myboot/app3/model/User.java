package myboot.app3.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Table(name = "user2")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @Basic(fetch = FetchType.LAZY)
    private String description;

    //@JsonBackReference
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Exclude
    private List<Post> posts = new LinkedList<>();

    //@JsonBackReference
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    @OrderColumn(name = "position")
    @Exclude
    private List<Comment> comments = new LinkedList<>();

    public User(String name, String email, String description) {
        this.name = name;
        this.email = email;
        this.description = description;
    }

}
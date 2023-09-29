package myboot.app3.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;

    //@JsonBackReference
    @JsonManagedReference
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @Exclude
    private List<Comment> comments = new LinkedList<>();

    //@JsonManagedReference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @Exclude
    private User user;

    public Post(String subject, User user) {
        super();
        this.subject = subject;
        this.user = user;
    }

}
package myboot.myapp.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import myboot.myapp.model.Cv;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private String name;
    
    private String firstName;
    
    private String email;
    
    private String site;
    
    private String dateOfBirth;
    
    private Cv cv;

}
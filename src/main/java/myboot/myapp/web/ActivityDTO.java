package myboot.myapp.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {

    private int year;
    
    private String nature;

    private String title;
 
    private String description;

    private String webAddress;

}
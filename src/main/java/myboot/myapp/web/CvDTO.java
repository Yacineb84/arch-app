package myboot.myapp.web;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
import myboot.myapp.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CvDTO {
	
    private List<Activity> activities = new LinkedList<>();
    
}

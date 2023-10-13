package myboot.app1.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Profile("!usejwt")
public class ErrorControler implements ErrorController {

	@RequestMapping("/error")
	@ResponseBody
	public String handleError(Exception e) {
		e.printStackTrace();
		return "Error " + e.getClass().getName();
	}

}
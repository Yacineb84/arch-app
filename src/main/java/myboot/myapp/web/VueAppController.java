package myboot.myapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class VueAppController {

    @RequestMapping(value = "/app")
    private ModelAndView hello() {
        return new ModelAndView("app");
    }

    /*@RequestMapping(value = "app/cv/{email}")
    private ModelAndView route() {
        return new ModelAndView("app");
    }*/
}
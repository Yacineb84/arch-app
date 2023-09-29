package myboot.app3.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloRestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping(value = "/list")
    public List<Integer> list() {
        return Arrays.asList(10, 20, 30);
    }
    
    @GetMapping("/hello/{message}")
    public String helloWithMessage(@PathVariable String message) {
        return "Hello " + message;
    }
    
    @GetMapping(value = "/hello2")
    public ResponseEntity<String> hello2() {
        return ResponseEntity.ok("Hello");
    }
    
    @GetMapping(value = "/notFound")
    public ResponseEntity notFound() {
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping(value = "/noContent")
    public ResponseEntity noContent() {
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/headers")
    public ResponseEntity<String> headers(@RequestHeader String myHeader) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("resultHeader", myHeader.toUpperCase());
        var res = ResponseEntity.ok()//
                .headers(responseHeaders)//
                .header("xx", "yy")//
                .body("HEADER " + myHeader);
        return res;
    }

}
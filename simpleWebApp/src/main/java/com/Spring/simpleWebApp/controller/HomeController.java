package com.Spring.simpleWebApp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String greet(){
        return "Welcome to the SimpleWebApp!!";
    }

    @RequestMapping("/about")
    public String about(){
        return "No about page found!!";
    }
}

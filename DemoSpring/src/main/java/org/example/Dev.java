package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Dev {

    @Autowired
    @Qualifier("desktop")//field injection
    private Computer computer;

    // constructor injection
//    public Dev(Laptop laptop){
//        this.laptop = laptop;
//    }

//    @Autowired
//    public void setLaptop(Laptop laptop) {
//        this.laptop = laptop;
//    }

    public void build() {
        System.out.println("working on Awesome project");
        computer.compile();
    }

}

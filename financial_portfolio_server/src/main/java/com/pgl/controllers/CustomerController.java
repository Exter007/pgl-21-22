package com.pgl.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="customer")
public class CustomerController {
    protected Logger logger;

    public CustomerController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
}

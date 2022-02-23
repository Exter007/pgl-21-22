package com.pgl.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "institution")
public class InstitutionController {
    protected Logger logger;

    public InstitutionController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
}

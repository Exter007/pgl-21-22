package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.services.ApplicationClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "account/")
public class AccountController {
    protected Logger logger;

    @Autowired
    ApplicationClientService applicationClientService;

    public AccountController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @ResponseBody
    @RequestMapping(value = "register/client", method = RequestMethod.POST)
    public ResponseEntity<?> confirm(@RequestBody ApplicationClient applicationClient) {
        applicationClient = applicationClientService.getRepository().save(applicationClient);
        return ResponseEntity.ok(applicationClient);
    }

}

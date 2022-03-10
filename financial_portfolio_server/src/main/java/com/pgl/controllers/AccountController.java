package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.security.UserDetailsImpl;
import com.pgl.services.ApplicationClientService;
import com.pgl.utils.JwtResponse;
import com.pgl.utils.JwtUtils;
import com.pgl.utils.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "account/")
public class AccountController {
    protected Logger logger;

//    private final AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ApplicationClientService applicationClientService;

//    public AccountController(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//        this.logger = LoggerFactory.getLogger(this.getClass());
//    }

    public AccountController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

//    @PostMapping("login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(new JwtResponse(jwt,
//                userDetails.getUsername(),
//                roles));
//    }

    @PostMapping(value = "register/client")
    public ResponseEntity<?> register(@RequestBody ApplicationClient applicationClient) throws Exception {
        applicationClient = applicationClientService.saveClient(applicationClient);
        return ResponseEntity.ok(applicationClient);
    }

    @RequestMapping(value = "getTest", method = RequestMethod.GET)
    public ResponseEntity<?> getTest(String id){
        return ResponseEntity.ok("Success");
    }

    @RolesAllowed("APPLICATION_CLIENT")
    @RequestMapping(value = "getTestSecu", method = RequestMethod.GET)
    public ResponseEntity<?> getTestSecu(String id){
        return ResponseEntity.ok("Success");
    }

}

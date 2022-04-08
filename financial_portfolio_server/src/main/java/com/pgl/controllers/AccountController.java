package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
import com.pgl.security.UserDetailsImpl;
import com.pgl.services.ApplicationClientService;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.UserService;
import com.pgl.utils.JwtResponse;
import com.pgl.utils.JwtUtils;
import com.pgl.utils.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "account/")
public class AccountController {
    protected Logger logger;

    private final AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationClientService applicationClientService;

    @Autowired
    FinancialInstitutionService financialInstitutionService;

    public AccountController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * User login
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findByLogin(loginRequest.getUsername());
        if(!user.getActive()){
            throw new RuntimeException("Account not activated");
        }
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles, user));
    }

    /**
     * Customer user registration
     * @param applicationClient
     * @return
     * @throws Exception
     */
    @PostMapping(value = "register/client")
    public ResponseEntity<?> register(@RequestBody ApplicationClient applicationClient) throws Exception {
        applicationClient = applicationClientService.saveClient(applicationClient);
        userService.sendValidateAccount(applicationClient);
        return ResponseEntity.ok(applicationClient);
    }

    /**
     * Institution user registration
     * @param institution
     * @return
     * @throws Exception
     */
    @PostMapping(value = "register/institution")
    public ResponseEntity<?> register(@RequestBody FinancialInstitution institution) throws Exception {
        institution = financialInstitutionService.saveInstitution(institution);
        userService.sendValidateAccount(institution);
        return ResponseEntity.ok(institution);
    }

    /**
     * Send the account activation code to a user
     * @param user
     * @return
     * @throws Exception
     */
    @PermitAll
    @PostMapping(value = "register/send-code")
    public ResponseEntity<?> sendAccountActivationCode(@RequestBody User user) throws Exception{
        logger.debug("Call : Send account reset code");
        boolean result = userService.sendAccountActivationCode(user);

        return ResponseEntity.ok(result);
    }

    /**
     * Activate an account when creating it with the activation code
     * @param user
     * @return
     * @throws Exception
     */
    @PermitAll
    @PostMapping(value = "register/activation")
    public ResponseEntity<?> accountActivation(@RequestBody User user) throws Exception{
        logger.debug("Call : Account Activation");
        boolean result = userService.accountActivation(user);

        return ResponseEntity.ok(result);
    }

    /**
     * Send password reset code
     * @param user
     * @return
     * @throws Exception
     */
    @PermitAll
    @PostMapping(value = "reset-password/send-code")
    public ResponseEntity<?> sendPasswordResetCode(@RequestBody User user) throws Exception{
        logger.debug("Call : Sent Password Reset Code");
        user = userService.sendPasswordResetCode(user);

        return ResponseEntity.ok(user);
    }

    /**
     * Validate the activation code when resetting the password
     * @param user
     * @return
     * @throws Exception
     */
    @PermitAll
    @PostMapping(value = "reset-password/validation")
    public ResponseEntity<?> passwordValidation(@RequestBody User user) throws Exception{
        logger.debug("Call : Validation Reset Code");
        boolean result = userService.passwordValidation(user);

        return ResponseEntity.ok(result);
    }

    /**
     * Validate the activation code when resetting the password
     * @param user
     * @return
     * @throws Exception
     */
    @PermitAll
    @PutMapping(value = "edit")
    public ResponseEntity<?> editAccount(@RequestBody User user) throws Exception{
        logger.debug("Call : Edit personnal informations");
        boolean result = userService.editData(user);

        return ResponseEntity.ok(result);
    }

}

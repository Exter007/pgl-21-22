package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
import com.pgl.utils.Code;
import com.pgl.utils.ContextName;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service()
public class UserService<P extends User> {
    protected static Logger logger;

    @Autowired
    public ApplicationClientService applicationClientService;

    @Autowired
    public FinancialInstitutionService financialInstitutionService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ContextName contextName;

    @Autowired
    EmailService emailService;


    public UserService() {
    }

    public User findByLogin(String login){
        if (this.contextName.equals(ContextName.CLIENT) ){
            return applicationClientService.getRepository().findByLogin(login);
        }else if(this.contextName.equals(ContextName.INSTITUTION)){
            return financialInstitutionService.getRepository().findByLogin(login);
        }
        return null;
    }

    /**
     * Send mail for User account validation when creating a new user account
     * @param user
     * @throws Exception
     */
    public void sendValidateAccount(User user)throws Exception{
        String mailSubject = "Validation de votre compte";
        String token = user.getToken();
        String message = "Bonjour,\n\n Votre compte a bien étè créé.\n Veuillez utiliser ce code pour valider votre compte : "
                + token + "\n\n Merci,";

        sendMail(user.getEmail(), mailSubject, message);
    }

    /**
     * Create and send a user account activation code
     * @param user
     * @return
     * @throws Exception
     */
    public boolean sendAccountActivationCode(User user) throws Exception {

        String token = Code.generateCode();
        String mailSubject = "Activation du compte";
        String message = "Bonjour,\n\n Veuillez utiliser ce code pour activer votre compte: "+ token;
        sendMail(user.getEmail(), mailSubject, message);
        user.setToken(token);
        updateTokenOrStatusOrPwd(user);

        return true;
    }

    /**
     * Create and send a user password reset code
     * @param user
     * @return
     * @throws Exception
     */
    public User sendPasswordResetCode(User user) throws Exception {

        String token = Code.generateCode();
        user.setToken(token);
        User user2 = updateTokenOrStatusOrPwd(user);

        String mailSubject = "Réinitialisation du mot de passe";
        String message = "Bonjour,\n\n Veuillez utiliser ce code pour réinitialiser votre mot de passe: " + user2.getToken();
        sendMail(user2.getEmail(), mailSubject, message);

        return user2;
    }

    /**
     * User account activation
     * @param user
     * @return
     * @throws Exception
     */
    public boolean accountActivation(User user)throws Exception{
        if(codeValidation(user)){
            user.setActive(true);
            updateTokenOrStatusOrPwd(user);

            return true;
        }

        return false;
    }

    /**
     * User password validation
     * @param user
     * @return
     * @throws Exception
     */
    public boolean passwordValidation(User user)throws Exception{
        if(codeValidation(user)){
            String newPassword = user.getPassword();
            String hashPW = bCryptPasswordEncoder.encode(newPassword);
            user.setPassword(hashPW);
            updateTokenOrStatusOrPwd(user);

            return true;
        }

        return false;
    }

    /**
     * User code validation
     * @param user
     * @return
     */
    public boolean codeValidation(User user){
        User user2 = findByLogin(user.getLogin());
        if (user2 == null ){
            throw new RuntimeException("User not found");
        }
        if(!user.getToken().equals(user2.getToken())){
            throw new RuntimeException("Reset code is incorrect");
        }

        return true;
    }

    /**
     * Send an email to an existing user
     * @param receiverAdress
     * @param mailSubject
     * @param message
     */
    public void sendMail(String receiverAdress, String mailSubject, String message){
            emailService.sendSimpleMessage(receiverAdress, mailSubject, message);
    }

    /**
     * Update token or the activation status or password of a User account
     * @param user
     * @throws Exception
     */
    private User updateTokenOrStatusOrPwd(User user) throws Exception {
        if (this.contextName.name().equals(ContextName.CLIENT.name())){
            ApplicationClient userFound = applicationClientService.getRepository()
                    .findByLogin(user.getLogin());
            if (user.getToken() != null){
                userFound.setToken(user.getToken());
            }
            if (user.getPassword() != null){
                userFound.setPassword(user.getPassword());
            }
            if (!userFound.getActive()){
                userFound.setActive(user.getActive());
            }
            return applicationClientService.saveClient(userFound);

        }else if(this.contextName.name().equals(ContextName.INSTITUTION.name())){
            FinancialInstitution userFound = financialInstitutionService.getRepository()
                    .findByLogin(user.getLogin());

            if (user.getToken() != null){
                userFound.setToken(user.getToken());
            }
            if (user.getPassword() != null){
                userFound.setPassword(user.getPassword());
            }
            if (!userFound.getActive()) {
                userFound.setActive(user.getActive());
            }
            return financialInstitutionService.saveClient(userFound);
        }
        return null;
    }

}

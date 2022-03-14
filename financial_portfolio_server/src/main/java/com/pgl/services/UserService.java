package com.pgl.services;

import com.pgl.models.User;
import com.pgl.utils.ContextName;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class UserService<P extends User> {
    protected static Logger logger;

    @Autowired
    public ApplicationClientService applicationClientService;

    @Autowired
    public FinancialInstitutionService financialInstitutionService;


    public UserService() {
    }

    public User findByLogin(String login, String contextName){
        if (contextName == ContextName.CLIENT.name()){
            return applicationClientService.getRepository().findByLogin(login);
        }else if(contextName == ContextName.INSTITUTION.name()){
            return financialInstitutionService.getRepository().findByLogin(login);
        }
        return null;
    }

//    /**
//     * Send mail for User account validation when creating a new user account
//     * @param user
//     * @throws Exception
//     */
//    public void sendValidateAccount(User user)throws Exception{
//        String mailSubject = "Validation de votre compte";
//        int code = user.getValidateCode();
//        String message = "Bonjour,\n\n Votre compte a bien étè créé.\n Veuillez utiliser ce code pour valider votre compte : "
//                +code + "\n\n Merci,";
//
//        sendMail(user.getEmail(), mailSubject, message);
//        user = findUserByUsernameOrEmail(user.getEmail());
//        user.setValidateCode(code);
//        saveUser(user, false);
//    }
//
//    /**
//     * Create and send a user account activation code
//     * @param user
//     * @return
//     * @throws Exception
//     */
//    public boolean sendAccountResetCode(User user) throws Exception {
//
//        int code = 10000 + (int) (Math.random()*(99999-10000));
//        String mailSubject = "Activation du compte";
//        String message = "Bonjour,\n\n Veuillez utiliser ce code pour activer votre compte: "+code;
//        sendMail(user.getEmail(), mailSubject, message);
//
//        user = findUserByUsernameOrEmail(user.getEmail());
//        user.setValidateCode(code);
//        saveUser(user, false);
//
//        return true;
//    }
//
//    /**
//     * Create and send a user password reset code
//     * @param user
//     * @return
//     * @throws Exception
//     */
//    public boolean sendPasswordResetCode(User user) throws Exception {
//
//        int code = 10000 + (int) (Math.random()*(99999-10000));
//        String mailSubject = "Réinitialisation du mot de passe";
//        String message = "Bonjour,\n\n Veuillez utiliser ce code pour réinitialiser votre mot de passe: "+code;
//        sendMail(user.getEmail(), mailSubject, message);
//
//        user = findUserByUsernameOrEmail(user.getEmail());
//        user.setValidateCode(code);
//        saveUser(user, false);
//
//        return true;
//    }
//
//    /**
//     * User account activation
//     * @param user
//     * @return
//     * @throws Exception
//     */
//    public boolean accountActivation(User user)throws Exception{
//        if(codeValidation(user) == true){
//            user = findUserByUsernameOrEmail(user.getEmail());
//            user.setActive(true);
//            saveUser(user, false);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * User password validation
//     * @param user
//     * @return
//     * @throws Exception
//     */
//    public boolean passwordValidation(User user)throws Exception{
//        if(codeValidation(user) == true){
//            String newPassword = user.getPassword();
//            user = findUserByUsernameOrEmail(user.getEmail());
//            String hashPW = bCryptPasswordEncoder.encode(newPassword);
//            user.setPassword(hashPW);
//            saveUser(user, false);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * User code validation
//     * @param user
//     * @return
//     */
//    public boolean codeValidation(User user){
//        User user2 = findUserByUsernameOrEmail(user.getEmail());
//        if(user.getValidateCode() != user2.getValidateCode()){
//            throw new RuntimeException("Reset code is incorrect");
//        }
//
//        return true;
//    }
//
//    /**
//     * Send an email to an existing user
//     * @param receiverAdress
//     * @param mailSubject
//     * @param message
//     * @throws Exception
//     */
//    public void sendMail(String receiverAdress, String mailSubject, String message)throws Exception{
//        try {
//            SendMail.sendToSingleReceiver(receiverAdress, mailSubject, message);
//        }
//        catch (MessagingException ex) {
//            ex.printStackTrace();
//        }
//    }
}

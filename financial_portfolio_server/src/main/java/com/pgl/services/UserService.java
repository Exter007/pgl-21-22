package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
import org.slf4j.Logger;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Optional;

@Service()
public class UserService<P extends User> {
    protected static Logger logger;
    Object user;
    Class className;
    Optional<ApplicationClient> applicationClient;
    Optional<FinancialInstitution> financialInstitution;

    @Autowired
    ApplicationClientService applicationClientService;

    @Autowired
    FinancialInstitutionService financialInstitutionService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService() {
    }

    public UserService(Class className) {
        this.className = className;
        if( className == ApplicationClient.class){
            this.applicationClient = Optional.of((ApplicationClient) user);
        }else if (className == FinancialInstitution.class){
            this.financialInstitution = Optional.of((FinancialInstitution) user);
        }
    }

    CrudRepository<? extends User, String> getRepository(){
        if( className  == ApplicationClient.class){
            return applicationClientService.getRepository();
        }else if (className  == FinancialInstitution.class){
            return financialInstitutionService.getRepository();
        }

        return null;
    }

    /**
     * Find user by username or email
     * @param id
     * @return
     */
    public Object findUserById(String id) {
        logger.debug("Entering findUserById()");

        if( className == ApplicationClient.class){
            return applicationClient = applicationClientService.getRepository()
                    .findById(id);
        } else if( className == FinancialInstitution.class){
            return financialInstitution = financialInstitutionService.getRepository()
                    .findById(id);
        }

        return user;
    }


    /**
     * Build or modify and save user
     * @param user
     * @return
     * @throws Exception
     */
    public Object saveUser(P user) throws Exception {

        Object userFound = null;
        String id = null;

        if( className == ApplicationClient.class){
            applicationClient= Optional.of((ApplicationClient) user);
            id = applicationClient.get().getNationalRegister();
            userFound = findUserById(id);
        } else if( className == FinancialInstitution.class){
            financialInstitution= Optional.of((FinancialInstitution) user);
            id = financialInstitution.get().getBIC();
            userFound = findUserById(id);
        }

        //      If a User already exists with this login
        if(id == null && userFound != null)
            throw new RuntimeException("This User already exists");

        //      If is a new User
        if (userFound == null) {
            user.setCreationDate(new Date());
            userFound = SerializationUtils.clone(user);
//            userFound = user;
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            if( className == ApplicationClient.class){
                applicationClient = (Optional<ApplicationClient>) userFound;
                applicationClient.get().setPassword(hashPW);
                return saveUser2(applicationClient);

            }else if( className == FinancialInstitution.class){
                financialInstitution = (Optional<FinancialInstitution>) userFound;
                financialInstitution.get().setPassword(hashPW);
               return saveUser2(financialInstitution);
            }

//            if(isProvider){
//                userFound = addRoleToUser(userFound, User.ROLE_NAME.PROVIDER.name());
//            }else{
//                userFound = addRoleToUser(userFound, User.ROLE_NAME.USER.name());
//            }

        }else {
            if( className == ApplicationClient.class){
                applicationClient = (Optional<ApplicationClient>) userFound;
                applicationClient.get().setModificationDate(new Date());
                return saveUser2(applicationClient);
            }else if( className == FinancialInstitution.class){
                financialInstitution = (Optional<FinancialInstitution>) userFound;
                applicationClient.get().setModificationDate(new Date());
                return saveUser2(financialInstitution);
            }
        }

        return null;
    }

    private Object saveUser2(Object user){
        if( className == ApplicationClient.class){
            try {
                return applicationClientService.getRepository().save((ApplicationClient) user);
            } catch(Exception ex) {
                throw new RuntimeException("save.fail.data.integrity.violation.error");
            }
        } else if( className == FinancialInstitution.class){
            try {
                return financialInstitutionService.getRepository().save((FinancialInstitution) user);
            } catch(Exception ex) {
                throw new RuntimeException("save.fail.data.integrity.violation.error");
            }
        }
        return null;
    }

//    public User addRoleToUser(User user, String roleName) throws Exception{
//        Role role = roleService.findByRoleName(roleName);
//        System.out.println(role.getRoleName());
//
//        user.setRole(role);
//        return user;
//    }

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

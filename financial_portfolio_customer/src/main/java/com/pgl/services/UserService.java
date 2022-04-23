package com.pgl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgl.controllers.RegisterController;
import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.utils.GlobalStage;
import com.pgl.utils.GlobalVariables;
import com.pgl.utils.JwtResponse;
import com.pgl.utils.LoginRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    HttpClientService<ApplicationClient> httpClientService = new HttpClientService<ApplicationClient>();

    RestTemplate restTemplate = new RestTemplate();

    private static ApplicationClient currentUser;

    public UserService(){}

    public ApplicationClient getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ApplicationClient currentUser) {
        UserService.currentUser = currentUser;
    }

    /**
     * Connect a user
     * @param username
     * @param password
     * @return a boolean status login
     */
    public boolean login(String username, String password){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/login");

        // create user authentication object
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        // create headers specifying that it is JSON request
        httpClientService.initHeaders();
        HttpHeaders authenticationHeaders = httpClientService.getHeaders();
        HttpEntity<LoginRequest> authenticationEntity = new HttpEntity<>(loginRequest,
                authenticationHeaders);

        try{
            // Authenticate User and get JWT
            ResponseEntity<JwtResponse> response = restTemplate.exchange(url,
                    HttpMethod.POST, authenticationEntity, JwtResponse.class);

            currentUser = new ObjectMapper().convertValue(Objects.requireNonNull(response.getBody()).getUser(), ApplicationClient.class);
            String token = "Bearer " + response.getBody().getAccessToken();
            HttpHeaders headers = httpClientService.getHeaders();
            headers.set("Authorization", token);
            httpClientService.setHeaders(headers);

            return true;

        }catch (HttpClientErrorException ex){
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());
            if(Objects.requireNonNull(ex.getMessage()).contains("Bad credentials")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Les données fournies ne sont pas correctes");
                alert.showAndWait();
            } else if(ex.getMessage().contains("Account not activated")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Compte non activé");
                alert.setContentText("Veuillez valider votre compte avec le code envoyé par email");
                alert.showAndWait();
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Client-AccountValidation.fxml")));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);
                } catch (IOException exc) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, exc);
                }
            } else {
                System.out.println("Error: "+ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur inconnue ! Veuillez contacter un administrateur");
                alert.showAndWait();
            }
        } catch(Exception ex) {
            showException(ex);
        }

        ApplicationClient user = new ApplicationClient();
        user.setLogin(username);
        currentUser = user;

        return false;
    }

    /**
     * Connect a user
     * @param cardNumber
     * @param pin
     * @return a boolean status login
     */
    public boolean loginWithCard(String cardNumber, String pin){
        String url = GlobalVariables.CONTEXT_PATH_CARD.concat("/login");

        // create user authentication object
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(cardNumber);
        loginRequest.setPassword(pin);

        // create headers specifying that it is JSON request
        httpClientService.initHeaders();
        HttpHeaders authenticationHeaders = httpClientService.getHeaders();
        HttpEntity<LoginRequest> authenticationEntity = new HttpEntity<>(loginRequest, authenticationHeaders);

        try{
            // Authenticate User and get JWT
            ResponseEntity<JwtResponse> response = restTemplate.exchange(url, HttpMethod.POST, authenticationEntity, JwtResponse.class);
            currentUser = new ObjectMapper().convertValue(Objects.requireNonNull(response.getBody()).getUser(), ApplicationClient.class);
            String token = "Bearer " + response.getBody().getAccessToken();
            HttpHeaders headers = httpClientService.getHeaders();
            headers.set("Authorization", token);
            httpClientService.setHeaders(headers);

            return true;

        }catch (HttpClientErrorException ex){
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());
        }catch(Exception ex) {
            showException(ex);
        }

        return false;
    }

    /**
     * Disconnect a user
     */
    public void logout(){
        httpClientService.initHeaders();
        currentUser = null;
    }

    /**
     * Register a user
     * @param user
     * @return registered user
     */
    public ApplicationClient register(ApplicationClient user){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/register/client");

		httpClientService.initHeaders();
        HttpEntity<Object> httpEntity = getHttpEntity(user);

        try {
            ResponseEntity<ApplicationClient> response = restTemplate.exchange(url, HttpMethod.POST,
                    httpEntity, ApplicationClient.class);

            System.out.println(response);

            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Compte créé avec succès");
            alert.setContentText("Un mail de confirmation vous a été envoyé. \n Veuillez valider votre compte pour vous connectez");
            alert.showAndWait();

            return response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (Objects.requireNonNull(ex.getMessage()).contains("This User already exists")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Un compte avec ce numéro de registre existe déjà");
                alert.showAndWait();
            } else if (ex.getMessage().contains("MailSendException")) {
                System.out.println("Error : "+ ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur lors de l'envoi du mail de confirmation \n Veuillez vous connectez pour activer votre compte");
                alert.showAndWait();
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Client-Login.fxml")));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);
                } catch (IOException exc) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, exc);
                }
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }

        return null;
    }

    /**
     * Send verification code for password reset
     * @param user
     * @return a boolean status result
     */
    public User sendPasswordResetCode(User user){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/reset-password/send-code");
        System.out.println("url: "+url);

        httpClientService.initHeaders();
        HttpEntity<Object> httpEntity = getHttpEntity(user);

        try {
            ResponseEntity<ApplicationClient> response = restTemplate.exchange(url, HttpMethod.POST,
                    httpEntity, ApplicationClient.class);
            System.out.println(response.getStatusCode());

            return currentUser = response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());
            if(ex.getMessage().contains("User not found")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Compte inexistant");
                alert.showAndWait();
            }else if (Objects.requireNonNull(ex.getMessage()).contains("MailSendException")) {
                showMailException();
            } else {
               showOtherException();
            }
        } catch(Exception ex) {
            showException(ex);
        }

        return null;
    }

    /**
     * Reset user password
     * @param user
     * @return a boolean status result
     */
    public boolean resetPassword(User user){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/reset-password/validation");
        System.out.println("url: "+url);

        httpClientService.initHeaders();
        HttpEntity<Object> httpEntity = getHttpEntity(user);

        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST,
                    httpEntity, boolean.class);

            System.out.println(response.getStatusCode());

            return Boolean.TRUE.equals(response.getBody());

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (Objects.requireNonNull(ex.getMessage()).contains("User not found")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Pas de compte associé à ces données");
                alert.showAndWait();
            }else if (ex.getMessage().contains("Reset code is incorrect")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Le code saisi est incorrect");
                alert.showAndWait();
            }else if (ex.getMessage().contains("MailSendException")) {
                showMailException();
            }else {
               showOtherException();
            }

        }catch(Exception ex) {
            showException(ex);
        }

        return false;
    }

    /**
     *Account activation
     * @param user
     * @return a boolean status result
     */
    public boolean accountActivation(User user){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/register/activation");
        System.out.println("url: "+url);

        httpClientService.initHeaders();
        HttpEntity<Object> httpEntity = getHttpEntity(user);

        try{
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST,
                    httpEntity, boolean.class);

            System.out.println(response.getStatusCode());

            return Boolean.TRUE.equals(response.getBody());

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (Objects.requireNonNull(ex.getMessage()).contains("Reset code is incorrect")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur lors de la validation du mot de passe");
                alert.setHeaderText("Le code saisi est incorrect");
                alert.showAndWait();
            } else if(ex.getMessage().contains("MailSendException")) {
                showMailException();
            } else {
               showOtherException();
            }

        } catch(Exception ex) {
            showException(ex);
        }

        return false;
    }

    public HttpEntity<Object> getHttpEntity(Object entity){
        HttpHeaders headers = httpClientService.getHeaders();
        return new HttpEntity<>(entity, headers);
    }

    public void showMailException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erreur lors de l'envoi du mail de confirmation");
        alert.showAndWait();
    }

    public void showException(Exception ex){
        if (ex.getMessage().contains("Connection refused: connect")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Serveur indisponible");
            alert.showAndWait();
        } else {
            showOtherException();
        }
    }

    public void showOtherException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erreur inconnue ! Veuillez contacter un administrateur");
        alert.showAndWait();
    }
}

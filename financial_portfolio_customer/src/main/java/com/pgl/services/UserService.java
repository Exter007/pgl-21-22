package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.utils.GlobalVariables;
import com.pgl.utils.JwtResponse;
import com.pgl.utils.LoginRequest;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.inject.Inject;

public class UserService {

    @Inject
    HttpClientService httpClientService = new HttpClientService<ApplicationClient>();

    RestTemplate restTemplate = new RestTemplate();

    private static ApplicationClient currentUser;

    public UserService(){}

    public static ApplicationClient getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(ApplicationClient currentUser) {
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
        HttpHeaders authenticationHeaders = httpClientService.getHeaders();
        HttpEntity<LoginRequest> authenticationEntity = new HttpEntity<>(loginRequest,
                authenticationHeaders);

        // Authenticate User and get JWT
        ResponseEntity<JwtResponse> response = restTemplate.exchange(url,
                HttpMethod.POST, authenticationEntity, JwtResponse.class);

        String token = "Bearer " + response.getBody().getAccessToken();
        HttpHeaders headers = httpClientService.getHeaders();
        headers.set("Authorization", token);
        httpClientService.setHeaders(headers);

        // if the authentication is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            String error= response.toString();
            System.out.println(response);
            if(response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                if(error.equals("Account not activated")){
                    alert.setHeaderText("Compte non activé");
                    alert.setContentText("Veuillez le validé avec le code envoyé à votre email");
                }else{
                    alert.setHeaderText("Email ou mot de passe incorrect");
                }
                alert.showAndWait();
            }

            System.out.println("Failed with HTTP Error code: " + response.getStatusCode());

            System.out.println("Error: "+error);

            return false;

        }

        return true;
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

        HttpEntity<ApplicationClient> httpEntity = getHttpEntity(user);

        ResponseEntity<ApplicationClient> response = restTemplate.exchange(url, HttpMethod.POST,
                httpEntity, ApplicationClient.class);

        System.out.println(response);

        Alert alert;
        // if the registration is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            String error= response.toString();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'enregistrement");
            if(error.equals("This User already exists")){
                alert.setHeaderText("Erreur email");
                alert.setContentText("Un compte avec cet email existe deja");
            } else{
                alert.setHeaderText("Erreur lors de la création du compte");
            }
            alert.showAndWait();
            return null;
        }
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Client enregistré avec succés");
        alert.setContentText("Une mail de confirmation vous a étè envoyè.  Veuillez validè votre compte pour vous connectez");
        alert.showAndWait();

        return response.getBody();
    }



    public void requestSecu(){
//        String token = "Bearer " + authenticationResponse.getBody().getAccessToken();
//        HttpHeaders headers = getHeaders();
//        headers.set("Authorization", token);
//        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
//        // Use Token to get Response
//        ResponseEntity<String> helloResponse = restTemplate.exchange(HELLO_URL, HttpMethod.GET, jwtEntity,
//                String.class);
//        if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
//            response = helloResponse.getBody();
//        }
    }

    /**
     * Send verification code for password reset
     * @param user
     * @return a boolean status result
     */
    public boolean sendPasswordResetCode(ApplicationClient user){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/reset-password/send-code");
        System.out.println("url: "+url);

        HttpEntity<ApplicationClient> httpEntity = getHttpEntity(user);

        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST,
                httpEntity, boolean.class);

        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'envoi du code de reinitialisation");
            alert.showAndWait();

            System.out.println("Failed : HTTP error code : " + response.getStatusCode());

            String error= response.getBody().toString();
            System.out.println("Error: "+error);

            return false;
        }

        return response.getBody();

    }

    /**
     * Reset user password
     * @param user
     * @return a boolean status result
     */
    public boolean resetPassword(ApplicationClient user){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/reset-password/validation");
        System.out.println("url: "+url);

        HttpEntity<ApplicationClient> httpEntity = getHttpEntity(user);

        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST,
                httpEntity, boolean.class);

        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Failed : HTTP error code : " + response.getStatusCode());

            String error= response.getBody().toString();
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de la validation du mot de passe");
            if(error.equals("Reset code is incorrect")){
                alert.setContentText("Le code saisi est incorrect");
            }
            alert.showAndWait();


            return false;
        }

        return response.getBody();
    }

    /**
     * Send account reset code
     * @param user
     * @return a boolean status result
     */
    public boolean sendAccountResetCode(ApplicationClient user){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/register/send-code");
        System.out.println("url: "+url);

        HttpEntity<ApplicationClient> httpEntity = getHttpEntity(user);

        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST,
                httpEntity, boolean.class);

        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'envoi du code d'activation");
            alert.showAndWait();

            System.out.println("Failed : HTTP error code : " + response.getStatusCode());

            String error= response.getBody().toString();
            System.out.println("Error: "+error);

            return false;
        }

        return response.getBody();

    }

    /**
     *Account activation
     * @param user
     * @return a boolean status result
     */
    public boolean accountActivation(ApplicationClient user){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/register/activation");
        System.out.println("url: "+url);

        HttpEntity<ApplicationClient> httpEntity = getHttpEntity(user);

        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST,
                httpEntity, boolean.class);

        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'activation du compte ");
            alert.showAndWait();

            System.out.println("Failed : HTTP error code : " + response.getStatusCode());

            String error= response.getBody().toString();
            System.out.println("Error: "+error);

            return false;
        }

        return response.getBody();
    }

    public HttpEntity getHttpEntity(ApplicationClient entity){
        HttpHeaders headers = httpClientService.getHeaders();
        HttpEntity<ApplicationClient> httpEntity = new HttpEntity<>(entity, headers);
        return httpEntity;
    }

}

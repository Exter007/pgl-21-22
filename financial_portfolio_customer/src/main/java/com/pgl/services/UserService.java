package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.utils.ContextName;
import com.pgl.utils.GlobalVariables;
import javafx.scene.control.Alert;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserService {

    public WebTarget webTarget;
    protected ClientConfig config = new ClientConfig();
    private Client client = ClientBuilder.newClient(config);
    public static HttpAuthenticationFeature feature;
    private static ApplicationClient currentUser;

    public UserService(){}

    public static ApplicationClient getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(ApplicationClient currentUser) {
        UserService.currentUser = currentUser;
    }

    /**
     * User login
     * @param username
     * @param password
     * @return
     */
    public ApplicationClient login(String username, String password){
        feature = HttpAuthenticationFeature.basic(username, password);
        client = ClientBuilder.newClient(config);
        client.register(feature);
        client.property("contextName", ContextName.CLIENT);

        String url = GlobalVariables.CONTEXT_PATH.concat("/account");
        System.out.println("url: "+url);

        webTarget = client.target(url).path("login2");

        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).get(Response.class);
        System.out.println(response);

        // Status 200 or 201 is successful.
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            String error= response.readEntity(String.class);

            if(response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {

                if(error.equals("Account not activated")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Compte non activé");
                    alert.setContentText("Veuillez le validé avec le code envoyé à votre email");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
                    alert.setHeaderText("Email ou mot de passe incorrect");
//                alert.setContentText("Email ou mot de passe incorrect");
                    alert.showAndWait();
                }

            }

            System.out.println("Failed with HTTP Error code: " + response.getStatus());

            System.out.println("Error: "+error);

            return null;
        }

        return currentUser = response.readEntity(ApplicationClient.class);
    }

    /**
     * User logout
     */
    public void logout(){
        client = ClientBuilder.newClient(config);
//        feature = HttpAuthenticationFeature.digest();
        feature = HttpAuthenticationFeature.basic("","");
        currentUser = null;
    }

    /**
     * Register user
     * @param entity
     * @return
     */
    public ApplicationClient register(ApplicationClient entity){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/register/client");
        System.out.println("url: "+url);
        client.property("contextName", ContextName.CLIENT);

        webTarget = client.target(url);

        Response response = webTarget
                .request()
                .post(Entity.entity(entity,MediaType.APPLICATION_JSON),Response.class);

        System.out.println(response.getStatus());
//        System.out.println(response.readEntity(String.class));

//        Status 200 or 201 is successful.
        if (response.getStatus() != 200 && response.getStatus() != 201) {

            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(error.equals("This User already exists")){
                alert.setHeaderText("Erreur email");
                alert.setContentText("Un compte avec cet email existe deja");
                alert.showAndWait();
            }
            else{
                alert.setHeaderText("Erreur lors de la création du compte");
                alert.showAndWait();
            }

            System.out.println("Failed : HTTP error code : " + response.getStatus());

            return null;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Error");
        alert.setHeaderText("Compte créé avec succès");
        alert.setContentText("Une mail de confirmation vous a étè envoyè.  Veuillez validè votre compte pour vous connectez");
        alert.showAndWait();

        return response.readEntity(ApplicationClient.class);
    }

    /**
     * Send verification code for password reset
     * @param entity
     * @return
     */
    public boolean sendPasswordResetCode(ApplicationClient entity){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/reset-password/send-code");
        System.out.println("url: "+url);

        webTarget = client.target(url);

        Response response = webTarget
                .request()
                .post(Entity.entity(entity,MediaType.APPLICATION_JSON),Response.class);

        System.out.println(response.getStatus());

//        Status 200 or 201 is successful.
        if (response.getStatus() != 200 && response.getStatus() != 201) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'envoi du code de reinitialisation");
            alert.showAndWait();

            System.out.println("Failed : HTTP error code : " + response.getStatus());

            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            return false;
        }

        return response.readEntity(boolean.class);

    }

    /**
     * Reset user password
     * @param entity
     * @return
     */
    public boolean resetPassword(ApplicationClient entity){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/reset-password/validation");
        System.out.println("url: "+url);

        webTarget = client.target(url);

        Response response = webTarget
                .request()
                .post(Entity.entity(entity,MediaType.APPLICATION_JSON),Response.class);

        System.out.println(response.getStatus());

//        Status 200 or 201 is successful.
        if (response.getStatus() != 200 && response.getStatus() != 201) {
            System.out.println("Failed : HTTP error code : " + response.getStatus());

            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de la validation du mot de passe");
            if(error.equals("Reset code is incorrect")){
                alert.setContentText("Le code saisi est incorrect");
                alert.showAndWait();
            }else{
                alert.showAndWait();
            }




            return false;
        }

        return response.readEntity(boolean.class);
    }

    /**
     * Send account reset code
     * @param entity
     * @return
     */
    public boolean sendAccountResetCode(ApplicationClient entity){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/register/send-code");
        System.out.println("url: "+url);

        webTarget = client.target(url);

        Response response = webTarget
                .request()
                .post(Entity.entity(entity,MediaType.APPLICATION_JSON),Response.class);

        System.out.println(response.getStatus());

//        Status 200 or 201 is successful.
        if (response.getStatus() != 200 && response.getStatus() != 201) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'envoi du code d'activation");
            alert.showAndWait();

            System.out.println("Failed : HTTP error code : " + response.getStatus());

            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            return false;
        }

        return response.readEntity(boolean.class);

    }

    /**
     *Account activation
     * @param entity
     * @return
     */
    public boolean accountActivation(ApplicationClient entity){
        String url = GlobalVariables.CONTEXT_PATH.concat("/account/register/activation");
        System.out.println("url: "+url);

        webTarget = client.target(url);

        Response response = webTarget
                .request()
                .post(Entity.entity(entity,MediaType.APPLICATION_JSON),Response.class);

        System.out.println(response.getStatus());

//        Status 200 or 201 is successful.
        if (response.getStatus() != 200 && response.getStatus() != 201) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'activation du compte ");
            alert.showAndWait();

            System.out.println("Failed : HTTP error code : " + response.getStatus());

            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            return false;
        }

        return response.readEntity(boolean.class);
    }


    public HttpAuthenticationFeature getFeature(){
        return feature;
    }

}

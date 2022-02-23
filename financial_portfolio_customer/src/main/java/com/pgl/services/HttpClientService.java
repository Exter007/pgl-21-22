package com.pgl.services;

import com.pgl.models.Persistent;
import com.pgl.utils.GlobalVariables;

import javax.inject.Inject;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import javafx.scene.control.Alert;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

public class HttpClientService<P extends Persistent>{

    @Inject
    UserService userService = new UserService();

    public WebTarget webTarget;
    protected GenericType<P> genericP;
    protected GenericType<List<P>> genericsP;
    protected ClientConfig config = new ClientConfig();
    private Client client = ClientBuilder.newClient(config);
    /**
     * Reference du chemin de l'objet pour le mappage avec les services webs
     */
    private String referencePath;


    public HttpClientService( GenericType<P> genericP, GenericType<List<P>> genericsP, String referencePath){
        this.genericP = genericP;
        this.genericsP = genericsP;
        this.referencePath = referencePath;
    }

    public P save(P entity){
        String savePath = "/save";
        String url = GlobalVariables.CONTEXT_PATH_PORTFOLIO.concat(referencePath).concat(savePath);
        System.out.println("url: "+url);

        client.register(userService.getFeature());
        webTarget = client.target(url);

        Response response = webTarget
                .request()
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON),Response.class);

        System.out.println(response.getStatus());

        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            System.out.println("Failed : HTTP error code : " + response.getStatus());

            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);
            Alert alert = new Alert(Alert.AlertType.ERROR);

            if(error.equals("save.fail.data.integrity.violation.duplicate.error")){
                alert.setHeaderText("Erreur duplication");
                alert.setContentText("L'élément existe déjà");
            }else{
                alert.setHeaderText("Erreur lors de la sauvegarde");
//            alert.setContentText(error);
            }
            alert.showAndWait();

            return null;
        }

//        GenericType<P> genericP = new GenericType<P>() {};

        return response.readEntity(genericP);
    }

    public P findById(Long id){
        String findByIdPath = "/find-by-id/";
        String url = GlobalVariables.CONTEXT_PATH_PORTFOLIO + referencePath + findByIdPath + id;
        System.out.println(url);

        client.register(userService.getFeature());
        webTarget = client.target(url);
        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).get(Response.class);


        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de la consultation");
//            alert.setContentText(error);
            alert.showAndWait();

            return null;
        }

        return (P)response.readEntity(genericP);
    }

    public boolean deleteById(Long id) {
        String deleteByIdPath = "/delete-by-id/";

        String url = GlobalVariables.CONTEXT_PATH_PORTFOLIO + referencePath + deleteByIdPath + id;

        client.register(userService.getFeature());
        webTarget = client.target(url);

        Response response = webTarget.request().delete();

        System.out.println(response.getStatus());

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de la suppression");
//            alert.setContentText(error);
            alert.showAndWait();

            return false;
        }

        return true;

    }

    public List<P> getList() {
        String listPath = "/list";

        String url = GlobalVariables.CONTEXT_PATH_PORTFOLIO + referencePath + listPath;

        client.register(userService.getFeature());
        webTarget = client.target(url);

        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).get(Response.class);

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de listing");
//            alert.setContentText(error);
            alert.showAndWait();

            return null;
        }
//        GenericType<List<P>> genericsP = new GenericType<>() {};

        return response.readEntity(genericsP);
    }

    public P post(String path, P entity){
        System.out.println("url: "+path);

        client.register(userService.getFeature());
        webTarget = client.target(path);

        Response response = webTarget
                .request()
                .post(Entity.entity(entity,MediaType.APPLICATION_JSON),Response.class);

        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));

//        Status 200 or 201 is successful.
        if (response.getStatus() != 200 && response.getStatus() != 201) {
            System.out.println("Failed : HTTP error code : " + response.getStatus());

            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'opération");
//            alert.setContentText(error);
            alert.showAndWait();

            return null;
        }

//        GenericType<P> genericP = new GenericType<P>() {};

        return response.readEntity(genericP);
    }

    public P get(String path) {

        client.register(userService.getFeature());

        webTarget = client.target(path);

        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).get(Response.class);
//        System.out.println(response);

        // Status 200 or 201 is successful.
        if (response.getStatus() != 200 && response.getStatus() != 201) {
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'opération");
//            alert.setContentText(error);
            alert.showAndWait();

            return null;
        }

        return response.readEntity(genericP);
    }

    public List<P> getListByURL(String url) {

        client.register(userService.getFeature());
        webTarget = client.target(url);

        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).get(Response.class);

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.readEntity(String.class);
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de listing");
//            alert.setContentText(error);
            alert.showAndWait();

            return null;
        }

        return response.readEntity(genericsP);
    }

    public void not_selected_error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Aucun élément sélectionné");
        alert.showAndWait();
    }

}

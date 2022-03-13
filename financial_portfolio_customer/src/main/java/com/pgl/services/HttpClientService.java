package com.pgl.services;

import com.pgl.models.Persistent;
import com.pgl.utils.ContextName;
import com.pgl.utils.GlobalVariables;

import javafx.scene.control.Alert;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpClientService<P>{

    RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders headers;

    /**
     * Reference du service web (url)
     */
    private String referencePath;

    public HttpClientService() {
        initHeaders();
    }

    public HttpClientService(String referencePath){
        this.referencePath = referencePath;
    }

    public void initHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("contextName", ContextName.CLIENT.name());
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        this.headers = headers;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    /**
     * Save an entity
     * @param entity
     * @return entity saved
     */
    public P save(P entity){
        String savePath = "/save";
        String url = GlobalVariables.CONTEXT_PATH_PORTFOLIO.concat(referencePath).concat(savePath);
        System.out.println("url: "+url);

        HttpEntity<P> httpEntity = getHttpEntity(entity);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST,
                httpEntity, Object.class);

        System.out.println(response.getStatusCode());

        // if creation request is not successful
        if (!response.getStatusCode().equals(HttpStatus.CREATED)) {
            System.out.println("Failed : HTTP error code : " + response.getStatusCode());

            String error= response.getBody().toString();
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

        return (P)response.getBody();
    }


    /**
     * Find an entity by id
     * @param id
     * @return entity retrieved
     */
    public P findById(Long id){
        String findByIdPath = "/find-by-id/";
        String url = GlobalVariables.CONTEXT_PATH_PORTFOLIO + referencePath + findByIdPath + id;
        System.out.println(url);

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET,
                httpEntity, Object.class);

        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Failed with HTTP Error code: " + response.getStatusCode());
            String error= response.getStatusCode().toString();
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de la consultation");
            alert.showAndWait();

            return null;
        }

        return (P)response.getBody();
    }

    /**
     * Delete an entity by id
     * @param id
     * @return a boolean status result
     */
    public boolean deleteById(Long id) {
        String deleteByIdPath = "/delete-by-id/";

        String url = GlobalVariables.CONTEXT_PATH_PORTFOLIO + referencePath + deleteByIdPath + id;

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.DELETE,
                httpEntity, Object.class);


        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Failed with HTTP Error code: " + response.getStatusCode());
            String error= response.getStatusCode().toString();
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de la suppression");
            alert.showAndWait();

            return false;
        }

        return true;

    }

    /**
     * Retrieve the full entities list
     * @return entities list retrieved
     */
    public List<P> getList() {
        String listPath = "/list";

        String url = GlobalVariables.CONTEXT_PATH_PORTFOLIO + referencePath + listPath;

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET,
                httpEntity, Object.class);

        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Failed with HTTP Error code: " + response.getStatusCode());
            String error= response.getStatusCode().toString();
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de listing");
//            alert.setContentText(error);
            alert.showAndWait();

            return null;
        }

        return (List<P>) response.getBody();
    }


    /**
     * Post request with specific url
     * @return entities posted
     */
    public P post(String url, P entity){
        System.out.println("url: "+url);

        HttpEntity<P> httpEntity = getHttpEntity(entity);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST,
                httpEntity, Object.class);

        System.out.println(response.getStatusCode());

        // if creation request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Failed : HTTP error code : " + response.getStatusCode());

            String error= response.getStatusCode().toString();
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'opération");
//            alert.setContentText(error);
            alert.showAndWait();

            return null;
        }

        return (P) response.getBody();
    }

    /**
     * Retrieve an entity with specific url
     * @return entity retrieved
     */
    public P getByURL(String url) {
        System.out.println(url);

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET,
                httpEntity, Object.class);

        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Failed with HTTP Error code: " + response.getStatusCode());
            String error= response.getStatusCode().toString();
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors de l'opération");
            alert.showAndWait();

            return null;
        }

        return (P) response.getBody();
    }

    /**
     * Retrieve full entities with specific url
     * @return entities list retrieved
     */
    public List<P> getListByURL(String url) {

        System.out.println(url);

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET,
                httpEntity, Object.class);

        System.out.println(response.getStatusCode());

        // if request is not successful
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Failed with HTTP Error code: " + response.getStatusCode());
            String error= response.getStatusCode().toString();
            System.out.println("Error: "+error);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur lors du listing");
            alert.showAndWait();

            return null;
        }

        return (List<P>) response.getBody();
    }

    public void not_selected_error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Aucun élément sélectionné");
        alert.showAndWait();
    }

    public HttpEntity getHttpEntity(P entity){
        HttpEntity<P> httpEntity = new HttpEntity<>(entity, headers);
        return httpEntity;
    }

}

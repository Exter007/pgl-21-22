package com.pgl.services;

import com.pgl.utils.ContextName;
import com.pgl.utils.GlobalVariables;
import javafx.scene.control.Alert;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpClientService<P>{

    RestTemplate restTemplate = new RestTemplate();

    private static HttpHeaders headers;

    /**
     * Reference du service web (url)
     */
    private String referencePath;

    public HttpClientService() {
    }

    public HttpClientService(String referencePath){
        this.referencePath = referencePath;
    }

    public void initHeaders() {
        headers = new HttpHeaders();
        headers.set("contextName", ContextName.INSTITUTION.name());
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        HttpClientService.headers = headers;
    }

    /**
     * Save an entity
     * @param entity
     * @return entity saved
     */
    public P save(P entity){
        String savePath = "/save";
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION.concat(referencePath).concat(savePath);
        System.out.println("url: "+url);

        HttpEntity<P> httpEntity = getHttpEntity(entity);

        try {

            ResponseEntity<P> response = (ResponseEntity<P>) restTemplate.exchange(url, HttpMethod.POST,
                    httpEntity, Object.class);

            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Elément créé avec succès");
            alert.showAndWait();

            return response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                showNotAuthException();
            } else if (ex.getMessage().contains("save.fail.data.integrity.violation.duplicate.error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur de duplication");
                alert.setContentText("L'élément existe déjà");
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }

        return null;
    }


    /**
     * Find an entity by id
     * @param id
     * @return entity retrieved
     */
    public P findById(Long id){
        String findByIdPath = "/find-by-id/";
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + findByIdPath + id;
        System.out.println(url);

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        try {

            ResponseEntity<P> response = (ResponseEntity<P>) restTemplate.exchange(url, HttpMethod.GET,
                    httpEntity, Object.class);

            return response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                showNotAuthException();
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }

        return null;
    }

    /**
     * Delete an entity by id
     * @param id
     * @return a boolean status result
     */
    public boolean deleteById(Long id) {
        String deleteByIdPath = "/delete-by-id/";

        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + deleteByIdPath + id;

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<P> response = (ResponseEntity<P>) restTemplate.exchange(url, HttpMethod.DELETE,
                    httpEntity, Object.class);

            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Elément supprimé avec succès");
            alert.showAndWait();

            return true;

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                showNotAuthException();
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }

        return false;

    }

    /**
     * Retrieve the full entities list
     * @return entities list retrieved
     */
    public List<P> getList() {
        String listPath = "/list";

        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + listPath;

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        try{

            ResponseEntity<P> response = (ResponseEntity<P>) restTemplate.exchange(url, HttpMethod.GET,
                    httpEntity, Object.class);

            System.out.println(response.getStatusCode());

            return (List<P>) response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                showNotAuthException();
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }

        return null;
    }


    /**
     * Post request with specific url
     * @return entities posted
     */
    public P post(String url, P entity){
        System.out.println("url: "+url);

        HttpEntity<P> httpEntity = getHttpEntity(entity);

        try {
            ResponseEntity<P> response = (ResponseEntity<P>) restTemplate.exchange(url, HttpMethod.POST,
                    httpEntity, Object.class);

            System.out.println(response.getStatusCode());

            return (P) response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                showNotAuthException();
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }

        return null;
    }

    /**
     * Retrieve an entity with specific url
     * @return entity retrieved
     */
    public P getByURL(String url) {
        System.out.println(url);

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<P> response = (ResponseEntity<P>) restTemplate.exchange(url, HttpMethod.GET,
                    httpEntity, Object.class);

            System.out.println(response.getStatusCode());

            return (P) response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                showNotAuthException();
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }

        return null;
    }

    /**
     * Retrieve full entities with specific url
     * @return entities list retrieved
     */
    public List<P> getListByURL(String url) {

        System.out.println(url);

        HttpEntity<P> httpEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<P> response = (ResponseEntity<P>) restTemplate.exchange(url, HttpMethod.GET,
                    httpEntity, Object.class);

            System.out.println(response.getStatusCode());

            return (List<P>) response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED) || ex.getMessage().contains("AccessDenied")) {
                showNotAuthException();
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }

        return null;
    }

    /**
     * Provide Http Entity
     * @param entity
     * @return
     */
    public HttpEntity getHttpEntity(P entity){
        HttpEntity<P> httpEntity = new HttpEntity<>(entity, headers);
        return httpEntity;
    }

    /**
     * Error when server is unavailable or unknown error
     * @param ex
     */
    public void showException(Exception ex){
        if (ex.getMessage().contains("Connection refused: connect")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Serveur indisponible");
            alert.showAndWait();
        } else {
            showOtherException();
        }
    }

    /**
     * Unauthorized access error on a resource
     */
    public void showNotAuthException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Accès non autorisé");
        alert.showAndWait();
    }

    /**
     * Error for unknown behavior
     */
    public void showOtherException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erreur inconnue ! Veuillez contacter un administrateur");
        alert.showAndWait();
    }


    /**
     * Unselected element error during an action that requires a selection before
     */
    public void not_selected_error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Aucun élément sélectionné");
        alert.showAndWait();
    }
}

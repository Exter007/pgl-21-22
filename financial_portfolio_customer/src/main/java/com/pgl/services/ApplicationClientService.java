package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class ApplicationClientService extends HttpClientService<ApplicationClient>{
    private static final String referencePath = "";
    UserService userService = new UserService();

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Application Client
     * ApplicationClient.class for deserializing JSON received from Rest API to Application Client
     */
    public ApplicationClientService() {
        super(referencePath, ApplicationClient.class,
                new ParameterizedTypeReference<List<ApplicationClient>>() {});
    }

    /**
     * Check the password of a Application Client
     * @param password
     * @return
     */
    public boolean checkPassword(String password){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/check-password";
        ApplicationClient client = userService.getCurrentUser();
        client.setPassword(password);

        return post2(url, client);
    }

    /**
     * Update Application Client
     * @param client
     * @return
     */
    public ApplicationClient updateClient(ApplicationClient client){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/update";
        return post(url, client);
    }

    /**
     * Update Application Client password
     * @param password
     * @return
     */
    public ApplicationClient updatePassword(String password){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/password/update";
        ApplicationClient client = userService.getCurrentUser();
        client.setPassword(password);
        return post(url, client);
    }

    /**
     * Update Application Client language
     * @param language
     * @return
     */
    public ApplicationClient updateLanguage(String language){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/language/update";
        ApplicationClient client = userService.getCurrentUser();
        client.setLanguage(language);
        return post(url, client);
    }
}

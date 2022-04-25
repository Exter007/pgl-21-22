package com.pgl.services.extension3;

import com.pgl.models.extension3.Category;
import com.pgl.services.HttpClientService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class CategoryService extends HttpClientService<Category> {
    private static Category currentCategory;
    UserService userService = new UserService();
    private static final String referencePath = "/category";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Application Client
     * ApplicationClient.class for deserializing JSON received from Rest API to Application Client
     */
    public CategoryService() {
        super(referencePath, Category.class,
                new ParameterizedTypeReference<List<Category>>() {});
    }

    public Category createCategory(Category category) {
        String url =  GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/save";
        return post(url, category);
    }

    public boolean deleteCategory(Category category) {
        String url =  GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/delete-by-id/" + category.getId();
        return deleteById(category.getId().toString());
    }

    public void setCurrentCategory(Category category) {
        currentCategory = category;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public List<Category> getAllCategories() {
        String url =  GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/find-by-application-client/" + userService.getCurrentUser().getNationalRegister();
        return getListByURL(url);
    }
}

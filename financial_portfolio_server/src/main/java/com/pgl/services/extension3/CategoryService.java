package com.pgl.services.extension3;
import com.pgl.models.ApplicationClient;
import com.pgl.models.extension3.Category;
import com.pgl.repositories.extension3.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        if(categoryRepository.findByName(category.getName()) == null) {
            System.out.println(category.getApplicationClient());
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }
    public List<Category> getAllCategories(String applicationClient) {
        return categoryRepository.findByApplicationClient(applicationClient);
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }
}

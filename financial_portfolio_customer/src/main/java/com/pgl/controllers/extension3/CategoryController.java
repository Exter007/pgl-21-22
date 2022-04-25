package com.pgl.controllers.extension3;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.extension3.Category;
import com.pgl.services.UserService;
import com.pgl.services.extension3.CategoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.pgl.helpers.DynamicViews.border_pane;

public class CategoryController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    private CategoryService categoryService = new CategoryService();

    @FXML
    private Label myCategories_label;
    @FXML
    private ListView categoriesListView;
    @FXML
    private Button add_btn;
    @FXML
    private Button delete_btn;

    @FXML
    private Button create_btn;
    @FXML
    private TextField name_field;
    @FXML
    private TextField color_field;
    @FXML
    private Label add_title;
    @FXML
    private Text name_text;
    @FXML
    private Text color_text;


    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        myCategories_label.setText(bundle.getString("myCategories_label"));
        add_btn.setText(bundle.getString("add_btn"));
        delete_btn.setText(bundle.getString("delete_btn"));
        create_btn.setText(bundle.getString("create_btn"));
        name_text.setText(bundle.getString("name_text"));
        color_text.setText(bundle.getString("color_text"));
        add_title.setText(bundle.getString("add_title"));

    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        if(userService.getCurrentUser().getLanguage().equals("fr")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        }else if(userService.getCurrentUser().getLanguage().equals("en")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        }else{
            bundle = null;
        }
        // SetText not working...
        //setText();

        List<Category> categories = categoryService.getAllCategories();
        if(categories.size() > 0){
            categoriesListView.setItems(FXCollections.observableArrayList(categories));
        }

    }

    @FXML
    public void selectedItem(MouseEvent mouseEvent) {
        Category category = (Category) categoriesListView.getSelectionModel().getSelectedItem();
        categoryService.setCurrentCategory(category);
    }

    public void on_add(ActionEvent actionEvent) {
        DynamicViews.loadBorderCenter(border_pane, "extension3/Client-Dashboard-Add-Category.fxml");
    }

    public void on_delete(ActionEvent actionEvent) {
        Category category = categoryService.getCurrentCategory();
        categoryService.deleteCategory(category);
    }

    public void createCategory(ActionEvent actionEvent) {
        if (!name_field.getText().isEmpty() && !color_field.getText().isEmpty()) {
            Category category = new Category();
            category.setName(name_field.getText());
            category.setColor(color_field.getText());
            categoryService.createCategory(category);
            DynamicViews.loadBorderCenter(border_pane, "extension3/Client-Dashboard-Categories.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("errorCategory"));
            alert.showAndWait();
        }
    }
}

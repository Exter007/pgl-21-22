package com.pgl.controllers.extension3;

import com.pgl.controllers.DashboardController;
import com.pgl.helpers.DynamicViews;
import com.pgl.models.extension3.Category;
import com.pgl.services.UserService;
import com.pgl.services.extension3.CategoryService;
import com.pgl.utils.GlobalStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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


    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        myCategories_label.setText(bundle.getString("myCategories_label"));
        add_btn.setText(bundle.getString("add_btn"));
        delete_btn.setText(bundle.getString("delete_btn"));
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
        setText();

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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/extension3/Client-Dashboard-Add-Category.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void on_delete(ActionEvent actionEvent) {
        Category category = categoryService.getCurrentCategory();
        categoryService.deleteCategory(category);
    }

}

package com.pgl.controllers.extension3;

import com.pgl.controllers.DashboardController;
import com.pgl.helpers.DynamicViews;
import com.pgl.models.extension3.Category;
import com.pgl.services.UserService;
import com.pgl.services.extension3.CategoryService;
import com.pgl.utils.GlobalStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.pgl.helpers.DynamicViews.border_pane;

public class AddCategoryController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    private CategoryService categoryService = new CategoryService();

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

    public void setText() {
        create_btn.setText(bundle.getString("create_btn"));
        name_text.setText(bundle.getString("name_text"));
        color_text.setText(bundle.getString("color_text"));
        add_title.setText(bundle.getString("add_title"));
    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        if (userService.getCurrentUser().getLanguage().equals("fr")) {
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        } else if (userService.getCurrentUser().getLanguage().equals("en")) {
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        } else {
            bundle = null;
        }
        setText();
    }

    public void createCategory(ActionEvent actionEvent) {
        if (!name_field.getText().isEmpty() && !color_field.getText().isEmpty()) {
            Category category = new Category();
            category.setName(name_field.getText());
            category.setColor(color_field.getText());
            category.setApplicationClient(userService.getCurrentUser());
            categoryService.createCategory(category);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/extension3/Client-Dashboard-Categories.fxml"));
                Stage newWindow = new Stage();
                Scene scene = new Scene(root);
                newWindow.setScene(scene);
                GlobalStage.setStage(newWindow);

            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("errorCategory"));
            alert.showAndWait();
        }
    }
}

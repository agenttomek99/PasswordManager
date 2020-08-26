package PasswordManagerGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Controller {

    @FXML
    private StackPane mainStackPane;

    @FXML
    public void initialize() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("loginScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            System.out.println("dupa");
        }
        LoginScreenController loginScreenController = loader.getController();
        loginScreenController.setController(this);
        setScreen(pane);
    }

    public void setScreen(Pane pane) {
        mainStackPane.getChildren().add(pane);
    }
}

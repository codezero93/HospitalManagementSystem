/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.JDBC.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Olca
 */
public class LoginController implements Initializable {

    private MainController controller;
    private Alert alert = new Alert(AlertType.ERROR);
    private static String username;
    @FXML
    private TextField unF;

    @FXML
    private PasswordField pF;

    @FXML
    private void handleAction(ActionEvent e) throws SQLException, IOException {

        String name = new DBConnect().loginCheck(unF.getText(), pF.getText());

        if (name != null) {

            MainController controller = new MainController(name);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            loader.setController(controller);
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            stage.setTitle("Home");
            stage.centerOnScreen();

        } else {
            alert.setTitle("Error");
            alert.setHeaderText("Please Enter correct username or password");
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
            unF.clear();
            pF.clear();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                unF.requestFocus();
            }
        });

    }

    public static String getUsername() {
        return username;
    }

}

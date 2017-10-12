/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.hibernate.HibernateUtils;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Olca
 */
public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene mainscene = new Scene(root);
        mainStage.getIcons().add(new Image("/pics/hospitalicon1.png"));
        mainStage.setScene(mainscene);
        mainStage.setResizable(false);
        mainStage.show();
        mainStage.centerOnScreen();
        mainStage.setOnCloseRequest((event) -> {
            HibernateUtils.closeFactory();

        });
        

    }

    @Override
    public void stop() {
        HibernateUtils.closeFactory();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

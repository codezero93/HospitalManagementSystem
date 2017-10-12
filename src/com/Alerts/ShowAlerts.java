/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Sunny
 */
public class ShowAlerts {

    private static Alert alert = new Alert(AlertType.NONE);

    public static void showErrorAlert(String Title, String headerText, String contextText) {
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle(Title);
        alert.setContentText(contextText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    
    public static void showInformationAlert(String Title, String headerText, String contextText) {
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setContentText(contextText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    
    public static void showWarningAlert(String Title, String headerText, String contextText) {
        alert.setAlertType(AlertType.WARNING);
        alert.setTitle(Title);
        alert.setContentText(contextText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    
    public static void showConfrirmationAlert(String Title, String headerText, String contextText) {
        alert.setAlertType(AlertType.CONFIRMATION);
        alert.setTitle(Title);
            alert.setContentText(contextText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    
    public static Alert getAlert(){
    return alert;
    }
}

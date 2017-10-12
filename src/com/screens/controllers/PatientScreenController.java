/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screens.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Sunny
 */
public class PatientScreenController implements Initializable {

    @FXML
    private Tab patientTab;

    @FXML
    private Tab appointmentsTab;

    @FXML
    private Tab outpatientTab;

    @FXML
    private Tab inpatientTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handlePatientSelectionChange() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/screens/tabs/PatientTab.fxml"));
        AnchorPane node = loader.load();
        patientTab.setContent(node);
    }

    @FXML
    private void handleAppointmentsSelectionChange() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/screens/tabs/AppointmentTab.fxml"));
        AnchorPane node = loader.load();
        appointmentsTab.setContent(node);
    }

    @FXML
    private void handleInpatientSelectionChange() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/screens/tabs/InpatientTab.fxml"));
        AnchorPane node = loader.load();
        inpatientTab.setContent(node);
    }

    @FXML
    private void handleOutpatientSelectionChange() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/screens/tabs/OutpatientTab.fxml"));
        AnchorPane node = loader.load();
        outpatientTab.setContent(node);
    }
}

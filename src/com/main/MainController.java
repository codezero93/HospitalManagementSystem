package com.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.Alerts.ShowAlerts;
import com.settings.AccountSettingsController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author Olca
 */
public class MainController implements Initializable {

    @FXML
    private Region patientRegion, roomRegion, labRegion, doctorRegion, analyzeRegion, billRegion, staffRegion;

    private String username;
    @FXML
    private ImageView pIcon, dIcon, rIcon, tIcon, sIcon, bIcon, hIcon, eIcon;

    @FXML
    private BorderPane mainbp;

    @FXML
    private ComboBox<String> comboBox;

    //------------------------Home icon handlers--------------------------------
    @FXML
    private void handleHomeClicked(MouseEvent ev) throws IOException {
        loadCenterScreen("/com/screens/AnalyzeScreen.fxml");
        removeAllRegionColor();
        setRegionColor(analyzeRegion);

    }

    @FXML
    private void handleHomeEntered(MouseEvent ev) throws IOException {
        handleEntered(hIcon);
    }

    @FXML
    private void handleHomeExited(MouseEvent ev) throws IOException {
        handleExited(hIcon);
    }

    //--------------------Patient icon handlers---------------------------------
    @FXML
    private void handlePatientClicked(MouseEvent ev) throws IOException {
        String url = "/com/screens/Patient_Screen.fxml";
        loadCenterScreen(url);
        removeAllRegionColor();
        setRegionColor(patientRegion);

//        mainbp.setCenter(FXMLLoader.load(getClass().getResource("/com/screens/Patient_Screen.fxml")));
    }

    @FXML
    private void handlePatientEntered(MouseEvent ev) throws IOException {
        handleEntered(pIcon);
    }

    @FXML
    private void handlePatientExited(MouseEvent ev) throws IOException {
        handleExited(pIcon);
    }

    //--------------------Doctor icon handlers---------------------------------
    @FXML
    private void handleDoctorClicked(MouseEvent ev) throws IOException {
        loadCenterScreen("/com/screens/Doctor_Screen.fxml");
        removeAllRegionColor();
        setRegionColor(doctorRegion);

    }

    @FXML
    private void handleDoctorEntered(MouseEvent ev) throws IOException {
        handleEntered(dIcon);
    }

    @FXML
    private void handleDoctorExited(MouseEvent ev) throws IOException {
        handleExited(dIcon);
    }

    //--------------------LabTest icon handlers---------------------------------
    @FXML
    private void handleLabTestClicked(MouseEvent ev) throws IOException {
        loadCenterScreen("/com/screens/Lab_Screen.fxml");
        removeAllRegionColor();
        setRegionColor(labRegion);
    }

    @FXML
    private void handleLabTestEntered(MouseEvent ev) throws IOException {
        handleEntered(tIcon);
    }

    @FXML
    private void handleLabTestExited(MouseEvent ev) throws IOException {
        handleExited(tIcon);
    }

    //--------------------Room icon handlers---------------------------------
    @FXML
    private void handleRoomClicked(MouseEvent e) throws IOException {
        loadCenterScreen("/com/screens/Room_Screen.fxml");
        removeAllRegionColor();
        setRegionColor(roomRegion);

    }

    @FXML
    private void handleRoomEntered(MouseEvent e) {
        handleEntered(rIcon);
    }

    @FXML
    private void handleRoomExited(MouseEvent e) {
        handleExited(rIcon);
    }

    //--------------------Staff icon handlers---------------------------------
    @FXML
    private void handleStaffClicked(MouseEvent ev) throws IOException {
        loadCenterScreen("/com/screens/Staff_Screen.fxml");
//        mainbp.setCenter(FXMLLoader.load(getClass().getResource("/com/screens/Staff_Screen.fxml")));
        removeAllRegionColor();
        setRegionColor(staffRegion);

    }

    @FXML
    private void handleStaffEntered(MouseEvent ev) throws IOException {
        handleEntered(sIcon);
    }

    @FXML
    private void handleStaffExited(MouseEvent ev) throws IOException {
        handleExited(sIcon);
    }

    //--------------------Bill icon handlers---------------------------------
    @FXML
    private void handleBillClicked(MouseEvent ev) throws IOException {
        loadCenterScreen("/com/screens/Bill_Screen.fxml");
        removeAllRegionColor();
        setRegionColor(billRegion);
    }

    @FXML
    private void handleBillEntered(MouseEvent ev) throws IOException {
        handleEntered(bIcon);
    }

    @FXML
    private void handleBillExited(MouseEvent ev) throws IOException {
        handleExited(bIcon);
    }

    //--------------------Exit icon handlers---------------------------------
    @FXML
    private void handleExitClicked(MouseEvent a) {
        System.exit(0);
    }

    @FXML
    private void handleExitEntered(MouseEvent e) {
        handleEntered(eIcon);
    }

    @FXML
    private void handleExitExited(MouseEvent e) {
        handleExited(eIcon);
    }

    //handlers for mouseentered and exited events   
    private void handleEntered(ImageView image) {
        image.setOpacity(0.5);
        image.setStyle("-fx-cursor:HAND");
    }

    private void handleExited(ImageView image) {
        image.setOpacity(1);
    }

    private void loadCenterScreen(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent pane = (Parent) loader.load();
        FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
        ft.setFromValue(0.1);
        ft.setToValue(1.0);
        ft.play();
        mainbp.setCenter(pane);
    }

    //combobox event handler
    @FXML
    private void handleComboBoxAction(ActionEvent a) throws IOException {
        String selected = comboBox.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        if (selected.equalsIgnoreCase("logout")) {
            logOut(a);
        } else if (selected.equalsIgnoreCase("accountsettings")) {
            showAccountSettings(this.username);

        }
//        comboBox.promptTextProperty().set(this.username);
        comboBox.valueProperty().set(null);

    }

    private void showAccountSettings(String username) throws IOException {
        AccountSettingsController account = new AccountSettingsController(username);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/settings/AccountSettingsScreen.fxml"));
        loader.setController(account);
        Pane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/pics/settings1.png"));
        stage.setScene(scene);
        stage.setTitle("AccountSettings");
        stage.showAndWait();
        stage.centerOnScreen();

    }

    private void logOut(ActionEvent a) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent login = loader.load();
        Scene scene = new Scene(login);
        Stage stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.centerOnScreen();

    }

    //controller's intializer methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboBox.setPromptText(this.username);

        comboBox.getItems().addAll("AccountSettings", "Logout");

        try {
            loadCenterScreen("/com/screens/AnalyzeScreen.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public MainController(String username) {
        this.username = username;

    }

    private void removeAllRegionColor() {

        patientRegion.setStyle("-fx-background-color:transparent");
        billRegion.setStyle("-fx-background-color:transparent");
        analyzeRegion.setStyle("-fx-background-color:transparent");
        staffRegion.setStyle("-fx-background-color:transparent");
        doctorRegion.setStyle("-fx-background-color:transparent");
        labRegion.setStyle("-fx-background-color:transparent");
        roomRegion.setStyle("-fx-background-color:transparent");

    }

    private void setRegionColor(Region region) {
        region.setStyle("-fx-background-color:red");
    }
}

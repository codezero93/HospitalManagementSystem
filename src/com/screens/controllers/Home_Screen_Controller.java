package com.screens.controllers;

import com.hibernate.HibernateUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.hibernate.Query;
import org.hibernate.Session;

public class Home_Screen_Controller implements Initializable {

    @FXML
    private PieChart patientPieChart;

    @FXML
    private PieChart staffPieChart;

    @FXML
    private PieChart doctorPieChart;

    @FXML
    private PieChart roomPieChart;

    private ObservableList<PieChart.Data> patientdata = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> staffdata = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> doctordata = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> roomdata = FXCollections.observableArrayList();
    private Session session;

    Map<String, Integer> patientMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientPieChart.setData(patientdata);
        doctorPieChart.setData(doctordata);
        staffPieChart.setData(staffdata);
        roomPieChart.setData(roomdata);

        setUpPatientPieChart();
        setUpRoomPieChart();
        setUpStaffPieChart();
        setUpDoctorPieChart();

        Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font:24 arial");

        for (PieChart.Data data : patientPieChart.getData()) {
            data.nodeProperty().get().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    caption.setTranslateX(event.getSceneX());
                    caption.setTranslateY(event.getSceneY());
                    caption.setText(String.valueOf(data.getPieValue()) + "%");
                }
            }
            );
        }

    }

    private void setUpPatientPieChart() {
        session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT gender FROM Patient");
        List<String> list = query.list();

        session.getTransaction().commit();
        session.close();
        int countmale = 0;
        int countfemale = 0;
        for (String p : list) {
            if (p.equals("male")) {
                countmale += 1;
            } else {
                countfemale += 1;
            }
        }
        patientdata.setAll(new PieChart.Data("Male", countmale), new PieChart.Data("Female", countfemale));

    }

    @FXML
    private void handleRefreshAction(ActionEvent e
    ) {
        setUpPatientPieChart();
        setUpRoomPieChart();
        setUpStaffPieChart();
        setUpDoctorPieChart();
    }

    private void setUpRoomPieChart() {
        session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT status FROM Room");
        List<String> list = query.list();

        session.getTransaction().commit();
        session.close();
        int countfree = 0;
        int countoccupied = 0;
        for (String p : list) {
            if (p.equalsIgnoreCase("free")) {
                countfree += 1;
            } else {
                countoccupied += 1;
            }
        }
        roomdata.setAll(new PieChart.Data("Free", countfree), new PieChart.Data("Occupied", countoccupied));
    }

    private void setUpStaffPieChart() {
        session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT dept FROM Staff");
        List<String> list = query.list();

        session.getTransaction().commit();
        session.close();
        int emergency = 0;
        int cardiology = 0;
        int surgery = 0;
        int dental = 0;
        int orthopedics = 0;
        int pediatrics = 0;
        int physiotherapy = 0;
        int dermatology = 0;
        int none = 0;

        for (String p : list) {
            if (p.equalsIgnoreCase("emergency")) {
                emergency += 1;
            } else if (p.equalsIgnoreCase("cardiology")) {
                cardiology += 1;
            } else if (p.equalsIgnoreCase("surgery")) {
                surgery += 1;
            } else if (p.equalsIgnoreCase("dental")) {
                dental += 1;
            } else if (p.equalsIgnoreCase("orthopedics")) {
                orthopedics += 1;
            } else if (p.equalsIgnoreCase("pediatrics")) {
                pediatrics += 1;
            } else if (p.equalsIgnoreCase("physiotherapy")) {
                physiotherapy += 1;
            } else if (p.equalsIgnoreCase("dermatology")) {
                dermatology += 1;
            } else if (p.equalsIgnoreCase("none")) {
                none += 1;
            }
        }
        staffdata.setAll(new PieChart.Data("dermatology", dermatology),
                new PieChart.Data("physiotherapy", physiotherapy),
                new PieChart.Data("pediatrics", pediatrics),
                new PieChart.Data("orthopedics", orthopedics),
                new PieChart.Data("dental", dental),
                new PieChart.Data("surgery", surgery),
                new PieChart.Data("cardiology", cardiology),
                new PieChart.Data("emergency", emergency),
                new PieChart.Data("none", emergency)
        );
    }

    private void setUpDoctorPieChart() {
        session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT dept FROM Doctor");
        List<String> list = query.list();

        session.getTransaction().commit();
        session.close();
        int emergency = 0;
        int cardiology = 0;
        int surgery = 0;
        int dental = 0;
        int orthopedics = 0;
        int pediatrics = 0;
        int physiotherapy = 0;
        int dermatology = 0;
        int none = 0;

        for (String p : list) {
            if (p.equalsIgnoreCase("emergency")) {
                emergency += 1;
            } else if (p.equalsIgnoreCase("cardiology")) {
                cardiology += 1;
            } else if (p.equalsIgnoreCase("surgery")) {
                surgery += 1;
            } else if (p.equalsIgnoreCase("dental")) {
                dental += 1;
            } else if (p.equalsIgnoreCase("orthopedics")) {
                orthopedics += 1;
            } else if (p.equalsIgnoreCase("pediatrics")) {
                pediatrics += 1;
            } else if (p.equalsIgnoreCase("physiotherapy")) {
                physiotherapy += 1;
            } else if (p.equalsIgnoreCase("dermatology")) {
                dermatology += 1;
            } else if (p.equalsIgnoreCase("none")) {
                none += 1;
            }
        }
        doctordata.setAll(new PieChart.Data("dermatology", dermatology),
                new PieChart.Data("physiotherapy", physiotherapy),
                new PieChart.Data("pediatrics", pediatrics),
                new PieChart.Data("orthopedics", orthopedics),
                new PieChart.Data("dental", dental),
                new PieChart.Data("surgery", surgery),
                new PieChart.Data("cardiology", cardiology),
                new PieChart.Data("emergency", emergency),
                new PieChart.Data("none", emergency)
        );
    }
}

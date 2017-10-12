package entities;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Olca
 */
@Entity
@Table(name = "appointments")
public class Appointments {

    private StringProperty ano = new SimpleStringProperty();
    private StringProperty pid = new SimpleStringProperty();
    private StringProperty drid = new SimpleStringProperty();
    private LocalDate app_date;
    private LocalTime app_time;
    private Patient patient;
    private Doctor doctor;

    public Appointments() {
    }

    public Appointments(String ano, String pid, String drid, LocalDate app_date, LocalTime app_time) {
        this.ano.set(ano);
        this.pid.set(pid);
        this.drid.set(drid);
        this.app_date = app_date;
        this.app_time = app_time;
    }

    @Id
    @Column(name = "ano")
    public String getAno() {
        return ano.get();
    }

    public void setAno(String ano) {
        this.ano.set(ano);
    }

    @Column(name = "pid")
    public String getPid() {
        return pid.get();
    }

    public void setPid(String pid) {
        this.pid.set(pid);
    }

    @Column(name = "drid")
    public String getDrid() {
        return drid.get();
    }

    public void setDrid(String drid) {
        this.drid.set(drid);
    }

    @Column(name = "app_date")
    public LocalDate getApp_date() {
        return app_date;
    }

    public void setApp_date(LocalDate app_date) {
        this.app_date = app_date;
    }

    @Column(name = "app_time")
    public LocalTime getApp_time() {
        return app_time;
    }

    public void setApp_time(LocalTime app_time) {
        this.app_time = app_time;
    }

    @Override
    public String toString() {
        return ano.get() + " " + app_date.toString() + " " + app_time.toString();

    }

    @ManyToOne
    @JoinColumn(name = "pid", insertable = false, updatable = false)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne
    @JoinColumn(name = "drid", insertable = false, updatable = false)
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

}

package entities;

import com.Alerts.ShowAlerts;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {

    private StringProperty pid = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty age = new SimpleIntegerProperty();
    private IntegerProperty weight = new SimpleIntegerProperty();
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty drid = new SimpleStringProperty();
    private StringProperty disease = new SimpleStringProperty();
    private StringProperty contactno = new SimpleStringProperty();
    private Set<Lab> lab = new HashSet<>();
    private Set<Appointments> appointments = new HashSet<>();
    private Doctor doctor;
    private Inpatient inpatient;
    private Outpatient outpatient;

    public Patient() {

    }

    public Patient(String pid, String name, Integer age, Integer weight, String gender, String address, String drid, String disease, String contactno) {
        this.pid.set(pid);
        this.name.set(name);
        this.age.set(age);
        this.weight.set(weight);
        this.gender.set(gender);
        this.address.set(address);
        this.drid.set(drid);
        this.disease.set(disease);
        this.contactno.set(contactno);
    }

    @Id
    @Column(name = "pid")
    public String getPid() {
        return pid.get();
    }

    public void setPid(String pid) {
        this.pid.set(pid);
    }

    @Column(name = "name")
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Column(name = "age")
    public Integer getAge() {
        return age.get();
    }

    public void setAge(Integer age) {
        this.age.set(age);
    }

    @Column(name = "weight")
    public Integer getWeight() {
        return weight.get();
    }

    public void setWeight(Integer weight) {
        this.weight.set(weight);

    }

    @Column(name = "gender")
    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    @Column(name = "address")
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    @Column(name = "drid")
    public String getDrid() {
        return drid.get();
    }

    public void setDrid(String drid) {
        this.drid.set(drid);
    }

    @Column(name = "disease")
    public String getDisease() {
        return disease.get();
    }

    public void setDisease(String disease) {
        this.disease.set(disease);
    }

    @Column(name = "contactno")
    public String getContactno() {
        return contactno.get();
    }

    public void setContactno(String contactno) {
        this.contactno.set(contactno);
    }

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    public Set<Lab> getLab() {
        return lab;
    }

    public void setLab(Set<Lab> lab) {
        this.lab = lab;
    }

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    public Set<Appointments> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointments> appointments) {
        this.appointments = appointments;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "drid")
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "patient")
    public Inpatient getInpatient() {
        return inpatient;
    }

    public void setInpatient(Inpatient inpatient) {
        this.inpatient = inpatient;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "patient")
    public Outpatient getOutpatient() {
        return outpatient;
    }

    public void setOutpatient(Outpatient outpatient) {
        this.outpatient = outpatient;
    }

    
    
}

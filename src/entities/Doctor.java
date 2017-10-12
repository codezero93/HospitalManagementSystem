package entities;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {

    private Set<Appointments> appointments = new HashSet<>();
    private Set<Lab> lab = new HashSet<>();
    private Set<Patient> patient = new HashSet<>();

    private StringProperty drid = new SimpleStringProperty();

    private StringProperty drname = new SimpleStringProperty();

    private StringProperty dept = new SimpleStringProperty();

    private StringProperty gender = new SimpleStringProperty();

    private StringProperty address = new SimpleStringProperty();

    private StringProperty contactno = new SimpleStringProperty();

    public Doctor() {

    }

    public Doctor(String drid, String drname, String dept, String gender, String address, String contactno) {
        this.drid.set(drid);
        this.drname.set(drname);
        this.dept.set(dept);
        this.gender.set(gender);
        this.address.set(address);
        this.contactno.set(contactno);
    }

    @Id
    @Column(name = "drid")
    public String getDrid() {
        return drid.get();
    }

    public void setDrid(String drid) {

        this.drid.set(drid);

    }

    @Column(name = "drname")
    public String getDrname() {
        return drname.get();
    }

    public void setDrname(String name) {
        this.drname.set(name);
    }

    @Column(name = "dept")
    public String getDept() {
        return dept.get();
    }

    public void setDept(String dept) {
        this.dept.set(dept);
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

    @Column(name = "contactno")
    public String getContactno() {
        return contactno.get();
    }

    public void setContactno(String contactno) {
        this.contactno.set(contactno);
    }

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    public Set<Appointments> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointments> appointments) {
        this.appointments = appointments;
    }

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    public Set<Lab> getLab() {
        return lab;
    }

    public void setLab(Set<Lab> lab) {
        this.lab = lab;
    }

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    public Set<Patient> getPatient() {
        return patient;
    }

    public void setPatient(Set<Patient> patient) {
        this.patient = patient;
    }

}

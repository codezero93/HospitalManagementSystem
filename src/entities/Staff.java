/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "staff")
public class Staff {

    private StringProperty sid = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty dept = new SimpleStringProperty();
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty staff_type = new SimpleStringProperty();
    private StringProperty contactno = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();

    public Staff() {

    }

    public Staff(String sid, String name, String dept, String gender, String staff_type, String contactno, String address) {
        this.sid.set(sid);
        this.name.set(name);
        this.dept.set(dept);
        this.gender.set(gender);
        this.staff_type.set(staff_type);
        this.contactno.set(contactno);
        this.address.set(address);
    }

    @Id
    @Column(name = "sid")
    public String getSid() {
        return sid.get();
    }

    public void setSid(String sid) {
        this.sid.set(sid);
    }

    @Column(name = "name")
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
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

    @Column(name = "staff_type")
    public String getStaff_type() {
        return staff_type.get();
    }

    public void setStaff_type(String staff_type) {
        this.staff_type.set(staff_type);
    }

    @Column(name = "contactno")
    public String getContactno() {
        return contactno.get();
    }

    public void setContactno(String contactno) {
        this.contactno.set(contactno);
    }

    @Column(name = "address")
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

}

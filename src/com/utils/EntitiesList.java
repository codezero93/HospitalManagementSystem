package com.utils;

import com.hibernate.HibernateUtils;
import entities.Account;
import entities.Doctor;
import entities.Lab;
import entities.Patient;
import entities.Room;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.hibernate.Query;
import org.hibernate.Session;

public class EntitiesList {

    private static Session session;

    public static List<String> getPatients() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Patient");
        List<Patient> patList = query.list();
        session.getTransaction().commit();
        session.close();
        List<String> pidList = patList.stream().map(Patient::getPid).collect(toList());

        return pidList;
    }

    public static List<String> getLabs() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Lab");
        List<Lab> list = query.list();
        session.getTransaction().commit();
        session.close();
        List<String> labList = list.stream().map(Lab::getLabno).collect(toList());
        return labList;
    }

    public static List<String> getDoctors() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Doctor");
        List<Doctor> list = query.list();
        session.getTransaction().commit();
        session.close();
        List<String> docList = list.stream().map(Doctor::getDrid).collect(toList());
        return docList;
    }

    public static List<String> getRooms() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Room");
        List<Room> list = query.list();
        session.getTransaction().commit();
        session.close();
        List<String> roomList = list.stream().map(Room::getRoomno).collect(toList());
        return roomList;
    }

    public static List<String> getUserAccounts() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Account");
        List<Account> list = query.list();
        session.getTransaction().commit();
        session.close();
        List<String> accountList = list.stream().filter(d -> !d.getUsername().equals("admin")).map(Account::getUsername).collect(toList());
        return accountList;
    }

}

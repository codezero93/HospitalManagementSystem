/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    private HibernateUtils() {

    }

    static {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            registry = new StandardServiceRegistryBuilder().configure() // configures
                    // settings
                    // from
                    // hibernate.cfg.xml
                    .build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
				// The registry would be destroyed by the SessionFactory, but we
                // had trouble building the SessionFactory
                // so destroy it manually.
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy(registry);

            }
        }
    }

    public static void closeFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Session openSession() {
        return sessionFactory.openSession();
    }
}

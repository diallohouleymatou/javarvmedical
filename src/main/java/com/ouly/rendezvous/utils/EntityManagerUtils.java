package com.ouly.rendezvous.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtils {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("oulyPU");
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}


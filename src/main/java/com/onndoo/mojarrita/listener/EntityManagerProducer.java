package com.onndoo.mojarrita.listener;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

//@ApplicationScoped
public class EntityManagerProducer {

    // Define el nombre de la unidad de persistencia (unitName) configurada en persistence.xml
    private static final String PERSISTENCE_UNIT_NAME = "myPersistence";

    // Se utiliza @Produces para indicar que esta clase proveerá el EntityManager
    //@Produces
    //@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
   // private EntityManager entityManager;

    // Este método se usará para crear el EntityManagerFactory si no existe
    //@Produces
  //  public EntityManager produceEntityManager() {
        // Crea un EntityManagerFactory para la unidad de persistencia
  //      EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        // Crear y devolver un EntityManager utilizando el EntityManagerFactory
  //      return entityManagerFactory.createEntityManager();
  //  }

    // Cierre de recursos cuando ya no se necesiten (opcional)
 //   public void closeEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
   //     if (entityManagerFactory != null) {
     //       entityManagerFactory.close();
       // }
   // }
}


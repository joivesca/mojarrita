package com.onndoo.mojarrita.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class GService<T> implements Serializable {

	private static EntityManagerFactory emf;
    protected EntityManager em;
    
    // Método abstracto que las clases concretas deben implementar
    protected abstract Class<T> getEntityClass();
	
    // Inicialización del EntityManagerFactory y EntityManager
    public void init() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("myPersistence");
        }
        em = emf.createEntityManager();
    }
    
    //@PersistenceContext(name = "myPersistence") // Inyecta el EntityManager    
    //EntityManager em;

    // Listar todas las entidades
    public List<T> listAll() {
        try {
            return em.createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass())
                     .getResultList();
        } catch (Exception e) {
            System.out.println("Error listing all entities: " + e.getMessage());
            return null;
        }
    }

    // Actualizar una entidad existente
    public void update(T entity) {
        try {
            em.merge(entity);
        } catch (Exception e) {
            System.out.println("Error updating entity: " + e.getMessage());
        }
    }

    // Crear una nueva entidad
    public void create(T entity) {
        try {
            em.persist(entity);
        } catch (Exception e) {
            System.out.println("Error creating entity: " + e.getMessage());
        }
    }

    // Eliminar una entidad
    public void delete(T entity) {
        try {
            if (!em.contains(entity)) {
                entity = em.find(getEntityClass(), entity);
            }

            if (entity != null) {
                em.remove(entity);
            } else {
                System.out.println("Entity not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting entity: " + e.getMessage());
        }
    }

    // Contar el número total de entidades
    public Long count() {
        try {
            return em.createQuery("SELECT COUNT(e) FROM " + getEntityClass().getSimpleName() + " e", Long.class)
                     .getSingleResult();
        } catch (Exception e) {
            System.out.println("Error counting entities: " + e.getMessage());
            return null;
        }
    }
    
    // Método para cerrar el EntityManager y EntityManagerFactory
    public void close() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}
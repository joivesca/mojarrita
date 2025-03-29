package com.onndoo.mojarrita.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import java.util.List;

import com.onndoo.mojarrita.model.Profiles;


//

@Named
@ApplicationScoped
@Transactional
public class Service extends GService<Profiles> {
	
    @Inject Pbkdf2PasswordHash passwordHasher;
    
    @Inject
    //@Resource(lookup = "java:comp/env/UserTransaction")
    UserTransaction userTransaction;

//    private static EntityManagerFactory emf;
//    private EntityManager em;
    
    
    //@PersistenceContext(name = "myPersistence")
    //protected EntityManager em;
    
   // @Inject
    //private EntityManager em;


    @Override
    protected Class<Profiles> getEntityClass(){
        return Profiles.class;
    }
     
    @PostConstruct
    public void init() {
    	super.init();
        InsertAdmin();
    }
    
    public boolean verifyPassword(char[] password, String hashedPassword){
        return passwordHasher.verify(password, hashedPassword);
    }
        
    public Profiles getSession(String username) {
        try {
            TypedQuery<Profiles> query = em.createQuery("SELECT u FROM Profiles u WHERE u.username = :username", Profiles.class);
            query.setParameter("username", username);

            List<Profiles> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                return resultList.get(0);
            } else {
                return null;
            }
        } catch (IllegalStateException | SecurityException e) {
            System.out.println("Error: ");
            System.out.println(e);
            return null;
        }
    }
    
    public void InsertAdmin(){  
        try {
        	
        	System.out.println("1");
            this.userTransaction.begin();
            String username = "Admin2";
            System.out.println("2");
            TypedQuery<Profiles> query = em.createQuery("SELECT u FROM Profiles u WHERE u.username = :username", Profiles.class);
            
            System.out.println("3");
            query.setParameter("username", username);
            List<Profiles> existingUsers = query.getResultList();
            System.out.println("4");
            if (existingUsers.isEmpty()) {
            	System.out.println("5");
                Profiles user = new Profiles();
                user.setUsername(username);
                user.setPassword(passwordHasher.generate("password123".toCharArray()));
                user.setGroupName("admin");
                
                em.persist(user);
                System.out.println("6");
                this.userTransaction.commit();
                System.out.println("7");
                System.out.println("Default Admin Saved!");
            } else {
            	System.out.println("8");
                System.out.println("User already exists");
                this.userTransaction.rollback();
            }
        } catch (HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException | IllegalStateException | SecurityException e) {
            System.out.println("Error in InsertAdmin! Error: " + e.toString());
        }
    }
    
    @Override
    public void create(Profiles entity) {
    	System.out.println("CREATING");
        try {
        	System.out.println(userTransaction);
        	System.out.println(em);
        	userTransaction.begin();
            var unHashedPassword = entity.getPassword();
            var HashedPassword = passwordHasher.generate(unHashedPassword.toCharArray());
            entity.setPassword(HashedPassword);
            System.out.println(entity.getUsername());
            System.out.println(entity.getPassword());
            System.out.println(entity.getGroupName());
            em.persist(entity);
            //em.flush();
            userTransaction.commit(); 
        } catch (Exception e) {
        	try {
                userTransaction.rollback(); // Si hay un error, realiza un rollback
            } catch (SystemException se) {
                se.printStackTrace();
            }
            System.out.println("Error persistiendo la entidad: " + e.getMessage());
        	e.printStackTrace();
        }
    }
    
    @Override
    public void delete(Profiles entity) {
        try {
            if (!em.contains(entity)) {
                entity = em.find(getEntityClass(), entity.getId());
            }

            if (entity != null) {
                em.remove(entity);
            } else {
                System.out.println("Entity not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting "+ getEntityClass().getSimpleName() +" : " + e.toString());
        }
    }
    @PreDestroy
    public void cleanup() {
        super.close();
    }
    
}
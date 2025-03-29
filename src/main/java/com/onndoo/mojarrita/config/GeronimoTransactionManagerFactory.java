package com.onndoo.mojarrita.config;

import org.apache.geronimo.transaction.GeronimoUserTransaction;
import org.apache.geronimo.transaction.manager.GeronimoTransactionManager;

import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;


public class GeronimoTransactionManagerFactory {
	

	    private static TransactionManager transactionManager;
	    private static UserTransaction userTransaction;

	    static {
	        try {
	            // Inicializa el TransactionManager
	            transactionManager = new GeronimoTransactionManager();

	            // Crea un UserTransaction vinculado al TransactionManager
	            userTransaction = new GeronimoUserTransaction((GeronimoTransactionManager) transactionManager);
	        } catch (javax.transaction.xa.XAException e) {
	            // Maneja la excepción en caso de error
	            e.printStackTrace();
	            throw new RuntimeException("Error al inicializar GeronimoTransactionManager o UserTransaction", e);
	        }
	    }

	    public static TransactionManager getTransactionManager() {
	        return transactionManager;
	    }

	    public static UserTransaction getUserTransaction() {
	        return userTransaction;
	    }
    
}/*implements ObjectFactory {

	
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        if (obj instanceof Reference) {
            // Create and return a new GeronimoTransactionManager instance
            return new GeronimoTransactionManager(300); // Timeout of 300 seconds
        }
        return null;
    }
}*/

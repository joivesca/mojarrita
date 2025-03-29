package com.onndoo.mojarrita.config;

import javax.transaction.xa.XAException;

import org.apache.geronimo.transaction.GeronimoUserTransaction;
import org.apache.geronimo.transaction.manager.GeronimoTransactionManager;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.transaction.UserTransaction;

public class TransactionProducer {

    @Produces
    @Singleton
    public UserTransaction createUserTransaction() throws XAException {
        // Inicializamos GeronimoTransactionManager
        GeronimoTransactionManager transactionManager = new GeronimoTransactionManager();
        
        // Creamos el UserTransaction utilizando el GeronimoTransactionManager
        GeronimoUserTransaction userTransaction = new GeronimoUserTransaction(transactionManager);
        
        return userTransaction;  // Esto será inyectado en las clases que lo necesiten
    }
}

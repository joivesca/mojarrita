package com.onndoo.mojarrita.config;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;

public class GeronimoJtaPlatform extends AbstractJtaPlatform {
    private static final long serialVersionUID = -2338991736623225135L;

	@Override
    protected TransactionManager locateTransactionManager() {
        // Aquí debes obtener el TransactionManager de Geronimo
        return GeronimoTransactionManagerFactory.getTransactionManager();
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        // Aquí debes obtener el UserTransaction de Geronimo
        return GeronimoTransactionManagerFactory.getUserTransaction();
    }
}
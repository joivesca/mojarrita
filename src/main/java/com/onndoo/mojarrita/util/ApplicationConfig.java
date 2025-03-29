package com.onndoo.mojarrita.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;



@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'java:comp/env/jdbc/MySQLDB'}",
        callerQuery = "select password from jakarta.Profiles where username = ?",
        groupsQuery = "select groupName from jakarta.Profiles where username = ?"
)
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
        loginPage = "/index",
        errorPage = "",
        useForwardToLogin = false)
)
@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
    
}
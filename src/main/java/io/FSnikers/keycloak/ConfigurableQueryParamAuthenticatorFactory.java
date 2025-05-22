package io.FSnikers.keycloak;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.authenticators.browser.IdentityProviderAuthenticator;
import org.keycloak.authentication.authenticators.browser.IdentityProviderAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class ConfigurableQueryParamAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "save-query-param-auth";

    @Override
    public String getDisplayType() {
        return "Query Redirect Handler";
    }

    @Override
    public String getReferenceCategory() {
        return "auth-redirect";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[] {
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.ALTERNATIVE,
                AuthenticationExecutionModel.Requirement.CONDITIONAL,
                AuthenticationExecutionModel.Requirement.DISABLED,
        };

    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new ConfigurableQueryParamAuthenticator();
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}

    @Override
    public String getHelpText() {
        return null;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }
}

package io.FSnikers.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.Arrays;
import java.util.List;

public class ConfigurableQueryParamAuthenticator implements Authenticator {
    private static final Logger logger = Logger.getLogger(ConfigurableQueryParamAuthenticator.class);
    private static final List<String> DEFAULT_PARAMS = List.of("auth_redirect_url");

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        List<String> keysToStore = getConfigParams(context);

        logger.debugf("ConfigurableQueryParamAuthenticator — storing query params: %s", keysToStore);

        for (String key : keysToStore) {
            String val = context.getUriInfo().getQueryParameters().getFirst(key);
            logger.debugf("Checking query param: %s = %s", key, val);

            if (val != null) {
                context.getAuthenticationSession().setClientNote(key, val);
                logger.debugf("Saved to client note: %s = %s", key, val);
            }
        }

        context.success();
    }

    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {}

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {}

    private List<String> getConfigParams(AuthenticationFlowContext context) {
        var configVal = context.getAuthenticatorConfig() != null
                ? context.getAuthenticatorConfig().getConfig().get("queryParams")
                : null;

        if (configVal != null && !configVal.isEmpty()) {
            logger.debugf("Custom queryParams from config: %s", configVal);
            return Arrays.asList(configVal.split(","));
        }

        logger.info("Using default query params");
        return DEFAULT_PARAMS;
    }

    @Override
    public void close() {}
}

package io.FSnikers.keycloak;

import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;

public class DynamicAuthRedirectOidcIdentityProviderConfig extends OIDCIdentityProviderConfig {

    public DynamicAuthRedirectOidcIdentityProviderConfig(IdentityProviderModel model) {
        super(model);
    }
}
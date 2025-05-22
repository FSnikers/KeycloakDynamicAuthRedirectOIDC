package io.FSnikers.keycloak;

import org.keycloak.broker.oidc.OIDCIdentityProvider;
import org.keycloak.broker.oidc.OIDCIdentityProviderFactory;
import org.keycloak.broker.provider.IdentityProviderFactory;
import org.keycloak.broker.social.SocialIdentityProvider;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class DynamicAuthRedirectOidcIdentityProviderFactory
        extends OIDCIdentityProviderFactory
        implements IdentityProviderFactory<OIDCIdentityProvider>
{

    public static final String PROVIDER_ID = "dynamic-auth-redirect-oidc";

    @Override
    public String getName() {
        return "DynamicAuthRedirect OpenID Connect";
    }

    @Override
    public DynamicAuthRedirectOidcIdentityProvider create(KeycloakSession session, IdentityProviderModel model) {
        return new DynamicAuthRedirectOidcIdentityProvider(session, new DynamicAuthRedirectOidcIdentityProviderConfig(model));
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        List<ProviderConfigProperty> properties = super.getConfigProperties();
        return properties;
    }
}
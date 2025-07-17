package io.FSnikers.keycloak;

import jakarta.ws.rs.core.UriBuilder;
import org.jboss.logging.Logger;
import org.keycloak.broker.oidc.OIDCIdentityProvider;
import org.keycloak.broker.provider.AuthenticationRequest;
import org.keycloak.broker.provider.IdentityBrokerException;
import org.keycloak.broker.provider.IdentityProviderMapper;
import org.keycloak.models.KeycloakSession;

public class DynamicAuthRedirectOidcIdentityProvider extends OIDCIdentityProvider {

    private static final Logger logger = Logger.getLogger(DynamicAuthRedirectOidcIdentityProvider.class);

    public DynamicAuthRedirectOidcIdentityProvider(KeycloakSession session, DynamicAuthRedirectOidcIdentityProviderConfig config) {
        super(session, config);
    }

    @Override
    protected UriBuilder createAuthorizationUrl(AuthenticationRequest request) {
        try {
            String authRedirectUrl = request.getAuthenticationSession().getClientNote("auth_redirect_url");

            logger.infof("Received auth_redirect_url: %s", authRedirectUrl);
            logger.infof("Received state: %s", request.getState().getEncoded());

            if (authRedirectUrl != null && !authRedirectUrl.isEmpty()) {
                UriBuilder customUri = UriBuilder.fromUri(authRedirectUrl)
                        .queryParam("client_id", getConfig().getClientId())
                        .queryParam("redirect_uri", request.getRedirectUri())
                        .queryParam("response_type", "code")
                        .queryParam("scope", getConfig().getDefaultScope())
                        .queryParam("state", request.getState().getEncoded())
                        .queryParam("nonce", request.getAuthenticationSession().getClientNote("nonce"));

                logger.debugf("Redirecting to custom auth URL: %s", customUri.build().toString());
                return customUri;
            } else {
                logger.debugf("No auth_redirect_url found, using default behavior");
                return super.createAuthorizationUrl(request);
            }
        } catch (Exception e) {
            logger.error("Error while building authorization URL", e);
            throw new IdentityBrokerException("Could not create authentication request.", e);
        }
    }

    @Override
    protected String getDefaultScopes() {
        return super.getDefaultScopes();
    }

    @Override
    public boolean isMapperSupported(IdentityProviderMapper mapper) {
        return super.isMapperSupported(mapper);
    }

    @Override
    public boolean supportsLongStateParameter() {
        return super.supportsLongStateParameter();
    }
}

import {KeycloakService} from 'keycloak-angular';

export function initializer(keycloak: KeycloakService): () => Promise<any> {

  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'mqtt',
        clientId: 'mqtt-admin-ui',
      },
      initOptions: {
        onLoad: 'login-required',
        pkceMethod: 'S256',
        checkLoginIframe: false
      },
      bearerExcludedUrls: []
    });
}

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
        onLoad: 'check-sso',
        pkceMethod: 'S256',
        checkLoginIframe: false
      },
      bearerExcludedUrls: []
    });
}

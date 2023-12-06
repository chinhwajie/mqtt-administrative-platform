import {KeycloakService} from 'keycloak-angular';
import { environment } from 'src/environments/environment';

export function initializer(keycloak: KeycloakService): () => Promise<any> {

  return () =>
    keycloak.init({
      config: {
        url: `http://${environment.AUTH_SERVER_HOST}:${environment.AUTH_SERVER_PORT}`,
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

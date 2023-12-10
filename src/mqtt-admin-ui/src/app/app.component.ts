import { Component } from '@angular/core';
import { KeycloakService } from "keycloak-angular";
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MQTT Admin';

  isLoggedIn: boolean | undefined;

  public personalInfo: string = `http://${environment.AUTH_SERVER_HOST}:${environment.AUTH_SERVER_PORT}/realms/mqtt/account/#/personal-info`

  constructor(private keycloak: KeycloakService) {
    keycloak.isLoggedIn().then(value => {
      this.isLoggedIn = value;
    }).finally(() => {
      // console.log(this.isLoggedIn)
    })
  }

  redirectToLogin(): void {
    this.keycloak.login().then(() => {
    });
  }

  logout(): void {
    this.keycloak.logout().then(() => {
    });
  }
}

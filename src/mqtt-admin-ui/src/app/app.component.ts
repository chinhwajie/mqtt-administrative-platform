import { Component } from '@angular/core';
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MQTT Admin';

  isLoggedIn: boolean | undefined;

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

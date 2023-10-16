import { Component } from '@angular/core';
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-device-settings',
  templateUrl: './device-settings.component.html',
  styleUrls: ['./device-settings.component.css']
})
export class DeviceSettingsComponent {
  constructor(public keycloak: KeycloakService) {
  }

  currentComponent: string = 'devicesList';

  showComponent(component: string): void {
    this.currentComponent = component;
  }
  getToken() {
    this.keycloak.getToken().then(r => {
      console.log("Token: " + r);
    })
  }
}

import { Component } from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {MatDialog} from "@angular/material/dialog";
import {CreateDeviceComponent} from "../../components/create-device/create-device.component";

@Component({
  selector: 'app-device-settings',
  templateUrl: './device-settings.component.html',
  styleUrls: ['./device-settings.component.css']
})
export class DeviceSettingsComponent {
  constructor(public keycloak: KeycloakService,
              public dialog: MatDialog) {
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

  openCreateDeviceDialog() {
    this.dialog.open(CreateDeviceComponent);
  }
}

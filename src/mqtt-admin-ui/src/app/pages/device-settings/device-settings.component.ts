import { Component } from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {MatDialog} from "@angular/material/dialog";
import {CreateDeviceComponent} from "../../components/create-device/create-device.component";
import {DeviceDetails} from "../../interfaces/device-details";
import {TopicsSettingComponent} from "../../components/topics-setting/topics-setting.component";
import {CategorySettingsComponent} from "../../components/category-settings/category-settings.component";

@Component({
  selector: 'app-device-settings',
  templateUrl: './device-settings.component.html',
  styleUrls: ['./device-settings.component.css']
})
export class DeviceSettingsComponent {
  constructor(public keycloak: KeycloakService,
              public dialog: MatDialog) {
    this.openCategorySettingsDialog();
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

  openTopicsSettingDialog() {
    this.dialog.open(TopicsSettingComponent);
  }

  openCategorySettingsDialog() {
    this.dialog.open(CategorySettingsComponent);
  }

  deviceList: DeviceDetails[] = [
    {
      deviceName: "D1",
      deviceModel: "M1",
      deviceBrand: "B1",
      deviceCategory: "C1",
      serialNumber: "S1"
    },
    {
      deviceName: "D1",
      deviceModel: "M1",
      deviceBrand: "B1",
      deviceCategory: "C1",
      serialNumber: "S1"
    },
    {
      deviceName: "D1",
      deviceModel: "M1",
      deviceBrand: "B1",
      deviceCategory: "C1",
      serialNumber: "S1"
    }
  ];
}

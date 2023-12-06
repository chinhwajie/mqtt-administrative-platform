import { Component, Output } from '@angular/core';
import { KeycloakService } from "keycloak-angular";
import { MatDialog } from "@angular/material/dialog";
import { CreateDeviceComponent } from "../../components/create-device/create-device.component";
import { DeviceDetails } from "../../interfaces/device-details";
import { TopicsSettingComponent } from "../../components/topics-setting/topics-setting.component";
import { CategorySettingsComponent } from "../../components/category-settings/category-settings.component";
import { MatIconRegistry } from "@angular/material/icon";
import { DomSanitizer } from "@angular/platform-browser";
import { MENU_ICON } from "../../icons";
import { DataSourceService } from 'src/app/services/data-source.service';

// TODO: Fix double scroll issue
@Component({
  selector: 'app-device-settings',
  templateUrl: './device-settings.component.html',
  styleUrls: ['./device-settings.component.css']
})
export class DeviceSettingsComponent {
  constructor(public keycloak: KeycloakService,
    public dialog: MatDialog,
    iconRegistry: MatIconRegistry,
    sanitizer: DomSanitizer,
    dataSourceService: DataSourceService
  ) {
    iconRegistry.addSvgIconLiteral('my-menu', sanitizer.bypassSecurityTrustHtml(MENU_ICON));
  }

  currentComponent: string = 'devicesList';

  showComponent(component: string): void {
    this.currentComponent = component;
  }

  getToken() {
    this.keycloak.getToken().then(r => {
      // console.log("Token: " + r);
    })
  }

  openCreateDeviceDialog() {
    this.dialog.open(CreateDeviceComponent);
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

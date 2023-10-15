import { Component } from '@angular/core';

@Component({
  selector: 'app-device-settings',
  templateUrl: './device-settings.component.html',
  styleUrls: ['./device-settings.component.css']
})
export class DeviceSettingsComponent {

  currentComponent: string = 'devicesList';

  showComponent(component: string): void {
    this.currentComponent = component;
  }
}

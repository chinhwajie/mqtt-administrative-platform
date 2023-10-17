import {Component, Input} from '@angular/core';
import {DeviceDetails} from "../../interfaces/device-details";


@Component({
  selector: 'app-device-details',
  templateUrl: './device-details.component.html',
  styleUrls: ['./device-details.component.css']
})
export class DeviceDetailsComponent {
  panelOpenState: boolean = false;
  @Input() deviceDetails: DeviceDetails | undefined;
}

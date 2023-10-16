import { Component } from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-create-device-dialog',
  templateUrl: './create-device.component.html',
  styleUrls: ['./create-device.component.css']
})
export class CreateDeviceComponent {
  topics = new FormControl('');
  topicsList: String[] = ["topic1","topic1/sub-topic1"];
  categoriesList: String[] = ["Cat1", "Cat2", "Cat3"];
}

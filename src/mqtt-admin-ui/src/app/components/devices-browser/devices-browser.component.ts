import {Component, Output} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {dummyDevices, dummyMessages, dummyTopics, generateSubscribedTopics} from "../dummy-data";
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";
import {MENU_ICON} from "../../icons";
import { categoryOptions } from 'src/app/enums/category';

@Component({
  selector: 'app-devices-browser',
  templateUrl: './devices-browser.component.html',
  styleUrls: ['./devices-browser.component.css']
})
export class DevicesBrowserComponent {
  page(event: PageEvent) {
    console.log("Page clicked!");
    console.log(event)
  }

  categories = categoryOptions;
  types = ["Device ID", "Device Name", "Device Info"];
  radioButtonFC = new FormControl('');
  devices = dummyDevices;
  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
  protected readonly dummyTopics = dummyTopics;
}

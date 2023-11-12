import {Component, Output} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {dummyDevices, dummyMessages, dummyTopics, generateSubscribedTopics} from "../dummy-data";
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";
import {MENU_ICON} from "../../icons";

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

  categories = [
    {value: 1, name: "Cat1"},
    {value: 2, name: "Cat2"},
    {value: 3, name: "Cat3"},
    {value: 4, name: "Cat4"},
  ];
  radioButtonFC = new FormControl('');
  devices = dummyDevices;
  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
  protected readonly dummyTopics = dummyTopics;
}

import {Component, Output} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FloatLabelType} from "@angular/material/form-field";
import {dummyDevices} from "../dummy-data";

@Component({
  selector: 'app-devices-browser',
  templateUrl: './devices-browser.component.html',
  styleUrls: ['./devices-browser.component.css']
})
export class DevicesBrowserComponent {
  constructor() {
  }

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
}

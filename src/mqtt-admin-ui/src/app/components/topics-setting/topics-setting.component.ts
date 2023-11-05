import {Component} from '@angular/core';
import {FormControl} from "@angular/forms";
import {KeycloakService} from "keycloak-angular";
import {MatDialog} from "@angular/material/dialog";

interface Topic {
  id: Number
  name: String
}

@Component({
  selector: 'app-topics-setting',
  templateUrl: './topics-setting.component.html',
  styleUrls: ['./topics-setting.component.css']
})
export class TopicsSettingComponent {
  topics = new FormControl({});
  displayedColumns = ['id', 'topicName', 'action'];
  dataSource = ELEMENT_DATA;
}

const ELEMENT_DATA: Topic[] = [
  {id: 1, name: 'Hydrogen'},
  {id: 2, name: 'Helium'},
  {id: 3, name: 'Lithium'},
  {id: 4, name: 'Beryllium'},
  {id: 5, name: 'Boron'},
  {id: 6, name: 'Carbon'},
  {id: 7, name: 'Nitrogen'},
  {id: 8, name: 'Oxygen'},
  {id: 9, name: 'Fluorine'},
  {id: 10, name: 'Neon'},
  {id: 11, name: 'Sodium'},
  {id: 12, name: 'Magnesium'},
  {id: 13, name: 'Aluminum'},
  {id: 14, name: 'Silicon'},
  {id: 15, name: 'Phosphorus'},
  {id: 16, name: 'Sulfur'},
  {id: 17, name: 'Chlorine'},
  {id: 18, name: 'Argon'},
  {id: 19, name: 'Potassium'},
  {id: 20, name: 'Calcium'},
];

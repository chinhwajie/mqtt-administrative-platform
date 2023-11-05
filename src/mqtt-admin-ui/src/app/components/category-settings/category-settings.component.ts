import { Component } from '@angular/core';

interface Category {
  id: Number
  name: String
}

@Component({
  selector: 'app-category-settings',
  templateUrl: './category-settings.component.html',
  styleUrls: ['./category-settings.component.css']
})
export class CategorySettingsComponent {
  displayedColumns = ['id', 'categoryName', 'action'];
  dataSource = ELEMENT_DATA;
}

const ELEMENT_DATA: Category[] = [
  {id: 1, name: 'Sensor'},
  {id: 2, name: 'Mob'},
  {id: 3, name: 'Lithium'},
  {id: 4, name: 'Beryllium'},
  {id: 5, name: 'Boron'},
  {id: 6, name: 'Carbon'},
];

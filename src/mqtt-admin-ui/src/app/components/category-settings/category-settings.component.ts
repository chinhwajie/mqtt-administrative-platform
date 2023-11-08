import { Component } from '@angular/core';
import {dummyCategories} from "../dummy-data";

@Component({
  selector: 'app-category-settings',
  templateUrl: './category-settings.component.html',
  styleUrls: ['./category-settings.component.css']
})
export class CategorySettingsComponent {
  displayedColumns = ['id', 'categoryName', 'action'];
  dataSource = dummyCategories;
}


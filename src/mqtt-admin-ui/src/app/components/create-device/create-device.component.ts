import { Component, ViewChild } from '@angular/core';
import { FormControl } from "@angular/forms";
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { timeDay } from 'd3';
import { categoryOptions } from 'src/app/enums/category';
import { DataSourceService } from 'src/app/services/data-source.service';
import { CategoryData } from '../devices-browser/devices-browser.component';

@Component({
  selector: 'app-create-device-dialog',
  templateUrl: './create-device.component.html',
  styleUrls: ['./create-device.component.css']
})
export class CreateDeviceComponent {
  topicsList: String[] = [];
  categoriesList: String[];
  chooseTopic: String = "";
  inputTopic: String = "";

  createDeviceData: any = {
    iotId: "",
    iotName: "",
    iotInfo: "",
    iotCategory: "",
    topics: []
  }

  @ViewChild(MatTable, { static: true }) table!: MatTable<any>;

  constructor(
    private dialogRef: MatDialogRef<CreateDeviceComponent>,
    private dataSourceService: DataSourceService,
  ) {
    this.categoriesList = [];
    this.getAvailableCategories().then(r => {
      r.subscribe(rr => {
        this.categoriesList = (rr as CategoryData).data.getAvailableCategories;
      })
    });
  }
  private async getAvailableCategories() {
    const query = `
      query {
          getAvailableCategories
      }
    `;
    const variables = {};
    return this.dataSourceService._query(query, variables);
  }
  selectRow(topic: string) {

    let table: any = document.querySelector("#table");
    for (var i = 0; i < table.rows.length; i++) {
      let row: any = table.rows[i];
      row.style.backgroundColor = "white";
      if (row.cells[0].textContent.trim() == topic) {
        row.style.backgroundColor = "#DEDEDE";
      }
    }
    this.chooseTopic = topic;
    console.log(topic);
  }

  createRow(topic: String) {
    if (topic == "" || this.topicsList.includes(topic)) {
      return;
    }
    this.topicsList.push(topic);
    this.inputTopic = "";
    this.table.renderRows();
    console.log(this.topicsList);
  }

  removeRow(topic: String) {
    let index = this.topicsList.indexOf(topic);
    if (index > -1) {
      this.topicsList.splice(index, 1);
      this.table.renderRows();
    }
  }

  resetRow() {
    this.topicsList.splice(0, this.topicsList.length);
    this.table.renderRows();
  }

  createDevice() {
    this.createDeviceData.topics = this.topicsList;

    console.log(this.createDeviceData);

    this.dataSourceService.createFullIot(
      this.createDeviceData.iotId,
      this.createDeviceData.iotName,
      this.createDeviceData.iotInfo,
      this.createDeviceData.iotCategory,
      this.createDeviceData.topics
    ).then(r => {
      r.subscribe((data: any) => {
        console.log(data);
        this.dialogRef.close();
      });
    });
  }
}

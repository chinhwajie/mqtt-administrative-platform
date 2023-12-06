import { Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { CreateDeviceComponent } from '../create-device/create-device.component';
import { DataSourceService } from 'src/app/services/data-source.service';
import { CategoryData, Iot } from '../devices-browser/devices-browser.component';
import { Category } from 'src/app/interfaces/device-category';
import { PopUpNotificationComponent } from '../pop-up-notification/pop-up-notification.component';

@Component({
  selector: 'app-edit-device',
  templateUrl: './edit-device.component.html',
  styleUrls: ['./edit-device.component.css']
})
export class EditDeviceComponent {
  categoriesList: String[];
  chooseTopic: String = "";
  inputTopic: String = "";

  editDeviceData: {
    readonly iotId: String,
    iotName: String,
    iotInfo: String,
    iotCategory: String,
    topics: String[]
  };

  @ViewChild(MatTable, { static: true }) table!: MatTable<any>;

  constructor(
    private dialogRef: MatDialogRef<EditDeviceComponent>,
    private dataSourceService: DataSourceService,
    @Inject(MAT_DIALOG_DATA) public data: Iot,
    private dialog: MatDialog
  ) {

    this.categoriesList = [];
    this.getAvailableCategories().then(r => {
      r.subscribe(rr => {
        this.categoriesList = (rr as CategoryData).data.getAvailableCategories;
      })
    });

    this.editDeviceData = {
      iotId: data.iotId,
      iotName: data.name,
      iotInfo: data.info,
      iotCategory: data.category,
      topics: data.topics.map(m => { return m.topic; })
    }

    console.log(this.editDeviceData);
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
    if (topic == "" || this.editDeviceData.topics.includes(topic)) {
      return;
    }
    this.editDeviceData.topics.push(topic);
    this.inputTopic = "";
    this.table.renderRows();
    console.log(this.editDeviceData.topics);
  }

  removeRow(topic: String) {
    let index = this.editDeviceData.topics.indexOf(topic);
    if (index > -1) {
      this.editDeviceData.topics.splice(index, 1);
      this.table.renderRows();
    }
  }

  resetRow() {
    this.editDeviceData.topics.splice(0, this.editDeviceData.topics.length);
    this.table.renderRows();
  }

  updateDevice() {
    this.editDeviceData.topics = this.editDeviceData.topics;

    console.log(this.editDeviceData);

    this.updateFullIot(
      this.editDeviceData.iotId,
      this.editDeviceData.iotName,
      this.editDeviceData.iotInfo,
      this.editDeviceData.iotCategory,
      this.editDeviceData.topics
    ).then(r => {
      r.subscribe((data: any) => {
        if (data.errors != null) {
          // data.message = "Update failed"
          this.dialog.open(PopUpNotificationComponent, { data: data });
        }
        console.log(data);
        this.dialogRef.close();
      });
    });
  }


  public async updateFullIot(iotId: String, iotName: String, info: String, iotCategory: String, topics: String[]) {
    const query = `
    mutation updateFullIot($iotId: String!, $iotName: String!, $info: String!, $iotCategory: Category!, $topics: [String]!) {
      updateFullIot(iotId: $iotId, iotName: $iotName, info: $info, iotCategory: $iotCategory, topics: $topics) {
        iotId,
        name,
        info,
        category
      }
    }`;
    const variables = {
      iotId,
      iotName,
      info,
      iotCategory,
      topics
    };

    return this.dataSourceService._query(query, variables);
  }
}

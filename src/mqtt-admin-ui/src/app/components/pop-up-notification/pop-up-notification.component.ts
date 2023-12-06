import { Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { CreateDeviceComponent } from '../create-device/create-device.component';
import { DataSourceService } from 'src/app/services/data-source.service';
import { CategoryData, Iot } from '../devices-browser/devices-browser.component';
import { Category } from 'src/app/interfaces/device-category';
import { ResultBox } from 'src/app/interfaces/result-box';

export interface GraphqlErrorResponse {
  errors: {
    message: string,
    locations: {
      line: number,
      column: number
    }[],
    path: string[],
    extensions: any
  }[],
  data: any
}

@Component({
  selector: 'app-pop-up-notification',
  templateUrl: './pop-up-notification.component.html',
  styleUrls: ['./pop-up-notification.component.css']
})
export class PopUpNotificationComponent {
  constructor(
    private dialogRef: MatDialogRef<PopUpNotificationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: GraphqlErrorResponse
  ) {

  }

  public dialogClose() {
    this.dialogRef.close();
  }
}

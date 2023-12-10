import { Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { CreateDeviceComponent } from '../create-device/create-device.component';
import { DataSourceService } from 'src/app/services/data-source.service';
import { CategoryData, Iot } from '../devices-browser/devices-browser.component';
import { DeleteMessageData, Message } from 'src/app/pages/statistics/statistics.component';

export interface GetIotData {
  data: {
    getIot: {
      messages: Message[]
    }
  }
}

@Component({
  selector: 'app-device-more-info',
  templateUrl: './device-more-info.component.html',
  styleUrls: ['./device-more-info.component.css']
})
export class DeviceMoreInfoComponent {
  messages: Message[] = [];

  @ViewChild(MatTable, { static: true }) table!: MatTable<any>;

  constructor(
    private dialogRef: MatDialogRef<DeviceMoreInfoComponent>,
    private dataSourceService: DataSourceService,
    @Inject(MAT_DIALOG_DATA) public data: Iot
  ) {
    this.loadMessages();
  }

  public loadMessages() {
    const query = `
    query getIot($iotId: String) {
        getIot(iotId: $iotId) {
            messages {
                id
                topic
                createTime
                payload
                alert
                iot {
                    name
                    iotId
                }
            }
        }
    }
    `;
    const variables = {
      iotId: this.data.iotId
    }

    this.dataSourceService._query(query, variables).then(r => {
      r.subscribe(rr => {
        this.messages = (rr as GetIotData).data.getIot.messages;
      })
    })
  }

  public deleteMessage(message: Message) {
    let messageId: number = message.id;
    console.log("[Delete Message] ID: " + message.iot.iotId);
    const query = `
    mutation deleteMessage($messageId: Int) {
      deleteMessage(messageId: $messageId) {
        id
        createTime
        topic
        payload
        iot {
          iotId
          name
        }
      }
    }`;

    const variables = {
      messageId
    }

    this.dataSourceService._query(query, variables).then(r => {
      r.subscribe(rr => {
        console.log(rr);
        let deletedMessage: Message = (rr as DeleteMessageData).data.deleteMessage;
        console.log(deletedMessage);
        let idx: number = this.messages.indexOf(message);
        console.log(idx);
        this.messages.splice(idx, 1);
      })
    })
  }
}

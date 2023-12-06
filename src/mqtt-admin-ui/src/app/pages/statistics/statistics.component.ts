import { Component } from '@angular/core';
import { dummyMessages } from "../../components/dummy-data";
import { MessagesSearchQuery } from 'src/app/interfaces/statistics';
import { FormControl, FormGroup } from '@angular/forms';
import { DataSourceService } from 'src/app/services/data-source.service';
import { PageEvent } from '@angular/material/paginator';

export interface SearchMessagesData {
  data: {
    searchMessages: Message[]
  }
}

export interface DeleteMessageData {
  data: {
    deleteMessage: Message
  }
}

export interface Message {
  id: number,
  createTime: string,
  topic: string,
  payload: string,
  iot: {
    iotId: string,
    name: string
  }
}

export interface Page {
  length: number,
  pageSize: number,
  pageSizeOptions: number[],
  pageIndex: number
}



@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  constructor(
    private dataSourceService: DataSourceService
  ) {
    this.paging = {
      length: 0,
      pageIndex: 0,
      pageSize: 25,
      pageSizeOptions: [10, 25, 50, 100]
    }
  }
  query = new FormGroup({
    type: new FormControl(""),
    value: new FormControl("")
  })

  types = ["Topic", "Category", "Iot ID", "Message"];
  messages: Message[] = [];
  data: Message[] = [];
  paging: Page;

  page(event: PageEvent) {
    // console.log("Page clicked!");
    // console.log(event)
    let sIdx = event.pageIndex * event.pageSize;
    let eIdx = sIdx + event.pageSize;
    this.messages = this.data.slice(sIdx, eIdx);
  }

  private async searchMessages(type: string, value: string) {
    const query = `
    query searchMessages($type: String!, $value: String) {
        searchMessages(type: $type, value: $value) {
            id
            createTime
            topic
            payload
            iot {
                iotId
                name
            }
        }
    }
    `;
    const variables = {
      type,
      value
    }
    return this.dataSourceService._query(query, variables);
  }
  public deleteMessage(message: Message) {
    let messageId: number = message.id;
    // console.log("[Delete Message] ID: " + message.iot.iotId);
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
        // console.log(rr);
        let deletedMessage: Message = (rr as DeleteMessageData).data.deleteMessage;
        // console.log(deletedMessage);
        let idx: number = this.messages.indexOf(message);
        // console.log(idx);
        this.messages.splice(idx, 1);
      })
    })
  }
  public lastUpdate: any;

  public search() {
    const type = this.query.get('type')?.value;
    const value = this.query.get('value')?.value;

    if (type == null || value == null) return;

    if (type !== "" && value !== "") {
      // console.log(value);
      // console.log(type);

      this.searchMessages(type, value).then(r => {
        r.subscribe(rr => {
          this.data = (rr as SearchMessagesData).data.searchMessages;
          this.paging.length = this.data.length;
          this.paging.pageIndex = 0;
          let sIdx = this.paging.pageIndex * this.paging.pageSize;
          let eIdx = sIdx + this.paging.pageSize;
          this.messages = this.data.slice(sIdx, eIdx);
          const current = new Date();
          this.lastUpdate = new Date(current.getTime()).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
          });
          // console.log(this.messages);
        })
      })
    }
  }
}

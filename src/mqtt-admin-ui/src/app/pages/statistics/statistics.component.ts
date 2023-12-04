import { Component } from '@angular/core';
import { dummyMessages } from "../../components/dummy-data";
import { MessagesSearchQuery } from 'src/app/interfaces/statistics';
import { FormControl, FormGroup } from '@angular/forms';
import { DataSourceService } from 'src/app/services/data-source.service';

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




@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  constructor(
    private dataSourceService: DataSourceService
  ) {

  }
  query = new FormGroup({
    type: new FormControl(""),
    value: new FormControl("")
  })

  types = ["Topic", "Category", "Iot ID", "Message"];
  messages: Message[] = [];

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

  public search() {
    const type = this.query.get('type')?.value;
    const value = this.query.get('value')?.value;

    if (type == null || value == null) return;

    if (type !== "" && value !== "") {
      console.log(value);
      console.log(type);

      this.searchMessages(type, value).then(r => {
        r.subscribe(rr => {
          this.messages = (rr as SearchMessagesData).data.searchMessages;
          console.log(this.messages);
        })
      })
    }
  }
}

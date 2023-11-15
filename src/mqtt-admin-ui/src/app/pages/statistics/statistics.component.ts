import { Component } from '@angular/core';
import { dummyMessages } from "../../components/dummy-data";
import { MessagesSearchQuery } from 'src/app/interfaces/statistics';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  constructor() {

  }
  query = new FormGroup({
    type: new FormControl(""),
    value: new FormControl("")
  })

  types = ["Topic", "Category", "Device ID", "Message"];
  messages = dummyMessages;
  
  public search() {
    if (this.query.get('type')?.value !== "" && this.query.get('value')?.value !== "") {
      console.log(this.query.get('type')?.value);
      console.log(this.query.get('value')?.value);

      // TODO: Search messages
    }
  }
}

import { Component } from '@angular/core';
import {dummyMessages} from "../../components/dummy-data";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  messages = dummyMessages;
}

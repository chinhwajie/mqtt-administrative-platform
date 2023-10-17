import {Component} from '@angular/core';
import {FormControl} from "@angular/forms";

interface Topic {
  name: String
}

@Component({
  selector: 'app-topics-setting',
  templateUrl: './topics-setting.component.html',
  styleUrls: ['./topics-setting.component.css']
})
export class TopicsSettingComponent {
  topics = new FormControl('');
  topicsList: Topic[] = [{"name": "topic1"}, {"name": "topic1/sub-topic1"}];
  displayedColumns: string[] = ['Topic Name', 'Actions'];
}

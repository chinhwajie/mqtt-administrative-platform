import {Component} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  public lastUpdate: string = new Date(new Date().getTime()).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  });
  public totalMessagesReceived: number = 99999;
  public totalDevicesCount: number = 898;
  public onlineDevices = [
    {
      name: 'Online Devices',
      value: 332
    },
    {
      name: 'Offline Devices',
      value: this.totalDevicesCount - 332
    }
  ];
  public pieChartDeviceCategories = [
    {
      name: 'Cat1',
      value: 40
    },
    {
      name: 'Cat2',
      value: 30
    },
    {
      name: 'Cat3',
      value: 20
    },
    {
      name: 'Cat4',
      value: 100
    },
    {
      name: 'Cat5',
      value: 120
    },
    {
      name: 'Cat6',
      value: 3
    },
  ];
  public pieChartDeviceStatus = [
    {name: "Normal", value: 70},
    {name: "Warning", value: 25},
    {name: "Critical", value: 5}
  ]
  public lineChartData = [
    {
      name: 'Device Growth',
      series: [
        {
          name: '2022-01-01',
          value: 10
        },
        {
          name: '2022-02-01',
          value: 20
        },
        {
          name: '2022-03-01',
          value: 50
        },
        {
          name: '2022-04-01',
          value: 78
        },
        {
          name: '2022-05-01',
          value: 100
        },
        {
          name: '2022-06-01',
          value: 227
        },
        {
          name: '2022-07-01',
          value: 343
        },
        {
          name: '2022-08-01',
          value: 554
        },
        {
          name: '2022-09-01',
          value: 767
        },
        {
          name: '2022-10-01',
          value: 834
        },
      ]
    },
    // Add more series data here
  ];
  displayedColumns = ['topicName', 'count'];
  public topVisitedTopics = [
    {name: "topic1", count: 333},
    {name: "topic1/sub-topic1", count: 221},
    {name: "topicN", count: 44}
  ];

  updateTotalDevicesCount() {
    const current = new Date();
    this.lastUpdate = new Date(current.getTime()).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    });
    this.totalMessagesReceived++;
  }
}

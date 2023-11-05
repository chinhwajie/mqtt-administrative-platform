import { Component } from '@angular/core';
import {ChartConfiguration, ChartOptions} from "chart.js";

@Component({
  selector: 'app-home',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
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
}

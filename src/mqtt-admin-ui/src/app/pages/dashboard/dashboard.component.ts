import {Component, ElementRef, ViewChild} from '@angular/core';
import {dummyDevices, dummyMessages} from "../../components/dummy-data";

@Component({
  selector: 'app-home',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  constructor() {
  }

  // TODO: Redesign Dashboard
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
  public topVisitedTopics = [
    {name: "topic1", count: 333},
    {name: "topic1/sub-topic1", count: 221},
    {name: "topicN", count: 44}
  ];

  // Sample data for Chart.js
  barChartData = [
    {data: [65, 343], label: 'Count'}
  ];
  barChartLabels = ['Online', 'Offline'];
  barChartOptions = {
    responsive: true,
    indexAxis: 'y',
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Active Devices'
      }
    }
  };
  barChartLegend = false;

  public lineChartData: any = [
    {data: [65, 59, 80, 81, 56, 55], label: 'Total messages received/day'},
  ];
  public lineChartLabels: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Friday', 'Saturday', 'Sunday'];
  public lineChartOptions: any = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Weekly Messages Activity Monitor'
      }
    }
  };
  public lineChartLegend = true;

  doughnutChartOptions: any = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Devices Distribution'
      }
    }
  }
  doughnutChartData = {
    labels: ['Category 1', 'Category 2', 'Category 3', 'Category 4', 'Category 5'],
    datasets: [
      {
        label: 'Dataset 1',
        data: [10, 20, 30, 40, 50],
      }
    ]
  };
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

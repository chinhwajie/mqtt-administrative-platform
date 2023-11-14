import { Component, ElementRef, ViewChild } from '@angular/core';
import { dummyDevices, dummyMessages } from "../../components/dummy-data";
import { DataSourceService } from 'src/app/services/data-source.service';

export interface DashboardData {
  data: {
    getTotalMessagesCount: number,
    getCountIotGroupByCategory: {
      count: number
      category: string
    }[]
    getIotsCount: number
    countDistinctTopic: {
      count: number
      topic: string
    }[]
  }
}

@Component({
  selector: 'app-home',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  constructor(private dataSource: DataSourceService) {
    this.loadData();
  }

  public loadData() {
    this.dataSource.getDashboardData().then(r => {
      r.subscribe((response) => {
        console.log(response);
        let dashboardData = response as DashboardData;

        this.totalDevicesCount = dashboardData.data.getIotsCount;
        this.totalMessagesReceived = dashboardData.data.getTotalMessagesCount;
        let doughnutChartDataLabels: string[] = [];
        let doughnutChartDataDatasetsData: number[] = [];
        dashboardData.data.getCountIotGroupByCategory.forEach(i => {
          doughnutChartDataLabels.push(i.category);
          doughnutChartDataDatasetsData.push(i.count);
        })
        this.doughnutChartData = {
          labels: doughnutChartDataLabels,
          datasets: [{
            label: "Datasets 1",
            data: doughnutChartDataDatasetsData
          }]
        }
        this.topVisitedTopics = [];
        for (let i = 0; i < 5; i++) {
          this.topVisitedTopics.push({ name: dashboardData.data.countDistinctTopic[i].topic, count: dashboardData.data.countDistinctTopic[i].count });
        }

      })
    });
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
  public totalMessagesReceived: number = 0;
  public totalDevicesCount: number = 0;
  public topVisitedTopics: any = [];

  // Sample data for Chart.js
  barChartData = [
    { data: [65, 343], label: 'Count' }
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
    { data: [65, 59, 80, 81, 56, 55], label: 'Total messages received/day' },
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
  doughnutChartData: any;
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
    this.loadData();
  }
}

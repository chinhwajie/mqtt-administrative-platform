import { Component, ElementRef, ViewChild } from '@angular/core';
import { dummyDevices, dummyMessages } from "../../components/dummy-data";
import { DataSourceService } from 'src/app/services/data-source.service';

export interface DashboardData {
  data: {
    getTotalMessagesCount: number,
    getCountIotGroupByCategory: {
      count: number
      category: string
    }[],
    getIotsCount: number,
    countDistinctTopic: {
      count: number,
      topic: string,
    }[],
    latestNDaysMessagesCountTrend: {
      date: string,
      messageCount: number
    }[],
    getActiveListeningConnection: number
  }
}

@Component({
  selector: 'app-home',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  constructor(private dataSourceService: DataSourceService) {
    this.lineChartData = [];
    this.barChartData = [];
    this.loadData();
  }

  public loadData() {
    this.getDashboardData().then(r => {
      r.subscribe((response) => {
        console.log(response);
        let dat = response as DashboardData;

        this.totalDevicesCount = dat.data.getIotsCount;
        this.totalMessagesReceived = dat.data.getTotalMessagesCount;
        let doughnutChartDataLabels: string[] = [];
        let doughnutChartDataDatasetsData: number[] = [];
        dat.data.getCountIotGroupByCategory.forEach(i => {
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

        for (let i = 0; i < Math.min(dat.data.countDistinctTopic.length, 5); i++) {
          this.topVisitedTopics.push({ name: dat.data.countDistinctTopic[i].topic, count: dat.data.countDistinctTopic[i].count });
        }
        this.lineChartData.splice(0, this.lineChartData.length);
        this.lineChartData.push({
          data: dat.data.latestNDaysMessagesCountTrend.map(i => { return i.messageCount; }),
          label: "Total messages received/day"
        });
        this.lineChartLabels = dat.data.latestNDaysMessagesCountTrend.map(i => { return i.date; });

        this.barChartData.splice(0, this.barChartData.length);
        let activeDevices = dat.data.getActiveListeningConnection;
        this.barChartData.push({
          data: [activeDevices, this.totalDevicesCount - activeDevices],
          label: "Devices Count"
        })
      })
    });

  }

  public async getDashboardData() {
    const query = `query {
        getTotalMessagesCount
        getCountIotGroupByCategory {
            count
            category
        }
        getIotsCount
        countDistinctTopic {
            count
            topic
        }
        latestNDaysMessagesCountTrend(n: 7) {
            date,
            messageCount
        }
        getActiveListeningConnection
    }`;
    const variables = {};
    return this.dataSourceService._query(query, variables);
  }

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
  barChartData: { data: number[], label: string }[];
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

  public lineChartData: { data: number[], label: string }[];
  public lineChartLabels: string[] = [];
  public lineChartOptions: any = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Latest 7 days non-zero messages count monitor'
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

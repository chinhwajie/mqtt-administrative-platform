import { Component } from '@angular/core';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent {
  single = [
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
}

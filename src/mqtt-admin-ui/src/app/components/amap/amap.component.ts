import {Component, OnInit} from '@angular/core';
import {dummyDevices} from "../dummy-data";

declare var AMap: any;

@Component({
  selector: 'app-amap',
  templateUrl: './amap.component.html',
  styleUrls: ['./amap.component.css']
})
export class AmapComponent implements OnInit {
  constructor() {

  }
  public devices = dummyDevices
  public amap: any;
  public marker: any;
  public polylines: any[] = [];

  ngOnInit(): void {
    // AMapUI.loadUI(['misc/PositionPicker'], (PositionPicker: any) => {
    //
    // })
    this.amap = new AMap.Map('container', {
      scrollWheel: true,
      lang: 'cn',
      viewMode: '2D',
      showBuildingBlock: true,
    });

    const layers = [];
    for (let i = 0; i < 10; i++) {

      const path = this.generateRandomPath(this.points); // 生成路径

      this.marker = new AMap.Marker({
        position: path[0],
        content: `Device ${i + 1}`,
      });
      this.amap.add(this.marker);
      // 绘制轨迹
      let polyline = new AMap.Polyline({
        path: path,
        showDir: true,
        strokeColor: "#28F",  //线颜色
        strokeOpacity: 1,     //线透明度
        strokeWeight: 6,      //线宽
        strokeStyle: "solid"  //线样式
      });
      this.polylines.push(polyline);
      this.amap.add(this.polylines.at(i));

      this.polylines.at(i).hide();
    }
    this.amap.setFitView(); // 根据覆盖物自适应展示地图
  }

  public points = [[120.095014, 30.298819], [121.51225, 31.139389], [118.886518, 31.991212], [117.260541, 31.711251], [112.975873, 28.122279], [106.559858, 29.163578], [104.01103, 35.685766], [108.932905, 34.462998], [91.222944, 29.623035]];

  shuffleArray(array: number[][]) {
    const shuffled = array.slice();
    for (let i = shuffled.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
    }
    return shuffled;
  }

  generateRandomPath(points: number[][]) {
    const shuffledPoints = this.shuffleArray(points); // 随机排列点的顺序
    return shuffledPoints.map(point => [point[0], point[1]]);
  }

  public show(id: number) {
    this.polylines.at(id).show();
    this.devices[id].show = true;
  }

  public hide(id: number) {
    this.polylines.at(id).hide();
    this.devices[id].show = false;
  }

  protected readonly dummyDevices = dummyDevices;
}

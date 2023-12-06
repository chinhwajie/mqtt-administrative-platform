import { Component, OnInit } from '@angular/core';
import { dummyDevices } from "../dummy-data";
import { DataSourceService } from 'src/app/services/data-source.service';

declare var AMap: any;
export interface IotTracesData {
  data: {
    getAllIotTraces: IotTrace[]
  }
}

export interface IotTrace {
  iot: {
    iotId: string,
    name: string,
    messages: {
      alert: boolean
    }[]
    show: boolean
  },
  coordinates: [number[]]
}

@Component({
  selector: 'app-amap',
  templateUrl: './amap.component.html',
  styleUrls: ['./amap.component.css']
})
export class AmapComponent {

  public amap: any;
  public marker: any;
  public polylines: any[] = [];
  public data: IotTrace[];
  public warningIcon: string = `<img src="../assets/warning.png" alt="warning-icon" style="width: 25px; height: 25px; float: left;">`;
  public pinIcon: string = `<img src="../assets/pin.png" alt="pin-icon" style="width: 25px; height: 25px; float: left; width: auto;">`;

  constructor(
    private dataSourceService: DataSourceService
  ) {
    this.data = [];
    this.showHide = [];
    this.getIotTraces().then(r => {
      r.subscribe(rr => {
        this.data = (rr as IotTracesData).data.getAllIotTraces;

        this.amap = new AMap.Map('container', {
          scrollWheel: true,
          lang: 'cn',
          viewMode: '2D',
          showBuildingBlock: true,
        });



        this.data.forEach(iotTrace => {
          let lng = iotTrace.coordinates[0][0].toFixed(3);
          let lat = iotTrace.coordinates[0][1].toFixed(3);
          let markerContent = `
          <div style="clear: both; display: table; content: ''; background-color: rgba(255,255,255,0.5); border-radius: 5px; padding: 5px">
            ${iotTrace.iot.messages.pop()?.alert ? this.warningIcon : this.pinIcon}
            <p style="float: left; width: auto; margin: 0px">${iotTrace.iot.name}(${lng},${lat})</p>
          </div>
          `;

          iotTrace.iot.show = false;
          this.showHide.push(false);
          const path = iotTrace.coordinates;
          this.marker = new AMap.Marker({
            position: path[0],
            content: markerContent,
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
          let idx = this.polylines.indexOf(polyline);
          this.amap.add(this.polylines.at(idx));
          this.polylines.at(idx).hide();

        });

        this.amap.setFitView(); // 根据覆盖物自适应展示地图
      });
    });
  }
  public async getIotTraces() {
    const query = `
    query {
        getAllIotTraces {
          iot {
              iotId,
              name,
              messages {
                  alert
              }
          },
          coordinates
      }
    }
    `;
    const variables = {}
    return this.dataSourceService._query(query, variables);
  }

  public showHide: boolean[];
  public show(idx: number, iot: IotTrace) {
    this.polylines.at(idx).show();
    iot.iot.show = true;
  }

  public hide(idx: number, iot: IotTrace) {
    this.polylines.at(idx).hide();
    iot.iot.show = false;
  }

  protected readonly dummyDevices = dummyDevices;
}

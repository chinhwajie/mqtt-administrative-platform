import {Component} from '@angular/core';

declare var AMap: any;

@Component({
  selector: 'app-amap',
  templateUrl: './amap.component.html',
  styleUrls: ['./amap.component.css']
})
export class AmapComponent {
  constructor() {
  }

  ngOnInit() {
    const map = this.getMap();
    this.layer.add(this.markers);
    // 图层添加到地图
    map.add(this.layer);
    for (let i = 0; i < this.LabelsData.length; i++) {
      let curData = (this.LabelsData)[i];
      let labelMarker = new AMap.LabelMarker(curData);
      // @ts-ignore
      this.markers.push(labelMarker);
    }
    // 将 marker 添加到图层
    this.layer.add(this.markers);

    map.setFitView(null, false, [100, 150, 10, 10]);
  }

  // 地图要放到函数里。
  getMap() {
    let map = new AMap.Map('container', {
      resizeEnable: true,
      zoom: 15.8,
      center: [116.469881, 39.993599],
      showIndoorMap: false,
    });
    return map;
  }

  iconPrimary = {
    // 图标类型，现阶段只支持 image 类型
    type: 'image',
    // 图片 url
    image:
      '/assets/pin.png',
    // 图片尺寸
    size: [32, 32],
    // 图片相对 position 的锚点，默认为 bottom-center
    anchor: 'bottom',
  };

  iconWarn = {
    // 图标类型，现阶段只支持 image 类型
    type: 'image',
    // 图片 url
    image:
      '/assets/warning.png',
    // 图片尺寸
    size: [32, 32],
    // 图片相对 position 的锚点，默认为 bottom-center
    anchor: 'bottom',
  };

  textStyle = {
    fontSize: 14,
    fontWeight: 'normal',
    fillColor: '#0000c2',
    strokeColor: '#fff',
    strokeWidth: 0,
    fold: true,
    padding: '2, 5',
    backgroundColor: '#fff',
  };

  LabelsData = [
    {
      name: '自提点1',
      position: [116.461009, 39.991443],
      zooms: [10, 20],
      opacity: 1,
      zIndex: 10,
      fold: true,
      icon: this.iconWarn,
      text: {
        // 要展示的文字内容
        content: '中邮速递易',
        // 文字方向，有 icon 时为围绕文字的方向，没有 icon 时，则为相对 position 的位置
        direction: 'right',
        // 在 direction 基础上的偏移量
        offset: [0, -5],
        // 文字样式
        style: this.textStyle,
      },
    },
    {
      name: '自提点2',
      position: [116.466994, 39.984904],
      zooms: [10, 20],
      opacity: 1,
      zIndex: 16,
      icon: this.iconPrimary,
      text: {
        content: '丰巢快递柜-花家地北里',
        direction: 'right',
        offset: [0, -5],
        style: this.textStyle,
      },
    },
    {
      name: '自提点3',
      position: [116.472914, 39.987093],
      zooms: [10, 20],
      opacity: 1,
      zIndex: 8,
      icon: this.iconPrimary,
      text: {
        content: '丰巢快递柜-中环南路11号院',
        direction: 'right',
        offset: [0, -5],
        style: this.textStyle,
      },
    },
    {
      name: '自提点4',
      position: [116.471814, 39.995856],
      zooms: [10, 20],
      opacity: 1,
      zIndex: 23,
      icon: this.iconPrimary,
      text: {
        content: '丰巢快递柜-合生麒麟社',
        direction: 'right',
        offset: [0, -5],
        style: this.textStyle,
      },
    },
    {
      name: '自提点5',
      position: [116.469639, 39.986889],
      zooms: [10, 20],
      opacity: 1,
      zIndex: 6,
      icon: this.iconWarn,
      text: {
        content: '速递易快递柜-望京大厦',
        direction: 'right',
        offset: [0, -5],
        style: this.textStyle,
      },
    },
    {
      name: '自提点6',
      position: [116.467361, 39.996361],
      zooms: [10, 20],
      opacity: 1,
      zIndex: 5,
      icon: this.iconWarn,
      text: {
        content: 'E栈快递柜-夏都家园',
        direction: 'right',
        offset: [0, -5],
        style: this.textStyle,
      },
    },
    {
      name: '自提点7',
      position: [116.462327, 39.990071],
      zooms: [10, 20],
      opacity: 1,
      zIndex: 4,
      icon: this.iconWarn,
      text: {
        content: '丰巢自提柜-圣馨大地家园',
        direction: 'right',
        offset: [0, -5],
        style: this.textStyle,
      },
    },
    {
      name: '自提点8',
      position: [116.462349, 39.996067],
      zooms: [10, 20],
      opacity: 1,
      zIndex: 3,
      icon: this.iconPrimary,
      text: {
        content: '丰巢快递-圣馨大地家园',
        direction: 'right',
        offset: [0, -5],
        style: this.textStyle,
      },
    },
    {
      name: '自提点9',
      position: [116.456474, 39.991563],
      zooms: [10, 20],
      zIndex: 2,
      opacity: 1,
      icon: this.iconPrimary,
      text: {
        content: 'E栈快递柜-南湖渠西里',
        direction: 'right',
        offset: [0, -5],
        style: this.textStyle,
      },
    },
  ];

  markers = [];
  allowCollision = false;
  layer = new AMap.LabelsLayer({
    zooms: [3, 20],
    zIndex: 1000,
    collision: false
  });

}

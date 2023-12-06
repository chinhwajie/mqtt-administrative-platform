import { Component, Output } from '@angular/core';
import { PageEvent } from "@angular/material/paginator";
import { FormArray, FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { dummyDevices, dummyMessages, dummyTopics } from "../dummy-data";
import { DataSourceService } from 'src/app/services/data-source.service';
import { formatDate } from 'src/app/utils/utils';
import { EditDeviceComponent } from '../edit-device/edit-device.component';
import { MatDialog } from '@angular/material/dialog';
import { ResultBox } from 'src/app/interfaces/result-box';
import { DeviceMoreInfoComponent } from '../device-more-info/device-more-info.component';

export interface CategoryData {
  data: {
    getAvailableCategories: String[]
  }
}

export interface GetAllIotsData {
  data: {
    getAllIots: Iot[]
  }
}

export interface ComplexIotSearch {
  data: {
    complexIotSearch: Iot[]
  }
}

export interface Iot {
  iotId: string,
  name: string,
  info: string,
  topics: {
    topic: string,
    connectionState: boolean
  }[],
  category: string,
  createTime: string,
  connectionState: boolean
  messages: {
    id: number,
    payload: string,
    createTime: string,
    topic: string,
  }[]
}

@Component({
  selector: 'app-devices-browser',
  templateUrl: './devices-browser.component.html',
  styleUrls: ['./devices-browser.component.css']
})
export class DevicesBrowserComponent {
  constructor(
    private dataSourceService: DataSourceService,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {

    this.getAvailableCategories().then(r => {
      r.subscribe(rr => {
        this.categories = (rr as CategoryData).data.getAvailableCategories;
      })
    });

    this.queryFG = fb.group({
      typeFC: new FormControl(""),
      typeValueFC: new FormControl(""),
      // topicFC: new FormControl(""),
      categoriesCheckBoxFA: this.fb.array([]),
      onlineRadioButtonFC: new FormControl('')
    });
    this.queryFG.get("typeValueFC")?.disable();
    // this.queryFG.get("topicFC")?.disable();
    this.devices = [];
    this.data = [];
    this.paging = {
      length: 0,
      pageIndex: 0,
      pageSize: 25,
      pageSizeOptions: [10, 25, 50, 100]
    }
  }

  categories: String[] = [];
  devices: Iot[];
  data: Iot[];
  paging: any;
  types: { name: String, value: String }[] = [
    { name: "None", value: "" },
    { name: "Iot ID", value: "iotId" },
    { name: "Iot Info", value: "info" },
    { name: "Iot Name", value: "name" }
  ];

  queryFG: FormGroup;
  dateCreatedRangeFG = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  public openEditDeviceDialog(iot: Iot) {
    this.dialog.open(EditDeviceComponent, { data: iot });
  }

  public deleteIot(iot: Iot, idx: number) {
    this.handleDeleteIot(iot.iotId).then(r => {
      r.subscribe(rr => {
        this.devices.splice(idx, 1);
        this.paging.length--;
      })
    })
  }

  public async handleDeleteIot(iotId: string) {
    const query = `
    mutation deleteIot($iotId: String!) {
      deleteIot(iotId: $iotId) {
        iotId,
        name,
        info,
        category
      }
    }`;
    const variables = {
      iotId
    };

    return this.dataSourceService._query(query, variables);
  }

  public moreInfo(iot: Iot) {
    this.dialog.open(DeviceMoreInfoComponent, { data: iot });
  }

  public onCheckAllChange(e: any) {
    const checkArray: FormArray = this.queryFG.get('categoriesCheckBoxFA') as FormArray;
    checkArray.clear();
    if (e.checked) {
      this.categories.forEach(item => {
        checkArray.push(new FormControl(item));
      })
    }
  }
  public onCheckboxChange(e: any) {
    const checkArray: FormArray = this.queryFG.get('categoriesCheckBoxFA') as FormArray;
    if (e.checked) {
      checkArray.push(new FormControl(e.source.value));
    } else {
      let i: number = 0;
      checkArray.controls.forEach((item) => {
        if (item.value == e.source.value) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

  // public onTopicFilterCheckBoxChange(e: any) {
  //   if (e.checked) this.queryFG.get("topicFC")?.enable();
  //   else this.queryFG.get("topicFC")?.disable();
  // }

  public onTypeFilterSelectionChange(e: any) {
    if (e === 'None') {
      this.queryFG.get("typeFC")?.setValue(null);
      this.queryFG.get("typeValueFC")?.disable();
    }
    else if (e !== null) this.queryFG.get("typeValueFC")?.enable();
  }

  page(event: PageEvent) {
    // console.log("Page clicked!");
    // console.log(event)
    let sIdx = event.pageIndex * event.pageSize;
    let eIdx = sIdx + event.pageSize;
    this.devices = this.data.slice(sIdx, eIdx);
  }

  private async getAvailableCategories() {
    const query = `
      query {
          getAvailableCategories
      }
    `;
    const variables = {};
    return this.dataSourceService._query(query, variables);
  }

  public handleSearch() {
    // console.log(this.queryFG.get('typeFC')?.value);
    // console.log(this.queryFG.get('typeValueFC')?.value);
    // // console.log(this.queryFG.get('topicFC')?.value);
    // console.log(this.queryFG.get('categoriesCheckBoxFA')?.value);

    let categoriesCheckedValues: [] = this.queryFG.get('categoriesCheckBoxFA')?.value;
    let onlineRadioButtonValue = this.queryFG.get('onlineRadioButtonFC')?.value;
    let filterType = this.queryFG.get('typeFC')?.value;
    let filterTypeValue = this.queryFG.get('typeValueFC')?.value;
    // let filterTopicValue = this.queryFG.get('topicFC')?.value;
    let sDateValue = this.dateCreatedRangeFG.get('start')?.value;
    let eDateValue = this.dateCreatedRangeFG.get('end')?.value;

    const query = `
    query complexIotSearch($categories: [Category], $type: String, $typeValue: String, $sDate: String, $eDate: String, $status: String) {
        complexIotSearch(
            categories: $categories,
            type: $type,
            typeValue: $typeValue,
            sDate: $sDate,
            eDate: $eDate,
            status: $status
            ) {
                iotId
                  name
                  info
                  connectionState
                  topics {
                      topic
                      connectionState
                  }
                  category
                  createTime
                  messages {
                      payload
                  }
    
        }
    }
    `

    const variables = {
      categories: categoriesCheckedValues as String[],
      type: filterType == null || filterType === 'None' ? "" : filterType,
      typeValue: filterType == null || filterType === 'None' ? "" : filterTypeValue,
      sDate: sDateValue == null ? "" : formatDate(sDateValue),
      eDate: eDateValue == null ? "" : formatDate(eDateValue),
      status: onlineRadioButtonValue
    }

    // console.log(variables);
    this.dataSourceService._query(query, variables).then(r => {
      r.subscribe(rr => {
        // console.log(rr);
        this.data = (rr as ComplexIotSearch).data.complexIotSearch;
        this.paging.length = this.data.length;
        this.paging.pageIndex = 0;
        let sIdx = this.paging.pageIndex * this.paging.pageSize;
        let eIdx = sIdx + this.paging.pageSize;
        this.devices = this.data.slice(sIdx, eIdx);
      })
    })
  }

  public handleSubscription(idx: number, iot: Iot) {
    let endpoint: string = '';
    let body = {
      iotId: iot.iotId,
      topic: iot.topics[idx].topic
    }


    if (iot.topics[idx].connectionState) {
      // console.log("To unsubscribe")
      endpoint = "classic/unsubscribe";

    } else {
      // console.log("To subscribe");
      endpoint = "classic/subscribe";
    }

    // console.log(endpoint);

    this.dataSourceService.postRequest(endpoint, body).then(r => {
      r.subscribe(rr => {
        let result = rr as ResultBox;
        if (result.success) {
          iot.topics[idx].connectionState = !iot.topics[idx].connectionState;
        } else {
          // Handle unable to connect
          // console.log(result.message);
        }
      })
    })
  }

  public handleConnect(iot: Iot, idx: number) {
    let endpoint: string = '';
    let body = {
      iotId: iot.iotId,
      topic: null
    }

    if (iot.connectionState) {
      // console.log("To disconnect")
      endpoint = "classic/disconnect";

    } else {
      // console.log("To connect");
      endpoint = "classic/connect";
    }

    // console.log(endpoint);

    this.dataSourceService.postRequest(endpoint, body).then(r => {
      r.subscribe(rr => {
        let result = rr as ResultBox;
        if (result.success) {
          if (iot.connectionState) {
            iot.topics.forEach(topic => {
              topic.connectionState = false;
            });
          }
          iot.connectionState = !iot.connectionState;
        } else {
          // console.log(result.message);
        }
      })
    })
  }

}

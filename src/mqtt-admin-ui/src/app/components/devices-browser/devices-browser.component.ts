import { Component, Output } from '@angular/core';
import { PageEvent } from "@angular/material/paginator";
import { FormArray, FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { dummyDevices, dummyMessages, dummyTopics } from "../dummy-data";
import { DataSourceService } from 'src/app/services/data-source.service';

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

export interface Iot {
  iotId: string,
  name: string,
  info: string,
  topics: {
    topic: string
  }[],
  category: string,
  createTime: string,
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
    private fb: FormBuilder
  ) {

    this.getAvailableCategories().then(r => {
      r.subscribe(rr => {
        this.categories = (rr as CategoryData).data.getAvailableCategories;
      })
    });

    this.queryFG = fb.group({
      typeFC: new FormControl(""),
      typeValueFC: new FormControl(""),
      topicFC: new FormControl(""),
      categoriesCheckBoxFA: this.fb.array([]),
      onlineRadioButtonFC: new FormControl('')
    });
    this.queryFG.get("typeValueFC")?.disable();
    this.queryFG.get("topicFC")?.disable();
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
  types = ["None", "Device ID", "Device Name", "Device Info"];

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

  public onTopicFilterCheckBoxChange(e: any) {
    if (e.checked) this.queryFG.get("topicFC")?.enable();
    else this.queryFG.get("topicFC")?.disable();
  }

  public onTypeFilterSelectionChange(e: any) {
    if (e === 'None') {
      this.queryFG.get("typeFC")?.setValue(null);
      this.queryFG.get("typeValueFC")?.disable();
    }
    else if (e !== null) this.queryFG.get("typeValueFC")?.enable();
  }

  page(event: PageEvent) {
    console.log("Page clicked!");
    console.log(event)
    let sIdx = event.pageIndex * event.pageSize;
    let eIdx = sIdx + event.pageSize;
    this.devices = this.data.slice(sIdx, eIdx);
  }

  queryFG: FormGroup;
  dateCreatedRangeFG = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  private async getAvailableCategories() {
    const query = `
      query {
          getAvailableCategories
      }
    `;
    const variables = {};
    return this.dataSourceService._query(query, variables);
  }

  private async getAllIots() {
    const query = `
      query {
          getAllIots {
              iotId
              name
              info
              topics {
                  topic
              }
              category
              createTime
              messages {
                  payload
              }
          }
      }
    `;
    const variables = {};
    return this.dataSourceService._query(query, variables);
  }

  public handleSearch() {
    console.log(this.queryFG.get('typeFC')?.value);
    console.log(this.queryFG.get('typeValueFC')?.value);
    console.log(this.queryFG.get('topicFC')?.value);
    console.log(this.queryFG.get('categoriesCheckBoxFA')?.value);

    let categoriesCheckedValues: [] = this.queryFG.get('categoriesCheckBoxFA')?.value;
    let onlineRadioButtonValue = this.queryFG.get('onlineRadioButtonFC')?.value;
    let filterType = this.queryFG.get('typeFC')?.value;
    let filterTypeValue = this.queryFG.get('typeValueFC')?.value;
    let filterTopicValue = this.queryFG.get('topicFC')?.value;

    if (categoriesCheckedValues.length == this.categories.length && onlineRadioButtonValue === 'all') {
      console.log("Get all Iots");
      this.getAllIots().then(r => {
        r.subscribe(rr => {
          this.data = (rr as GetAllIotsData).data.getAllIots;
          this.paging.length = this.data.length;
          let sIdx = this.paging.pageIndex * this.paging.pageSize;
          let eIdx = sIdx + this.paging.pageSize;
          this.devices = this.data.slice(sIdx, eIdx);
          console.log("Data load done");
        })
      })
    } else {
      
    }
  }


}

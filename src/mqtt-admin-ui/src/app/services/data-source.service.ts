import { animate } from '@angular/animations';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';


const RESOURCE_SERVER_URL = 'http://10.73.103.130:8081/';
const GRAPHQL_SERVER_URL = RESOURCE_SERVER_URL + 'graphql';
const REST_SERVER_URL = RESOURCE_SERVER_URL + 'classic';

@Injectable({
  providedIn: 'root'
})
export class DataSourceService {
  constructor(
    private keycloakService: KeycloakService,
    private http: HttpClient) {
  }

  public async getHeader() {
    const token = await this.keycloakService.getToken()
    console.log(token);
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return headers;
  }

  public async getDashboardData() {
    const headers = await this.getHeader();
    return this.http.post(GRAPHQL_SERVER_URL, {
      query: `query {
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
            }`
    }, { headers });
  }

  public async createIot(iotId: string, iotName: string, info: string, iotCategory: string) {
    const query = `mutation {
                    createIot(
                      iotId: "${iotId}",
                      iotName: "${iotName}",
                      info: "${info}",
                      iotCategory: ${iotCategory},
                    ) {
                      iotId,
                      name,
                      info,
                      category
                    }
                }`
    console.log(query);
    const headers = await this.getHeader();
    return this.http.post(GRAPHQL_SERVER_URL, {
      query
    }, { headers });
  }
}

export { GRAPHQL_SERVER_URL, REST_SERVER_URL }
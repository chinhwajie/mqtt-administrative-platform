import { animate } from '@angular/animations';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

const RESOURCE_SERVER_URL = 'http://10.188.44.35:8081/';
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

  public async createFullIot(iotId: string, iotName: string, info: string, iotCategory: string, topics: string[]) {
    // const query = gql`mutation createFullIot($iotId: String!, $iotName: String!, $info: String!, $iotCategory: Category!, $topics: [String]!) {
    //                       createFullIot(iotId: $iotId, iotName: $iotName, info: $info, iotCategory: $iotCategory, topics: $topics) {
    //                         iotId,
    //                         name,
    //                         info,
    //                         category
    //                       }
    //                   }`
    // return this.apollo.mutate({
    //   mutation: query,
    //   variables: {
    //     iotId,
    //     iotName,
    //     info,
    //     iotCategory,
    //     topics
    //   }
    // });
  }
}

export { RESOURCE_SERVER_URL, GRAPHQL_SERVER_URL, REST_SERVER_URL }
import { animate } from '@angular/animations';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { environment } from 'src/environments/environment';

const RESOURCE_SERVER_URL = `http://${environment.RESOURCE_SERVER_HOST}:${environment.RESOURCE_SERVER_PORT}/`;
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
    // console.log(token);
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return headers;
  }

  public async _query(query: string, variables: {}) {
    const headers = await this.getHeader();
    return this.http.post(GRAPHQL_SERVER_URL, {
      query: query,
      variables: variables
    }, { headers });
  }

  public async postRequest(endpoint: string, body: any) {
    const headers = await this.getHeader();
    const url = RESOURCE_SERVER_URL + endpoint
    return this.http.post(url, body, { headers });
  }

  public async getRequest(endpoint: string, body: any) {
    const headers = await this.getHeader();
    const url = RESOURCE_SERVER_URL + endpoint
    return this.http.get(url, { headers });
  }


  public async createFullIot(iotId: string, iotName: string, info: string, iotCategory: string, topics: string[]) {
    const query = `
    mutation createFullIot($iotId: String!, $iotName: String!, $info: String!, $iotCategory: Category!, $topics: [String]!) {
      createFullIot(iotId: $iotId, iotName: $iotName, info: $info, iotCategory: $iotCategory, topics: $topics) {
        iotId,
        name,
        info,
        category
      }
    }`;
    const variables = {
      iotId,
      iotName,
      info,
      iotCategory,
      topics
    };

    const headers = await this.getHeader();
    return this.http.post(GRAPHQL_SERVER_URL, {
      query: query,
      variables: variables
    }, { headers });
  }



  public async getAllIots() {

  }
}


export { RESOURCE_SERVER_URL, GRAPHQL_SERVER_URL, REST_SERVER_URL }
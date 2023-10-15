import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "./auth.guard";
import {HttpClientModule} from "@angular/common/http";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {initializer} from "./app.init";
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgChartsModule } from 'ng2-charts';
import { DeviceSettingsComponent } from './pages/device-settings/device-settings.component';
import { AnalyticsComponent } from './pages/analytics/analytics.component';
import { CreateDeviceDialogComponent } from './components/create-device-dialog/create-device-dialog.component';
import { EditDeviceDialogComponent } from './components/edit-device-dialog/edit-device-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import { DeviceDetailsComponent } from './components/device-details/device-details.component';
import {MatCardModule} from "@angular/material/card";

const routes: Routes = [
  {path: '', component: DashboardComponent, canActivate: [AuthGuard]},
  {path: 'device-settings', component: DeviceSettingsComponent, canActivate: [AuthGuard]},
  {path: 'analytics', component: AnalyticsComponent, canActivate: [AuthGuard]},
  // {path: 'protected', component: ProtectedComponent, canActivate: [AuthGuard], data: {roles: ['user']}},
];

@NgModule({
  bootstrap: [AppComponent],
  declarations: [
    AppComponent,
    DashboardComponent,
    DeviceSettingsComponent,
    AnalyticsComponent,
    CreateDeviceDialogComponent,
    EditDeviceDialogComponent,
    DeviceDetailsComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    KeycloakAngularModule,
    RouterModule.forRoot(routes),
    MatToolbarModule,
    MatButtonModule,
    BrowserAnimationsModule,
    NgChartsModule,
    MatDialogModule,
    MatCardModule,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    }
  ]
})
export class AppModule {
}

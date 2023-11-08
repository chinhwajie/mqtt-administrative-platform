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
import { CreateDeviceComponent } from './components/create-device/create-device.component';
import { EditDeviceDialogComponent } from './components/edit-device-dialog/edit-device-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import { DeviceDetailsComponent } from './components/device-details/device-details.component';
import {MatCardModule} from "@angular/material/card";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {MatIconModule} from "@angular/material/icon";
import { DevicesListComponent } from './components/device-list/devices-list.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatExpansionModule} from "@angular/material/expansion";
import { TopicsSettingComponent } from './components/topics-setting/topics-setting.component';
import { CategorySettingsComponent } from './components/category-settings/category-settings.component';
import {MatTableModule} from "@angular/material/table";
import { AmapComponent } from './components/amap/amap.component';
import { PieChartComponent } from './components/pie-chart/pie-chart.component';
import {NgxChartsModule} from "@swimlane/ngx-charts";
import { DevicesBrowserComponent } from './components/devices-browser/devices-browser.component';
import {MatRadioModule} from "@angular/material/radio";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatDatepickerModule} from "@angular/material/datepicker";

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
    CreateDeviceComponent,
    EditDeviceDialogComponent,
    DeviceDetailsComponent,
    DevicesListComponent,
    TopicsSettingComponent,
    CategorySettingsComponent,
    AmapComponent,
    PieChartComponent,
    DevicesBrowserComponent,
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
    MatPaginatorModule,
    MatSidenavModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    ReactiveFormsModule,
    MatExpansionModule,
    MatTableModule,
    NgxChartsModule,
    MatRadioModule,
    MatCheckboxModule,
    MatGridListModule,
    MatDatepickerModule,
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

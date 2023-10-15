import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "./auth.guard";
import {HttpClientModule} from "@angular/common/http";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {initializer} from "./app.init";
import { HomeComponent } from './home/home.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  // {path: 'protected', component: ProtectedComponent, canActivate: [AuthGuard], data: {roles: ['user']}},
  // {path: 'users', component: UserManagementComponent, canActivate: [AuthGuard], data: {roles: ['user', 'manage-users']}},
  // {path: 'profile', component: UserInfoComponent, canActivate: [AuthGuard], data: {roles: ['user']}},
];

@NgModule({
  bootstrap: [AppComponent],
  declarations: [
    AppComponent,
    HomeComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    KeycloakAngularModule,
    RouterModule.forRoot(routes),
    MatToolbarModule,
    MatButtonModule,
    BrowserAnimationsModule,
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

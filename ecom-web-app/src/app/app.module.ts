import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductsComponent } from './products/products.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { CustomersComponent } from './customers/customers.component';
import { OrdersComponent } from './orders/orders.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {AuthInterceptor} from "./services/AuthInterceptor";

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: window.location.hostname === 'localhost' ? 'http://localhost:31095' : 'http://keycloak.local',
        realm: 'amasoft-realm',
        clientId: 'amasoft-angular-app'
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      }
    }).then((authenticated) => {
      console.log('Keycloak initialized, authenticated:', authenticated);
      if (authenticated) {
        keycloak.getToken().then(token => {
          console.log('Token:', token);
        });
      }
    }).catch(error => {
      console.error('Error initializing Keycloak', error);
      console.dir(error);
    });
}
@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    CustomersComponent,
    OrdersComponent,
    OrderDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule ,
    KeycloakAngularModule
  ],
  providers: [    {provide : APP_INITIALIZER, deps : [KeycloakService],useFactory : initializeKeycloak, multi : true} ,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

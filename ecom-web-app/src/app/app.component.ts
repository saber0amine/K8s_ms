import {Component, OnInit} from '@angular/core';
// import {SecurityService} from "./services/security.service";
// import {KeycloakService} from "keycloak-angular";
// import {KeycloakProfile} from "keycloak-js";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
 })
//implements OnInit
export class AppComponent {
  title = 'angular-front-app';
  // public profile? : KeycloakProfile;
  // constructor(public keycloakService:KeycloakService) {
  // }
  // ngOnInit() {
  //   if(this.keycloakService.isLoggedIn()){
  //     this.keycloakService.loadUserProfile().then(profile=>{
  //       this.profile=profile;
  //       console.log('User profile loaded:', profile);
  //
  //     });
  //   }
  // }
  //
  // async login() {
  //   await this.keycloakService.login({
  //     redirectUri: window.location.origin
  //   });
  // }
  //
  // logout() {
  //   this.keycloakService.logout(window.location.origin)
  // }
}

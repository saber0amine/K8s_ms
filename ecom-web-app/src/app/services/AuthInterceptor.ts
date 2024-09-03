import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import {from, mergeMap, Observable} from 'rxjs';
import { KeycloakService } from 'keycloak-angular';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private keycloak: KeycloakService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return from(this.keycloak.getToken()).pipe(
      mergeMap(token => {
        if (token) {
          console.log('Token received:', token);
          const authRequest = request.clone({
            setHeaders: {
              Authorization: `Bearer ${token}`
            }
          });
          console.log('Request with auth header:', authRequest);
          return next.handle(authRequest);
        }
        console.log('No token available');
        return next.handle(request);
      })
    );
  }
}

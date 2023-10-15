import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {KeycloakAuthGuard, KeycloakService} from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

  constructor(router: Router, keycloak: KeycloakService) {
    super(router, keycloak);
  }

  async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    // console.log(state)
    // if (state.url === '/') {
    //   return true;
    // }
    if (!this.authenticated) {
      return false;
    }

    const requiredRoles = route.data['roles'];

    if (!requiredRoles || requiredRoles.length === 0) {
      return true;
    }
    // console.log(this.roles);

    for (let role of requiredRoles) {
      if (!this.roles.includes(role)) return false;
    }
    return true;
  }

  override canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean | UrlTree> {
    return super.canActivate(route, state);
  }
}

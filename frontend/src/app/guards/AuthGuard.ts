import {CanActivate, Router} from '@angular/router';
import {AuthService} from '../services/authService';
import 'rxjs/add/operator/map';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/first';
import {Injectable} from '@angular/core';
import 'rxjs/add/operator/take';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';

@Injectable()
export class AuthGuard implements CanActivate {

  private USER_INFO_URL = 'http://localhost:8080/user';

  constructor(private http: HttpClient, public router: Router) {
  }

  canActivate(): Observable<boolean> {

    return this.http.get(this.USER_INFO_URL, {responseType: 'text'}).map(signedIn => {
      if (signedIn === 'Not authorized') {
        this.router.navigate(['/signin']);
        return false;
      } else {
        return true;
      }
    }).first();
  }
}

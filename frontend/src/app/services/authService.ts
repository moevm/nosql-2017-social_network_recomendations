import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/first';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/take';

@Injectable()
export class AuthService {

  private USER_INFO_URL = 'http://localhost:8080/user';

  private isAuthorized = false;
  constructor(private http: HttpClient) {
  }

  isSignedIn() {
    return this.isAuthorized;
  }
}

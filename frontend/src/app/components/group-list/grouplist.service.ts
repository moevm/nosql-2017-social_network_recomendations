import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {GroupDTO} from '../../models/GroupDTO';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import {GraphDTO} from '../../models/GraphDTO';
import {Person} from '../../models/Person';

@Injectable()
export class GrouplistService {

  private url = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {

  }

  getRecommendations(withFriendWeight: boolean = false, limit: number = 10): Observable<GroupDTO[]> {
    return this.http.get(`${this.url}/getRecommendations?withFriendWeight=${withFriendWeight}&limit=${limit}`).map(res => res as GroupDTO[]);
  }

  getFriendsGroups(friends: number[], onlyCommon: boolean): Observable<GraphDTO> {
    return this.http.get(`${this.url}/getGroupsByIds?onlyCommon=${onlyCommon}&ids=${friends.join(',')}`).map(res => res as GraphDTO);
  }

  getFriends(): Observable<Person> {
    return this.http.get(`${this.url}/friends`).map(res => res as Person);
  }
}

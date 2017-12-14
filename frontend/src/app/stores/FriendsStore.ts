import {Injectable} from '@angular/core';
import * as Rx from '@reactivex/rxjs';

const initialState: any[] = [];

@Injectable()
export class FriendsStore {

  marked: Rx.ReplaySubject<any[]> = new Rx.ReplaySubject(1);
  updates: Rx.Subject<any> = new Rx.Subject<any>();
  addMark: Rx.Subject<any> = new Rx.Subject<any>();
  deleteMark: Rx.Subject<any> = new Rx.Subject<any>();

  constructor() {
    this.updates
      .scan((accumulator: Object[], operation: Function) => {
        return operation(accumulator);
      }, initialState)
      .subscribe(this.marked);

    this.addMark
      .map((friend) => {
        return (state) => {
          return [...state, friend];
        };
      })
      .subscribe(this.updates);


    this.deleteMark
      .map((friend) => {
        return (state) => {
          return state.filter((friendItem) => friendItem !== friend);
        };
      })
      .subscribe(this.updates);

  }

  addMarked(friend) {
    this.addMark.next(friend);
  }

  deleteMarked(friend) {
    this.deleteMark.next(friend);
  }
}

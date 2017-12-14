import {Component, Input, OnInit} from '@angular/core';
import {Person} from '../../models/Person';
import {GrouplistService} from '../group-list/grouplist.service';
import {Observable} from 'rxjs/Observable';
import {FriendsStore} from '../../stores/FriendsStore';

@Component({
  selector: 'app-friend-list',
  templateUrl: './friend-list.component.html',
  styleUrls: ['./friend-list.component.scss']
})
export class FriendListComponent implements OnInit {
  @Input() friends: Person[];

  markedList: any[] = [];

  constructor(public friendsStore: FriendsStore) {

  }

  ngOnInit() {
    this.friendsStore.marked.subscribe(data => this.markedList = data);
  }

  isMarked(friend) {
    return this.markedList.includes(friend);
  }

}

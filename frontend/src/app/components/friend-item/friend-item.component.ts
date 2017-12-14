import {Component, Input, OnInit} from '@angular/core';
import {FriendsStore} from '../../stores/FriendsStore';
import {Person} from '../../models/Person';

@Component({
  selector: 'app-friend-item',
  templateUrl: './friend-item.component.html',
  styleUrls: ['./friend-item.component.scss']
})
export class FriendItemComponent implements OnInit {

  @Input() friend: Person;
  @Input() isMarked: boolean;

  constructor(private friendsStore: FriendsStore) {
  }

  ngOnInit() {
  }

  changeMark(): void {
    if (!this.isMarked) {
      this.friendsStore.addMarked(this.friend);
    } else {
      this.friendsStore.deleteMarked(this.friend);
    }
  }
}

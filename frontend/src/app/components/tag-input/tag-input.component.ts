import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FriendsStore} from '../../stores/FriendsStore';

@Component({
  selector: 'app-tag-input',
  templateUrl: './tag-input.component.html',
  styleUrls: ['./tag-input.component.scss']
})
export class TagInputComponent implements OnInit {

  items: any[] = [];

  @Output() change = new EventEmitter();

  constructor(public friendsStore: FriendsStore) {
  }

  ngOnInit() {
    this.friendsStore.marked.subscribe(data => this.items = data);
  }

  onSearchChange(s: string) {
    this.change.emit(s);
  }

}

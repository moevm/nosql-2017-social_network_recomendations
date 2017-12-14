import {Component, Input, OnInit} from '@angular/core';
import {GroupDTO} from '../../models/GroupDTO';

@Component({
  selector: 'app-group-item',
  templateUrl: './group-item.component.html',
  styleUrls: ['./group-item.component.scss']
})
export class GroupItemComponent implements OnInit {

  @Input() groupDTO: GroupDTO;

  constructor() {
  }

  ngOnInit() {
  }

}

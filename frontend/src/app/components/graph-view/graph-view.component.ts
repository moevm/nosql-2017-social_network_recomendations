import {Component, OnInit} from '@angular/core';
import {GrouplistService} from '../group-list/grouplist.service';
import {Link, Node} from '../../d3/models';
import {Person} from "../../models/Person";
import {Observable} from "rxjs/Observable";
import {FriendsStore} from "../../stores/FriendsStore";

@Component({
  selector: 'app-graph-view',
  templateUrl: './graph-view.component.html',
  styleUrls: ['./graph-view.component.scss']
})
export class GraphViewComponent implements OnInit {

  nodes: Node[] = [];
  links: Link[] = [];
  friends: Observable<Person>;
  markedList: any[] = [];
  searchString = '';
  isOnlyCommon = false;

  constructor(private grouplistService: GrouplistService, private friendsStore: FriendsStore) {
  }

  ngOnInit() {
    this.friends = this.grouplistService.getFriends();
    this.friendsStore.marked.subscribe(data => this.markedList = data);
  }

  changeSearchString(event) {
    this.searchString = event;
  }

  onButtonClick() {
    this.grouplistService.getFriendsGroups(this.markedList.map(item => item.vkId), this.isOnlyCommon)
      .subscribe(graph => this.parseGraphDTO(graph));
  }

  parseGraphDTO = graph => {
    const userNodes = graph.users.map(user => new Node(`id${user.vkId}`, user.avatar_200));
    const groupNodes = graph.groups.map(group => new Node(`public${group.vkId}`, group.photo_200));
    this.nodes = [...userNodes, ...groupNodes];
    this.links = graph.links.map(link => {
      this.nodes.find(value => value.id === `id${link.source}`).linkCount++;
      this.nodes.find(value => value.id === `public${link.target}`).linkCount++;
      return new Link(`id${link.source}`, `public${link.target}`);
    });
  }

}

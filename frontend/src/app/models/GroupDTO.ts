import {Group} from './Group';
import {Person} from './Person';

export class GroupDTO {
  group: Group;
  friends: Person[];
  rating: number;

  constructor(group: Group, friends: Person[], rating: number) {
    this.group = group;
    this.friends = friends;
    this.rating = rating;
  }
}

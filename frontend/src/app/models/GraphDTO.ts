import {Person} from './Person';
import {Group} from './Group';
import {Link} from '../d3/models/link';

export class GraphDTO {
  users: Person[];
  groups: Group[];
  links: Link[];

  constructor(users: Person[], groups: Group[], links: Link[]) {
    this.users = users;
    this.groups = groups;
    this.links = links;
  }
}

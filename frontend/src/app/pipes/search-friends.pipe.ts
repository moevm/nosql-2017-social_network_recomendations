import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'searchFriends'
})
export class SearchFriendsPipe implements PipeTransform {

  transform(friends: any, searchString: string): any {
    if (searchString.length !== 0) {
      return friends.filter(item => item.name.toLowerCase().includes(searchString.toLowerCase()));
    } else {
      return friends;
    }
  }

}

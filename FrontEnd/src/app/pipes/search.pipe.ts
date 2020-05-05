import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'search'
})
export class SearchPipe implements PipeTransform {

/*search pipe for searching on list-users of admin where we can search using searchText 
   and that searchText */


  transform(usersList: any, searchText: any): any {

    let newList: any;

    if (searchText) {
      newList = usersList.filter(user => user.customer_name.toLowerCase().startsWith(searchText.toLowerCase()));
    }
    else {
      newList = usersList;
    }
    return newList;
  }

}

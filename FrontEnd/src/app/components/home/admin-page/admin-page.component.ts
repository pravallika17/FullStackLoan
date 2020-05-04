import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Customer } from 'src/assets/model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  users: Customer[];
  searchText: any;
  constructor(private service: UserService, private router: Router) { }

  ngOnInit() {
    this.getUsers();
  }
  getUsers() {
    this.service.getAllUsers().subscribe(
      data => {
        this.users = data;
      },
      err => { console.log(err.stack); }
    )
  }

  deleteUser(username: string) {
    console.log(username);
    this.service.deleteUser(username).subscribe(
      data => { alert('deleted successfully') }
    )

  }
  logout() {
    if (localStorage.userName == "admin@12") {
      localStorage.removeItem("admin@12");
      this.router.navigate(['/home/abchome']);



    }
    else {
      this.router.navigate(['']);

    }
  }
}

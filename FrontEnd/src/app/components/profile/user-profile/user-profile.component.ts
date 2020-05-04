import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { Customer, LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
  providers: [LoggingService]
})
export class UserProfileComponent implements OnInit {
  constructor(private service: UserService, private router: Router , private loggingService: LoggingService) { }

  user: Customer;
  ngOnInit() {
    // if username exists
    if (localStorage.userName != null) {
      this.getDetails();
    }
    //if user not exists in localstroage redirects to login page
    else {
      this.router.navigate(['/home/login']);
    }
  }
  //get details of customer
  getDetails() {
    this.service.getCustomer(localStorage.userName).subscribe(data => {
      // on resolve or on success
      this.user = data;
      console.log(this.user);
      this.loggingService.logStatus(localStorage.userName+" user profile showed successfully");
    },
      err => {
        // on reject or on error
        console.log(err.stack);
      });
  }


}

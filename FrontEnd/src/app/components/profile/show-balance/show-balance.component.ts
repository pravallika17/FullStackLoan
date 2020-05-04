import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { Customer, LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-show-balance',
  templateUrl: './show-balance.component.html',
  styleUrls: ['./show-balance.component.css'],
  providers: [LoggingService]
})
export class ShowBalanceComponent implements OnInit {
  constructor(private service:UserService,private router:Router, private loggingService:LoggingService) { }

  user: Customer;
  ngOnInit() {
    
    if(localStorage.userName!=null){
      this.showDetails();
    }
     //if user not exists in localstroage redirects to login page
    else{
      this.router.navigate(['/home/login']);
    }
  }
  showDetails() {
    this.service.getCustomer(localStorage.userName).subscribe(data => {
    //on resolve or success
      this.user = data;
      this.loggingService.logStatus(localStorage.userName+" showed balance successfully");
    },
      err => {
     //on reject or error
        console.log(err.stack);
      });
  }


}

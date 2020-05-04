import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { Customer, Passbook, LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-print-transactions',
  templateUrl: './print-transactions.component.html',
  styleUrls: ['./print-transactions.component.css'],
  providers: [LoggingService]
})
export class PrintTransactionsComponent implements OnInit {
user:Customer;
trans:Passbook[];
no_transactions:boolean=false;

constructor(private service : UserService,private router : Router, private loggingService : LoggingService) { }

  ngOnInit() {
     // if username exists
     if(localStorage.userName!=null){
      this.getUser();
      this.printTransactions();
    }
     //if user not exists in localstroage redirects to login page
    else{
      this.router.navigate(['/home/login']);
    }
  }
    
  getUser()
  {
    this.service.getCustomer(localStorage.userName).subscribe(data => {
      this.user = data;
    },
      err => {
        console.log(err.stack);
      });
  
  }
  printTransactions()
  {
    
      this.service.printTransactions(localStorage.userName).subscribe(data=>
        {this.trans=data;
          this.loggingService.logStatus(localStorage.userName+" print transactions successfull ");
        },
        err=>{
          if(err.error.errorMessage="No Transactions Available Loan")
          //loan_account_not_exists variable is used to handle error in UI
          this. no_transactions=true;
          this.loggingService.logStatus(localStorage.userName+" error thrown because of no transactions available");
      
        })
   

  }

}

import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { Customer, LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-pay-emi',
  templateUrl: './pay-emi.component.html',
  styleUrls: ['./pay-emi.component.css'],
  providers: [LoggingService]
})
export class PayEmiComponent implements OnInit {
  loan_account_not_exists:boolean=false;
  user: Customer;
  calculate_emi:boolean=false;
  message_if_loan_exists:string;
  message_if_calculated:string;
  message_if_deposit_not_sufficient:string;
  deposit_not_sufficient: boolean=false;

  
  constructor(private service:UserService,private router:Router, private loggingService:LoggingService) { }


  ngOnInit() {
    if(localStorage.userName!=null){
      this.pay();
    }
    else{
      this.router.navigate(['/home/login']);
    }
  }
  pay() {
    this.service.getCustomer(localStorage.userName).subscribe(data => {
      this.user = data;
    },
      err => {
        console.log(err.stack);
      });
  }
  payEmi()
  {

              this.service.payEMI(localStorage.userName).subscribe(data=>
                {
                  alert("EMI paid successfully");
                  this.loggingService.logStatus(localStorage.userName+" EMI paid successfully");
                  this.router.navigate(['/profile/showBal']);
                },
                err=>{
                  if(err.error.errorMessage=="Please Apply Loan First"){
                  //loan_account_not_exists variable is used to handle error in UI
                  this. loan_account_not_exists=true;
                  this.message_if_loan_exists=err.error.errorMessage;
                  this.loggingService.logStatus(localStorage.userName+" error thrown because of no loan applied");
                  }

                  if(err.error.errorMessage=="Please Deposit Amount"){
                    //deposit_not_sufficient variable is used to handle error in UI
                    this. deposit_not_sufficient=true;
                    this.message_if_deposit_not_sufficient=err.error.errorMessage;
                    this.loggingService.logStatus(localStorage.userName+" error thrown because of deposit not sufficient");
                    }

                  if(err.error.errorMessage=="Please Calculate EMI"){
                  //loan_account_not_exists variable is used to handle error in UI
                  this. calculate_emi=true;
                  this.message_if_calculated=err.error.errorMessage;
                  this.loggingService.logStatus(localStorage.userName+" error thrown because of no calculated EMI");
                  }
                })
  }
          

}

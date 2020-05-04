import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-fore-close',
  templateUrl: './fore-close.component.html',
  styleUrls: ['./fore-close.component.css'],
  providers: [LoggingService]
})
export class ForeCloseComponent implements OnInit {
  loan_account_not_exists:boolean=false;
  foreclosed:boolean=false;
  message_if_loan_not_exists:boolean=false;


  constructor(private service:UserService,private router:Router, private loggingService:LoggingService) { }

  ngOnInit() {
  // if username exists
  if(localStorage.userName==null){
   //if user not exists in localstroage redirects to login page
    this.router.navigate(['/home/login']);
  }
}
foreClose()
{
      this.service.foreClose(localStorage.userName).subscribe(data=>
      {
        //on resolve or success
        alert('Foreclosing your loan');
        alert('Account Foreclosed successfully..')
        this.foreclosed=true;
        this.router.navigate(['/profile/showBal']);
        this.loggingService.logStatus(localStorage.userName+" Fore closed account successfully"); 
      },err=>{

        if(err.error.errorMessage="Please Apply Loan First"){
        //loan_account_not_exists variable is used to handle error in UI
        this. loan_account_not_exists=true;
        //assigning error message from spring REST to variable message_if_loan_not_exists
        this.message_if_loan_not_exists=err.error.errorMessage;
        this.loggingService.logStatus(localStorage.userName+" error thrown because of no loan applied");
        }
      })
    }
    
}

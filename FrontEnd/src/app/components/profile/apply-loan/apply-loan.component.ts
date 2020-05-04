import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Customer, LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-apply-loan',
  templateUrl: './apply-loan.component.html',
  styleUrls: ['./apply-loan.component.css'],
  providers:[LoggingService],
  
})
export class ApplyLoanComponent implements OnInit {
  asset_value: number;
  loan_amount: number;
  applyForm: FormGroup;
  user: Customer;
  submitted: boolean = false;
  loan_account_exists:boolean=false;
  asset_value_valid:boolean=false;
  no_sufficient_deposit:boolean=false;
  message_if_not_valid_deposit:string;
  message_if_asset_value_not_valid:boolean=false;
  message_if_loan_exists:boolean=false;

  constructor(private service: UserService, private router: Router, private formBuilder: FormBuilder,
                        private loggingService : LoggingService ) { }

  ngOnInit() {
    //validations 
    if(localStorage.userName==null){
      this.router.navigate(['/home/abchome']);
    }
    this.applyForm = this.formBuilder.group({
      loanamount: ['', [Validators.required, Validators.min(10000)]],
      asset: ['', [Validators.required, Validators.min(20000)]]
    });
    this.getCust();


  }
  //get all the customer details
  getCust() {
    this.service.getCustomer(localStorage.userName).subscribe(data => {
      // on resolve or on success
      this.user = data;
      console.log(this.user);
    },
      err => {
        // on reject or on error
        console.log(err.stack);
      });
  }


  applyLoan() {
  this.submitted = true;
  if (this.applyForm.invalid) {
      //if validations fail
      return;
  }
    this.asset_value = this.applyForm.controls.asset.value;
    this.loan_amount = this.applyForm.controls.loanamount.value;
  
    this.service.applyLoan(localStorage.userName, this.asset_value, this.loan_amount).subscribe(data=>{
            if (confirm("Are you sure you want to apply Loan")) {
             alert("loan applied successfully")
               this.router.navigate(['/profile/showBal']);
            }
            this.loggingService.logStatus(localStorage.userName+" Loan applied successfully");
           
          },
          err => {
            if(err.error.errorMessage=="Loan Account With Balance Already Exists") {
                  //loan_account_exists variable is used to handle error in UI
                  this. loan_account_exists=true;
                  this.message_if_loan_exists=err.error.errorMessage;
                  this.loggingService.logStatus(localStorage.userName+"error thrown because of  Loan Account already exists");
            }
    
            if(err.error.errorMessage=="Asset Value Should be 1.5 times greater than loan amount"){
                //asset_value_valid variable is used to handle error in UI
                  this. asset_value_valid=true;
                  this.message_if_asset_value_not_valid=err.error.errorMessage;
                  this.loggingService.logStatus(localStorage.userName+" error thrown because of Asset value condition failed ");
            }

           
            if(err.error.errorMessage=="No sufficient Deposited Amount"){
              //asset_value_valid variable is used to handle error in UI
                this. no_sufficient_deposit=true;
                this.message_if_not_valid_deposit=err.error.errorMessage;
                this.loggingService.logStatus(localStorage.userName+" error thrown because of Asset value condition failed ");
          }
          })

            
          }
}
   



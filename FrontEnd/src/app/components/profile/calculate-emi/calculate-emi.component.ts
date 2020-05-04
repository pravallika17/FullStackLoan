import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Customer, LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-calculate-emi',
  templateUrl: './calculate-emi.component.html',
  styleUrls: ['./calculate-emi.component.css'],
  providers: [LoggingService]
})
export class CalculateEmiComponent implements OnInit {
  calculateForm:FormGroup;
submitted:boolean=false;
tenure:number;
loan_account_not_exists:boolean=false;
calculated_emi:boolean=false;
message_if_loan_exists:string;
message_if_already_calculated:string;


  constructor(private service:UserService, private router:Router, private formBuilder:FormBuilder,
                  private loggingService:LoggingService) { }
  ngOnInit() {
    this.calculateForm=this.formBuilder.group({
      time:['',[Validators.required, Validators.min(5)]]
    });
  }

  calculateEmi()
  {
    this.submitted=true;
    this.calculated_emi=false;
    this.loan_account_not_exists=false;
    if(this.calculateForm.invalid)
      return;
    this.tenure=this.calculateForm.controls.time.value;

        this.service.calculateEmi(localStorage.userName,this.tenure,1.5).subscribe(
          data=>{
            //on resolve or success
            alert('Calculated EMI successully ');
            this.loggingService.logStatus(localStorage.userName+" Calculated EMI successully")
          },
            err=>{
              if(err.error.errorMessage=="Please Apply Loan First"){
                //loan_account_not_exists variable is used to handle error in UI
                this. loan_account_not_exists=true;
                this.message_if_loan_exists=err.error.errorMessage;
                this.loggingService.logStatus(localStorage.userName+" error thrown because of no loan applied");
              }

              if(err.error.errorMessage=="You have already calculated EMI"){
                //calculated_emi variable is used to handle error in UI
                this.calculated_emi=true;
                this.message_if_already_calculated=err.error.errorMessage;
                this.loggingService.logStatus(localStorage.userName+" error thrown because of already calculated emi");
              }
            }
        )
       
      }
    

}

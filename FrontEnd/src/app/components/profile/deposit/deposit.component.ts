import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css'],
  providers: [LoggingService]
})
export class DepositComponent implements OnInit {

  depositForm:FormGroup;
  submitted: boolean=false;
 

  constructor(private router:Router, private service: UserService, private formBuilder:FormBuilder) { }

  ngOnInit() {
    this.depositForm= this.formBuilder.group({
      deposit:['',[Validators.required, Validators.min(0)]]
    })
  }
  deposit()
  {
    this.submitted = true;
    if(this.depositForm.invalid)
      return;
    this.service.deposit(localStorage.userName, this.depositForm.controls.deposit.value).subscribe(
    data=>{
      alert('Amount deposited successfully');
      this.router.navigate(['/profile/showBal']);
    })
   
  }

}

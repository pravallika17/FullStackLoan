import { Component, OnInit, ErrorHandler } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Customer, LoggingService } from 'src/assets/model';
import { UserService } from 'src/app/service/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers:[LoggingService]
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  user: Customer;
  invalid_Login_Fields:boolean=false;
  error_message:string;
  submitted: boolean = false;
  


  constructor(private router: Router, private formBuilder: FormBuilder, private service: UserService,
                 private loggingService:LoggingService ) {

  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group(
      {
        userName: ['', [Validators.required]],
        password: ['', [Validators.required]]
      });
    if (localStorage.userName != null) {
      this.router.navigate(['']);
      localStorage.removeItem("userName");
      sessionStorage.removeItem("userName");
      sessionStorage.removeItem("password");

    }
  }

  login(){
    this.submitted=true;
    if(this.loginForm.invalid){
      return;
    }
  //validateUser function validates whether he is a correct user
    this.service.validateUser(this.loginForm.controls.userName.value,this.loginForm.controls.password.value).subscribe(data => {
     
      //on resolve or success
     if(data==true){
        localStorage.userName=this.loginForm.controls.userName.value;
        sessionStorage.userName=this.loginForm.controls.userName.value;
        //if login details are authenticated correct, navigate to user profile.
        this.router.navigate(['/profile/userprofile']);
      }
      this.loggingService.logStatus(localStorage.userName+" Login successful")

    },
    //on error
      err => {
        //invalid_Login_Fields variable to handle UI part of the component
        this.invalid_Login_Fields=true;
        //error message from spring REST is assigned to variable error_message
        this.error_message=err.error.errorMessage;   
      });
      this.loggingService.logStatus(localStorage.userName+" Login Failed");
    }
    invalidLogin:boolean=false;
    
  
}
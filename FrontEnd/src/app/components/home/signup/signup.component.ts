import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators, PatternValidator } from '@angular/forms';
import { UserService } from 'src/app/service/user.service';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signUpForm: FormGroup;
  submitted: boolean = false;
  user_name_exists:boolean=false;
  phone_number_exists:boolean=false;
  password_mismatch:boolean=false;
  err_message_for_username:string;
  err_message_for_password:string;


  constructor(private router: Router, private formBuilder: FormBuilder, private service: UserService) {



  }

  ngOnInit() {
    if (localStorage.userName != null) {
      localStorage.removeItem("userName");
    }
//  validations for form using Form Builder
    this.signUpForm = this.formBuilder.group({
      customer_name: ['', [Validators.required, Validators.pattern("[A-Z][a-zA-Z]*")]],
      phone_number: ['', [Validators.required, Validators.pattern("[6-9][0-9]{9}")]],
      email: ['', [Validators.required, Validators.email]],
      user_name: ['', [Validators.required, Validators.pattern("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.*\\s).{6,10}$")]],
      password: ['', [Validators.required, Validators.pattern("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.*\\s).{6,10}$")]],
      pwdrept: ['', Validators.required],
      deposit:['',[Validators.required, Validators.min(1000)]]
    });

  }


  signUp() {
    this.submitted = true;
 
    if (this.signUpForm.invalid) {
      //if validation fails which are defined in ngOnInit
      return;
    }
    if (this.signUpForm.controls.password.value != this.signUpForm.controls.pwdrept.value) {
     //if password mismatches
      this.password_mismatch= true;
      return;
    }

    this.service.createNewUser(this.signUpForm.value).subscribe(data => {
      //on resolve or success
      alert(`${this.signUpForm.controls.customer_name.value} record is added successfully ..!`);
      this.router.navigate(['/home/login']);
      }, 
    err => {
      if(err.error.errorMessage="User Name already exists")
        {
              //user_name_exists variable is used to handle error 
              this.user_name_exists=true;
              /*err object being returned from spring REST is being set 
              into errors object and this error is given by spring */
              this.err_message_for_username= err.error.errorMessage;
        }

        if(err.error.errorMessage="Phone Number already exists")
            //user_name_exists variable is used to handle error 
              this.phone_number_exists=true;
                /*err object being returned from spring REST is being set 
              into errors object and this error is given by spring */
              this.err_message_for_password= err.error.errorMessage;
        })
      }
     
   
}

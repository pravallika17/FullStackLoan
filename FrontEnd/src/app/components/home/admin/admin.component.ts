import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  submitted:boolean=false;
  adminForm: FormGroup;
  invalid_login:boolean=false;
constructor(private router:Router, private formBuilder:FormBuilder) { }

  ngOnInit() {
this.adminForm= this.formBuilder.group({
  userName :['',Validators.pattern],
  password:['',Validators.pattern]
  
})

  }



admin()
{
this.submitted=true;
if(this.adminForm.invalid)
  return;
if(this.adminForm.controls.userName.value == "admin@12" && this.adminForm.controls.password.value== "admin@12"){
  localStorage.userName = "admin@12";
    this.router.navigate(['adminpage']);
 this.invalid_login=true;
}
else
  return;


}

}

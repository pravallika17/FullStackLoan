import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoggingService } from 'src/assets/model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  providers: [LoggingService]
})
export class ProfileComponent implements OnInit {

  constructor(private router:Router, private loggingService: LoggingService) { }

  ngOnInit() {
   

}
logout()
{
  if(localStorage.userName!=null)
  {
    localStorage.removeItem("userName");
    sessionStorage.removeItem("userName");
  }
  else
  {
    this.router.navigate(['/home/abchome'])
    this.loggingService.logStatus(localStorage.userName+" logged out successfully ");
  }
}
toggleSidebar(){
  var x= document.getElementById("sidebar");
  var y=document.getElementById("content");
  if(x.classList.contains("active")&& y.classList.contains("no")){
    x.classList.remove("active");
    x.classList.add("notactive");
    y.classList.remove("no");
    y.classList.add("yes");
    
  }
  else
  {
    x.classList.remove("notactive");
    x.classList.add("active");
    y.classList.remove("yes");
    y.classList.add("no");
  }
  
}

}

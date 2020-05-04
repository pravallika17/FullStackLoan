import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer, Passbook } from 'src/assets/model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

baseUrl: string = "http://localhost:8094/api";

getAllUsers()
{
  return this.http.get<Customer[]> (this.baseUrl + "/all")
}

  validateUser(uname:string,pwd:string)
  {
     return this.http.get<boolean>(this.baseUrl + "/checkUser" +"/"+ uname+ "/" +pwd);

  }
  userNameExists(uname:string)
  {
    return this.http.get<boolean>(this.baseUrl+"/userNameexists"+"/"+uname);
  }
  createNewUser(cust:Customer)
  {
    return this.http.post (this.baseUrl+"/add",cust);
  }
  getCustomer(uname:string)
  {
    return this.http.get<Customer> (this.baseUrl +"/getCust/" +uname);
  }
 

  applyLoan(uname:string,asset:number, loan:number)
  {
    return this.http.get<number> (this.baseUrl +"/apply/"+uname+"/"+asset+"/"+loan);
  }

  
  calculateEmi(un:string, month:number, roi:number)
  {
    return this.http.get<number> (this.baseUrl+"/calculate/"+un+"/"+month+"/"+1.5);
  }
  
  payEMI(un:string)
{
  return this.http.get<number> (this.baseUrl+"/payEMI/"+un);
}  
foreClose(uname:string)
{
  return this.http.get<number>(this.baseUrl+"/foreClose/"+uname);
}
checkPrint(uname:String)
{
  return this.http.get<number>(this.baseUrl+"/checkPrint/"+uname);
}
printTransactions(uname:String)
{
  return this.http.get<Passbook[]>(this.baseUrl+"/print/"+uname);
}
checkUser(uname:string,password:string)
{
  return this.http.get<number>(this.baseUrl+"/checkUser/"+uname+"/"+password);
}
  phNoExists(phNo:string)
  {
    return this.http.get<boolean>(this.baseUrl+"/checkPhNo/"+phNo);
  }
  deposit( uname:string, amount:number){
    return this.http.get<number>(this.baseUrl +"/deposit/"+ uname+"/"+amount);
  }
  editUser(customer:Customer)
  {
    return this.http.put(this.baseUrl +"/edit",customer);
  }
  deleteUser(uname:string){
      return this.http.delete(this.baseUrl+"/delete/"+ uname);
    }
  }


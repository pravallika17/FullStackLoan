import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AbcHomeComponent } from './components/home/abc-home/abc-home.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ShowBalanceComponent } from './components/profile/show-balance/show-balance.component';
import { ApplyLoanComponent } from './components/profile/apply-loan/apply-loan.component';
import { CalculateEmiComponent } from './components/profile/calculate-emi/calculate-emi.component';
import { ForeCloseComponent } from './components/profile/fore-close/fore-close.component';
import { PrintTransactionsComponent } from './components/profile/print-transactions/print-transactions.component';
import { LoginComponent } from './components/home/login/login.component';
import { SignupComponent } from './components/home/signup/signup.component';
import { SecurityComponent } from './components/home/security/security.component';
import { UserProfileComponent } from './components/profile/user-profile/user-profile.component';
import { PayEmiComponent } from './components/profile/pay-emi/pay-emi.component';
import { AboutUsComponent } from './components/home/about-us/about-us.component';
import { AdminPageComponent } from './components/home/admin-page/admin-page.component';
import { AdminComponent } from './components/home/admin/admin.component';
import { DepositComponent } from './components/profile/deposit/deposit.component';
import { ContactUsComponent } from './components/home/contact-us/contact-us.component';
import { TermsConditionsComponent } from './components/home/terms-conditions/terms-conditions.component';



const routes: Routes = [
  {path:'',component:HomeComponent,children:[{path:'',component:AbcHomeComponent}]},

  {path:'profile',component:ProfileComponent,children:[{path:'',component:UserProfileComponent},
                                                      {path:'showBal',component:ShowBalanceComponent},
                                                      {path:'userprofile',component:UserProfileComponent},
                                                      {path:'apply',component:ApplyLoanComponent},
                                                      {path:'payEmi',component:PayEmiComponent},
                                                      {path:'calcEmi',component:CalculateEmiComponent},
                                                      {path:'foreClose',component:ForeCloseComponent},
                                                      {path:'print',component:PrintTransactionsComponent},
                                                      {path:'deposit',component: DepositComponent},
                                                      {path:'**',component:ShowBalanceComponent}  
                                                          
  ]},
  {path:'home',component:HomeComponent, children:[{path:'',component:AbcHomeComponent},
                                                  {path:'abchome',component:AbcHomeComponent},
                                                  {path:'login',component:LoginComponent},
                                                  {path:'signup',component:SignupComponent},
                                                  {path:'security',component:SecurityComponent},
                                                  {path:'aboutus', component:AboutUsComponent},
                                                  {path:'admin',component:AdminComponent},
                                                  {path:'contact',component:ContactUsComponent},
                                                  {path:'t&c', component:TermsConditionsComponent}
                                                   
                                                  
                                                
]},
                                        
{path:'adminpage',component:AdminPageComponent},                                            
{path:'**',component:AbcHomeComponent}]
  

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AbcHomeComponent } from './components/home/abc-home/abc-home.component';
import { SecurityComponent } from './components/home/security/security.component';
import { LoginComponent } from './components/home/login/login.component';
import { SignupComponent } from './components/home/signup/signup.component';
import { ApplyLoanComponent } from './components/profile/apply-loan/apply-loan.component';
import { CalculateEmiComponent } from './components/profile/calculate-emi/calculate-emi.component';
import { ForeCloseComponent } from './components/profile/fore-close/fore-close.component';
import { PayEmiComponent } from './components/profile/pay-emi/pay-emi.component';
import { ShowBalanceComponent } from './components/profile/show-balance/show-balance.component';
import { PrintTransactionsComponent } from './components/profile/print-transactions/print-transactions.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserProfileComponent } from './components/profile/user-profile/user-profile.component';
import { HttpClientModule } from '@angular/common/http';
import { AdminComponent } from './components/home/admin/admin.component';
import { AdminPageComponent } from './components/home/admin-page/admin-page.component';
import { AboutUsComponent } from './components/home/about-us/about-us.component';
import { DepositComponent } from './components/profile/deposit/deposit.component';
import { ContactUsComponent } from './components/home/contact-us/contact-us.component';
import { TermsConditionsComponent } from './components/home/terms-conditions/terms-conditions.component';
import { SearchPipe } from './pipes/search.pipe';





@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProfileComponent,
    AbcHomeComponent,
    SecurityComponent,
    LoginComponent,
    SignupComponent,
    ApplyLoanComponent,
    CalculateEmiComponent,
    ForeCloseComponent,
    PayEmiComponent,
    ShowBalanceComponent,
    PrintTransactionsComponent,
    UserProfileComponent,
    AdminComponent,
    AdminPageComponent,
    AboutUsComponent,
    DepositComponent,
    ContactUsComponent,
    TermsConditionsComponent,
    SearchPipe,
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
   
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

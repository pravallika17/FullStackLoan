export class Customer{
    account_number:number;
    user_name:string;
    password:string;
    email:string;
    customer_name:string;
    phone_number:string;
    assasset_value:number;
    loan_amount:number;
    deposit:number;
    emi:number;
}
export class Passbook{
    transaction_id:number;
    operation:string;
    amount:number;
    transaction_time:string;
    transaction_date:String;
    
}

export class LoggingService{
    logStatus(message: string){
        let currentDateTime=new Date();
        let currentDateTimeString= currentDateTime.toDateString() + currentDateTime.getTime() ;
        let currentDateTimeString2=currentDateTime.toString();
        console.log(` ${ (currentDateTimeString) } : `,message);
        console.log(` ${(currentDateTimeString2)} : `, message);
    }
}








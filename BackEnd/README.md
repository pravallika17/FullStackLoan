##Please run the below DDL query before you execute this application.  

CREATE TABLE Customer(custName varchar2(20),accNumber number primary key, email varchar2(20), userName varchar2(20),
				 password varchar2(20), phNo varchar2(10),loanAccBal number, assetVal number,emi number);
			        





 insert into Customer values('Pravallika','1010110110','pravallika@gmail.com','Prava@12','Prava@12','9090900900',2000000,3000000,122);

 create table Passbook(tId number primary key, accNum number,transaction varchar2(30), amount number, tDate varchar2(20),
 tTime varchar2(20));


INSERT into Passbook values(1,1010110110,'Account Created!',0,'09-04-2020','01:03');

commit;
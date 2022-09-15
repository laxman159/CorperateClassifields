drop table if exists user;
drop table if exists points;


create Table user(
	empid int primary key auto_increment,
	emp_username varchar(50) not null,
	emp_password varchar(50) not null
	);
    
create Table points(
	pointsid int primary Key auto_increment,
    empid int not null,
    points int,
    offerid int not null
);

insert into user(empid,emp_username,emp_password) values (1,'Laxman','12345');
insert into user(empid,emp_username,emp_password) values (2,'Niharika','54321');

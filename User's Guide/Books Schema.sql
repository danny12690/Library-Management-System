create schema books;

create table demo_book(book_id varchar(10),book_id2 varchar(13),Title varchar(50), authors varchar(50),Publisher varchar(20), covers varchar(100), pages varchar(10));

load data infile 'c:/users/danny12690/documents/books_sample.csv' into table demo_book fields terminated by ',' lines terminated by '\n';

create table book(book_id varchar(10) primary key, title varchar(50));

insert into book (book_id,title) select (book_id,title) from demo_book;

create table book_authors(book_id varchar(10),authors varchar(50), primary key(book_id,authors), foreign key (book_id) references book(book_id));

insert into book_authors (book_id,authors) select (book_id,authors) from demo_book;

create table library_branch (branch_id varchar(10),branch_name varchar(50),address varchar(50));

load data infile 'c:/users/danny12690/documents/library_branch_sample.csv' into table library_branch fields terminated by ',' lines terminated by '\n';

create table demo_book_copies (book_id varchar(10),branch_id varchar(10),no_of_copies integer(10));

load data infile 'c:/users/danny12690/documents/book_copies_sample.csv' into table demo_book_copies fields terminated by ',' lines terminated by '\n';

delete from demo_book_copies where demo_book_copies.book_id not in (select book_id from demo_book);

create table book_copies(book_id varchar(10),branch_id varchar(10), no_of_copies integer(10), primary key (book_id,branch_id),foreign key (book_id) references book(book_id),foreign key (branch_id) references library_branch(branch_id));

create table demo_borrower (card_no integer(10) primary key,fname varchar (10),lname varchar(10),address varchar(100),address2 varchar(50),address3 varchar(3),phone varchar(15));

load data infile 'c:/users/danny12690/documents/borrowers.csv' into table demo_borrower fields terminated by ',' lines terminated by '\n';

create table borrower (card_no integer(10) primary key,fname varchar (10),lname varchar(10),address varchar(100),phone varchar(15));

insert into borrower (card_no,fname,lname,address,phone) select (card_no,fname,lname,address,phone) from demo_borrower;

create table demo_book_loans (book_id varchar(10),branch_id varchar (10),card_no integer(10),date_out date,due_date date,date_in date);

load data infile 'c:/users/danny12690/documents/book_loans_sample.csv' into table demo_book_loans fields terminated by ',' lines terminated by '\n';

delete from demo_book_loans where demo_book_loans.card_no not in (select card_no from demo_borrower);

create table book_loans(book_id varchar(10),branch_id varchar(10),card_no integer(10),date_out date,due_date date,date_in date,primary key(book_id,branch_id,card_no),foreign key (book_id) references book(book_id),foreign key branch_id references library_branch(branch_id),foreign key (card_no) references borrower(card_no));

insert into book_loans (book_id,branch_id,card_no,date_out,due_date,date_in) select (book_id,branch_id,card_no,date_out,due_date,date_in) from demo book_loans;

drop table demo_book;
drop table demo_book_copies;
drop table demo_borrower;

create table fine(card_no integer(10) primary key,fine double (5,3),foreign key (card_no) references borrower(card_no));
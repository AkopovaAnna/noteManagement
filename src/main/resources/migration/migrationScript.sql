create schema note_db


create table users
(
	user_id int auto_increment,
	full_name varchar(255) not null,
	email varchar(255) not null,
	created date not null,
	updated date not null,
	password varchar(1024) not null,
	constraint users_pk
		primary key (user_id)
);

create unique index users_email_uindex
	on users (email);


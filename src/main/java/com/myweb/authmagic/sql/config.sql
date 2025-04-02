create database hospital;

use hospital;

create table doctors (
    id int primary key auto_increment,
    name varchar(255) not null,
    email varchar(255) not null,
    department varchar(255) not null,
    create_at timestamp default current_timestamp
);

create table appointments (
    id int primary key auto_increment,
    doctor_id int not null,
    patient varchar(255) not null,
    date datetime not null,
    time varchar(10) not null,
    create_at timestamp default current_timestamp
);

create table users (
    id int primary key auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    create_at timestamp default current_timestamp
);
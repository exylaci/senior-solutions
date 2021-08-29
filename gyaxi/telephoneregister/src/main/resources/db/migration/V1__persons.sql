create table if not exists persons (
    id bigint not null auto_increment primary key,
    comment varchar(255),
    person_name varchar(255));

create table if not exists phones (
    person_id bigint not null,
    phone_number_type varchar(255) not null,
    phone_number varchar(255),
    primary key (person_id, phone_number_type),
    foreign key (person_id) references persons (id));

create table if not exists addresses (
    person_id bigint not null,
    address_type varchar(255) not null,
    address varchar(255),
    primary key (person_id, address_type),
    foreign key (person_id) references persons (id));

create table if not exists emails (
    person_id bigint not null,
    email varchar(255),
    foreign key (person_id) references persons (id));
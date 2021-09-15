create table if not exists recorder (
    id bigint not null auto_increment primary key,
    date_of_birth date,
    recorder_name varchar(255));
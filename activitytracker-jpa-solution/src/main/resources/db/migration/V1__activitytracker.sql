create table activities(
    id         bigint auto_increment primary key,
    start_time datetime,
    act_description varchar(50),
    act_type   varchar(20));
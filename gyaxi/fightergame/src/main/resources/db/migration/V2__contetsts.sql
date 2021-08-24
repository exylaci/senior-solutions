create table if not exists contests(
    id bigint not null auto_increment primary key,
    fighter_name_a varchar(255),
    fighter_name_b varchar(255),
    result varchar(5));


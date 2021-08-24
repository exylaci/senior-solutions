create table if not exists fighters (
    id bigint not null auto_increment primary key,
    fighter_name varchar(255) unique,
    vitality INTEGER,
    damage INTEGER,
    win INTEGER,
    draw INTEGER,
    lose INTEGER);
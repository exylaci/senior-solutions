create table if not exists teams(
    id bigint not null auto_increment primary key,
    team_name varchar(255) not null);

create table if not exists players(
    id bigint not null auto_increment primary key,
    player_name varchar(100) not null,
    birth_date DATE,
    player_position varchar(25),
    team_id bigint,
    foreign key (team_id) references teams(id));
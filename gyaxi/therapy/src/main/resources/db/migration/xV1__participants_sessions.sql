create table if not exists sessions (
    id bigint not null auto_increment primary key,
    therapist varchar(100),
    location varchar(100),
    starts_at datetime);

create table if not exists participants (
    id bigint not null auto_increment primary key,
    participant_name varchar(100));

create table if not exists participants_sessions (
    id bigint not null auto_increment primary key,
    session_id bigint,
    participant_id bigint,
    foreign key (session_id) references sessions(id),
    foreign key (participant_id) references participants(id));
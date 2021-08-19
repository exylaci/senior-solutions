create table if not exists sessions (
    id bigint not null auto_increment primary key,
    therapist varchar(100),
    location varchar(100),
    starts_at datetime);

create table if not exists participants (
    id bigint not null auto_increment primary key,
    participant_name varchar(100),
    session_id bigint,
    foreign key (session_id) references sessions(id));
ALTER TABLE participants
    DROP INDEX session_id,
    DROP FOREIGN KEY participants_ibfk_1,
    DROP column session_id;

create table if not exists sessions_participants (
    session_id bigint,
    participant_id bigint,
    primary key (participant_id, session_id),
    foreign key (participant_id) references participants(id),
    foreign key (session_id) references sessions(id));
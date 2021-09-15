create table if not exists world_record (
    id bigint not null auto_increment primary key,
    date_of_record date,
    description_of_worldrecord varchar(255),
    unit_of_measure varchar(255),
    value_of_worldrecord double,
    recorder_id bigint,
    foreign key (recorder_id) references recorder (id));
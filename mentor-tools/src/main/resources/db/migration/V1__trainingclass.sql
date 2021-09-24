-- USE mentortools;

CREATE TABLE IF NOT EXISTS training_classes(
    id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title varchar(255) not null,
    begin_date date,
    end_date date);
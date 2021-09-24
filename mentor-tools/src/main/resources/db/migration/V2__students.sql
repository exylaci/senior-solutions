CREATE TABLE IF NOT EXISTS students(
    id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_name varchar(255) not null,
    email_address varchar(255) not null,
    github_username varchar(255),
    comment text);
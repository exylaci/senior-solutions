create table if not exists accounts (
    id bigint not null auto_increment primary key,
    amount decimal(19,0),
    customer_name varchar(255),
    account_number varchar(8),
    opened date);
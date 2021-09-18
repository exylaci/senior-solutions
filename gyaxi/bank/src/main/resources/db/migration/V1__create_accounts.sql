create table if not exists accounts (
    id bigint not null auto_increment primary key,
    customer_name varchar(255),
    account_number varchar(8),
    balance decimal(19,0),
    deleted_account boolean,
    open_date date);
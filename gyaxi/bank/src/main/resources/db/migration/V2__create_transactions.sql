create table if not exists transactions (
    id bigint not null auto_increment primary key,
    account_number_target varchar(8),
    amount decimal(19,0),
    new_balamce decimal(19,0),
    transaction_timestamp timestamp,
    transaction_type varchar(10),
    account_source_id bigint,
    foreign key (account_source_id) references accounts (id));
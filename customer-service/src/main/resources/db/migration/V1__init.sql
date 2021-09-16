create sequence customerId_seq start 1;
create sequence orderId_seq start 1;

create table customer(
    id int,
    name varchar(40) not null,
    surname varchar(40) not null,
    primary key (id)
);

create table order_customer(
    id int,
    item varchar(40) not null,
    price numeric(15,4) not null,
    customer_id int not null,
    primary key (id),
    foreign key (customer_id) references customer (id)
);
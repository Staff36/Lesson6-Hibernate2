drop table  if exists products_and_customers.customers_products;
drop table  if exists products_and_customers.products;
drop table  if exists products_and_customers.customers;
drop schema if exists products_and_customers;

create schema products_and_customers;

create table products_and_customers.customers
(
    id   serial not null
        constraint customers_pk
            primary key,
    name varchar(255)
);

create unique index customers_name_uindex
    on products_and_customers.customers (name);


create table products_and_customers.products
(
    id   serial
        constraint products_pk
            primary key,
    name varchar(255) not null,
    cost double precision
);

create unique index products_name_uindex
    on products_and_customers.products (name);


-- Можно было бы исеользовать составной ключ, что избавило бы нас от задвоения, однако вдруг заказов было много?--
create table products_and_customers.customers_products
(
    products_id  integer not null
        constraint products_fk
            references products_and_customers.products,
    customers_id integer not null
        constraint customers_fk
            references products_and_customers.customers,
    cost         double precision,
    constraint customers_products_pk
        primary key (products_id, customers_id)
);

insert into products_and_customers.customers (name) values ('Dmitry');
insert into products_and_customers.customers (name) values ('Anton');
insert into products_and_customers.customers (name) values ('Igor');

insert into products_and_customers.products(name, cost) values ('Apple', 1.23);
insert into products_and_customers.products(name, cost) values ('Orange', 2.17);
insert into products_and_customers.products(name, cost) values ('Cherry', 3.32);

insert into products_and_customers.customers_products( customers_id, products_id, cost) values (1,1,22);
insert into products_and_customers.customers_products(customers_id, products_id, cost) values (1,3,56);
insert into products_and_customers.customers_products(customers_id, products_id, cost) values (2,1,62);
insert into products_and_customers.customers_products(customers_id, products_id, cost) values (2,2,100);
insert into products_and_customers.customers_products(customers_id, products_id, cost) values (3,1,110);
insert into products_and_customers.customers_products(customers_id, products_id, cost) values (3,2,310);
insert into products_and_customers.customers_products(customers_id, products_id, cost) values (3,3,1010.65);

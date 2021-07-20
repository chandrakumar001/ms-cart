-- CREATE DATABASE IF NOT EXISTS
--drop schema cart cascade;
create schema if not exists cart;

-- CREATE sequence for address,cart and cart_entry
create sequence cart.address_seq start with 1 increment by 1;
create sequence cart.cart_entry_seq start with 1 increment by 1;
create sequence cart.cart_seq start with 1 increment by 1;

-- CREATE table for address,cart and cart_entry
create table cart.address (id bigint not null, country varchar(255), name varchar(255), postal_code varchar(255), street_name varchar(255), street_number varchar(255));
create table cart.cart (id bigint not null, code varchar(255), discounts decimal(19,2), subtotal decimal(19,2), total decimal(19,2), total_tax decimal(19,2), address_billing_id bigint, address_shipping_id bigint);
create table cart.cart_entry (id bigint not null, code varchar(255), quantity bigint, total decimal(19,2), cart_id bigint);

-- ALTER table for address,cart and cart_entry for primary key
alter table cart.address add constraint cart_address_pkey primary key (id);
alter table cart.cart add constraint cart_cart_pkey primary key (id);
alter table cart.cart_entry add constraint cart_cart_entry_pkey primary key (id);

-- ALTER table for address,cart and cart_entry for foreign key
alter table cart.cart add constraint cart_address_billing_fk foreign key (address_billing_id) references cart.address;
alter table cart.cart add constraint cart_address_shipping_fk  foreign key (address_shipping_id) references cart.address;
alter table cart.cart_entry add constraint cart_cart_entry_fk foreign key (cart_id) references cart.cart;
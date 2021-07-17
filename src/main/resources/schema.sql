create SCHEMA cart;
create sequence hibernate_sequence increment 1 start 1 minvalue 1;
--create sequence cart.hibernate_sequence increment 1 start 1 minvalue 1;
create table cart.address (id bigint not null, country varchar(255), name varchar(255), postal_code varchar(255), street_name varchar(255), street_number varchar(255), primary key (id));
create table cart.cart (id bigint not null, code varchar(255), discounts decimal(19,2), subtotal decimal(19,2), total decimal(19,2), total_tax decimal(19,2), address_billing_id bigint, address_shipping_id bigint, primary key (id));
create table cart.cart_entry (id bigint not null, code varchar(255), quantity bigint, total decimal(19,2), cart_id bigint, primary key (id));

alter table cart.cart add constraint cart_address_billing_id foreign key (address_billing_id) references cart.address;
alter table cart.cart add constraint cart_address_shipping_id foreign key (address_shipping_id) references cart.address;
alter table cart.cart_entry add constraint cart_entry_cart_id foreign key (cart_id) references cart.cart;


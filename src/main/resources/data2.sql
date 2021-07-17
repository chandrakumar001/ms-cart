insert into cart.address (country, name, postal_code, street_name, street_number, id)
values ('Germany', 'Iron Man', '11111', 'Avenger street', '11', 1)
,('Germany', 'Super Man', '11111', 'DC street', '11', 2)
,('Germany', 'chandrakumar', '11111', 'WC street', '11', 3)
;

insert into cart.cart (address_billing_id, address_shipping_id,code, discounts,  subtotal, total, total_tax, id)
values (1, 2, 'Cart-eea378b5-5694-4df1-96e6-56409cda9aea', 0, 8, 50, 50, 1)
,(1, 2, 'Cart-eea378b5-5694-4df1-96e6-56409cda9ae3', 0, 8, 50, 50, 2)
,(3, 3, 'Cart-eea378b5-5694-4df1-96e6-56409cda9aeb', 0, 2, 80, 10, 3)
;

insert into cart.cart_entry (cart_id, code, quantity, total, id)
values (1, '12345_1_1', 10, 25, 1)
,(1, '12345_1_2', 2, 21, 2)
,(2, '12345_1_2', 1, 2, 3)
;

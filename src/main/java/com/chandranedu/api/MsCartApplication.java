package com.chandranedu.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCartApplication.class, args);
    }

//    @Bean
//    ApplicationRunner init(CartService cartService,
//                           AddressService addressService,
//                           CartEntryService cartEntryService) {
//
//        CartDTO cart1 = new CartDTO();
//        cart1.setCode("cart-1234");
//        cart1.setTotal(new BigDecimal(50));
//        cart1.setSubtotal(new BigDecimal(48));
//        cart1.setTotalTax(new BigDecimal(8));
//
//
//        AddressDTO address1 = new AddressDTO();
//        address1.setCountry("Germany");
//        address1.setName("Iron Man");
//        address1.setPostalCode("11111");
//        address1.setStreetName("Avenger street");
//        address1.setStreetNumber("11");
//
//
//        cart1.setBillingAddress(address1);
//        cart1.setShippingAddress(address1);
//
//        CartEntryDTO entry1 = new CartEntryDTO();
//        entry1.setCartEntryCode("12345_1_1");
//        entry1.setQuantity(10L);
//        entry1.setTotal(BigDecimal.valueOf(25));
//
//        CartEntryDTO entry2 = new CartEntryDTO();
//        entry2.setCartEntryCode("12345_1_2");
//        entry2.setQuantity(1L);
//        entry2.setTotal(BigDecimal.valueOf(23));
//
//
//        CartDTO cart2 = new CartDTO();
//        cart2.setCode("cart-12344");
//        cart2.setTotal(new BigDecimal(25));
//        cart2.setSubtotal(new BigDecimal(25));
//        cart2.setTotalTax(new BigDecimal(5));
//
//
//        AddressDTO address2 = new AddressDTO();
//        address2.setCountry("Germany");
//        address2.setName("Super Man");
//        address2.setPostalCode("11111");
//        address2.setStreetName("DC street");
//        address2.setStreetNumber("1111");
//
//
//        cart2.setBillingAddress(address2);
//        cart2.setShippingAddress(address2);
//
//        CartEntryDTO entry3 = new CartEntryDTO();
//        entry3.setCartEntryCode("99999_1_1");
//        entry3.setQuantity(10L);
//        entry3.setTotal(BigDecimal.valueOf(5));
//
//        CartEntryDTO entry4 = new CartEntryDTO();
//        entry4.setCartEntryCode("88888_1_2");
//        entry4.setQuantity(1L);
//        entry4.setTotal(BigDecimal.valueOf(20));
//
//
//        addressService.saveAddress(address1);
//        addressService.saveAddress(address2);
//
//        cartEntryService.saveCartEntry(entry1);
//        cartEntryService.saveCartEntry(entry2);
//        cartEntryService.saveCartEntry(entry3);
//        cartEntryService.saveCartEntry(entry4);
//
//
//        cart1.setEntriesList(Arrays.asList(entry1, entry2));
//        cart2.setEntriesList(Arrays.asList(entry3, entry4));
//
//        cartService.saveCart(cart1);
//        cartService.saveCart(cart2);
//
//
//        entry1.setCart(cart1);
//        entry2.setCart(cart1);
//        entry3.setCart(cart2);
//        entry4.setCart(cart2);
//
//
//        cartEntryService.saveCartEntry(entry1);
//        cartEntryService.saveCartEntry(entry2);
//        cartEntryService.saveCartEntry(entry3);
//        cartEntryService.saveCartEntry(entry4);
//
//        return args -> cartService.getCarts().forEach(System.out::println);
//    }
}

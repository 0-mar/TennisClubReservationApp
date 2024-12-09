package cz.omar.tennisclubreservationapp.customer.business;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String name;
    private String phoneNumber;
}

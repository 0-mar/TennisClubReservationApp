package cz.omar.tennisclubreservationapp.customer.storage;

import cz.omar.tennisclubreservationapp.customer.business.Customer;

public interface CustomerRepository {

    Customer create(CustomerEntity customerEntity);

    Customer get(Long id);

    Customer getByPhoneNumber(String phoneNumber);
}

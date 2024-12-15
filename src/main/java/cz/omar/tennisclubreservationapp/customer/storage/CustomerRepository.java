package cz.omar.tennisclubreservationapp.customer.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.customer.mapper.CustomerToDatabaseMapper;
import org.springframework.stereotype.Repository;

public interface CustomerRepository {

    Customer create(CustomerEntity customerEntity);

    Customer get(Long id);

    Customer getByPhoneNumber(String phoneNumber);
}

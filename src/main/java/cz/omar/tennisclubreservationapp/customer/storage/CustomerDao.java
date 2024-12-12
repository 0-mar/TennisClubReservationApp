package cz.omar.tennisclubreservationapp.customer.storage;

import java.util.List;

public interface CustomerDao {
    CustomerEntity create(CustomerEntity customerEntity);
    CustomerEntity get(Long id);
    List<CustomerEntity> getAll();
    CustomerEntity delete(Long id);
    CustomerEntity getByPhoneNumber(String phoneNumber);
}

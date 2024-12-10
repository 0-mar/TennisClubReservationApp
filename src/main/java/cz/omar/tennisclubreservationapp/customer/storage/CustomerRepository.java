package cz.omar.tennisclubreservationapp.customer.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.customer.mapper.CustomerToDatabaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerRepository {
    private final CustomerDao customerDao;
    private final CustomerToDatabaseMapper customerToDatabaseMapper;

    public CustomerRepository(CustomerDao customerDao, CustomerToDatabaseMapper customerToDatabaseMapper) {
        this.customerDao = customerDao;
        this.customerToDatabaseMapper = customerToDatabaseMapper;
    }

    public Customer create(CustomerEntity customerEntity) {
        return customerToDatabaseMapper.entityToCustomer(customerDao.create(customerEntity));
    }

    public Customer get(Long id) {
        CustomerEntity customerEntity = customerDao.get(id);
        if (customerEntity == null) {
            throw new RepositoryException("Customer " + id + " not found");
        }
        return customerToDatabaseMapper.entityToCustomer(customerEntity);
    }
}

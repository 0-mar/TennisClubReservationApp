package cz.omar.tennisclubreservationapp.customer.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.customer.mapper.CustomerToDatabaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerDao customerDao;
    private final CustomerToDatabaseMapper customerToDatabaseMapper;

    public CustomerRepositoryImpl(CustomerDao customerDao, CustomerToDatabaseMapper customerToDatabaseMapper) {
        this.customerDao = customerDao;
        this.customerToDatabaseMapper = customerToDatabaseMapper;
    }

    @Override
    public Customer create(CustomerEntity customerEntity) {
        if (customerDao.getByPhoneNumber(customerEntity.getPhoneNumber()) != null) {
            throw new RepositoryException("Phone number already in use");
        }

        return customerToDatabaseMapper.entityToCustomer(customerDao.create(customerEntity));
    }

    @Override
    public Customer get(Long id) {
        CustomerEntity customerEntity = customerDao.get(id);
        if (customerEntity == null) {
            throw new RepositoryException("Customer " + id + " not found");
        }
        return customerToDatabaseMapper.entityToCustomer(customerEntity);
    }

    @Override
    public Customer getByPhoneNumber(String phoneNumber) {
        CustomerEntity customerEntity = customerDao.getByPhoneNumber(phoneNumber);
        return customerToDatabaseMapper.entityToCustomer(customerEntity);
    }
}

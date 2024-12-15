package cz.omar.tennisclubreservationapp.repository;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.customer.mapper.CustomerToDatabaseMapper;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerDao;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerEntity;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerRepository;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@TestPropertySource(properties = "seed-data=false")
@SpringBootTest
public class CustomerRepositoryTests {
    CustomerRepository customerRepository;

    @Autowired
    CustomerToDatabaseMapper customerToDatabaseMapper;

    @MockBean
    CustomerDao customerDao;

    CustomerEntity customerEntity;
    Customer customer;

    @BeforeEach
    void setUp() {
        customerRepository = new CustomerRepositoryImpl(customerDao, customerToDatabaseMapper);

        customer = new Customer();
        customer.setId(1L);
        customer.setPhoneNumber("+420645789123");
        customer.setFirstName("John");
        customer.setLastName("Smith");

        customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setPhoneNumber("+420645789123");
        customerEntity.setFirstName("John");
        customerEntity.setLastName("Smith");
    }

    @Test
    void createCustomerTest() {
        CustomerEntity ce = new CustomerEntity();
        ce.setPhoneNumber("+420645789123");
        ce.setFirstName("John");
        ce.setLastName("Smith");

        doReturn(customerEntity).when(customerDao).create(ce);

        Customer created = customerRepository.create(ce);
        doReturn(customerEntity).when(customerDao).getByPhoneNumber(ce.getPhoneNumber());
        assertThat(created).isEqualTo(customerToDatabaseMapper.entityToCustomer(customerEntity));

        CustomerEntity ce2 = new CustomerEntity();
        ce2.setPhoneNumber("+420645789123");
        ce2.setFirstName("Pepek");
        ce2.setLastName("Novak");
        assertThrows(RepositoryException.class, () -> {
            customerRepository.create(ce2);
        });
    }

    @Test
    void getCustomerTest() {
        doReturn(customerEntity).when(customerDao).get(1L);

        Customer retrieved = customerRepository.get(1L);
        assertThat(retrieved).isEqualTo(customerToDatabaseMapper.entityToCustomer(customerEntity));
    }

    @Test
    void getNonexistentCustomerTest() {
        doReturn(null).when(customerDao).get(1L);

        assertThrows(RepositoryException.class, () -> {
            customerRepository.get(1L);
        });
    }

    @Test
    void getByPhoneNumberTest() {
        doReturn(customerEntity).when(customerDao).getByPhoneNumber(customerEntity.getPhoneNumber());

        Customer retrieved = customerRepository.getByPhoneNumber(customerEntity.getPhoneNumber());
        assertThat(retrieved).isEqualTo(customerToDatabaseMapper.entityToCustomer(customerEntity));
    }
}

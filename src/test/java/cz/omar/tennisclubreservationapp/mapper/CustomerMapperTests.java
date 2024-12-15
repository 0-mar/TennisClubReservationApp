package cz.omar.tennisclubreservationapp.mapper;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.customer.mapper.CustomerToDatabaseMapper;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "seed-data=false")
@ContextConfiguration(classes = {AppConfig.class})
public class CustomerMapperTests {
    @Autowired
    CustomerToDatabaseMapper customerToDatabaseMapper;

    Customer customer;
    CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
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
    void customerEntityToCustomerTest() {
        assertThat(customerToDatabaseMapper.customerToEntity(customer)).isEqualTo(customerEntity);
    }

    @Test
    void customerToCustomerEntityTest() {
        assertThat(customerToDatabaseMapper.entityToCustomer(customerEntity)).isEqualTo(customer);
    }
}

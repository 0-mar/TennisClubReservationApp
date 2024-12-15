package cz.omar.tennisclubreservationapp.dao;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerDao;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "seed-data=false")
@ContextConfiguration(classes = {AppConfig.class})
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testCreateCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setPhoneNumber("+420645789123");
        customer.setFirstName("John");
        customer.setLastName("Smith");

        CustomerEntity savedCustomer = customerDao.create(customer);
        assertThat(entityManager.find(CustomerEntity.class, savedCustomer.getId()) ).isEqualTo(savedCustomer);

    }

    @Test
    void testGet() {
        CustomerEntity customer = new CustomerEntity();
        customer.setPhoneNumber("+420645789123");
        customer.setFirstName("John");
        customer.setLastName("Smith");

        CustomerEntity savedCustomer = entityManager.persist(customer);

        CustomerEntity foundCustomer = customerDao.get(savedCustomer.getId());

        assertThat(savedCustomer).isEqualTo(foundCustomer);
    }

    @Test
    void testGetAll() {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setPhoneNumber("+420645789123");
        customer1.setFirstName("John");
        customer1.setLastName("Smith");

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setPhoneNumber("+420645789333");
        customer2.setFirstName("Joe");
        customer2.setLastName("Smith");

        CustomerEntity savedCustomer1 = entityManager.persist(customer1);
        CustomerEntity savedCustomer2 = entityManager.persist(customer2);

        List<CustomerEntity> customers = customerDao.getAll();

        assertThat(customers).hasSize(2);
        assertThat(customers).containsExactlyInAnyOrder(savedCustomer1, savedCustomer2);
    }

    @Test
    void testDelete() {
        CustomerEntity customer = new CustomerEntity();
        customer.setPhoneNumber("+420645789123");
        customer.setFirstName("John");
        customer.setLastName("Smith");

        CustomerEntity savedCustomer = entityManager.persist(customer);

        CustomerEntity deletedCustomer = customerDao.delete(savedCustomer.getId());
        CustomerEntity foundCustomer = entityManager.find(CustomerEntity.class, savedCustomer.getId());

        assertThat(deletedCustomer).isEqualTo(foundCustomer);
        assertThat(deletedCustomer.isDeleted()).isTrue();
    }

    @Test
    void testGetByPhoneNumber() {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setPhoneNumber("+420645789123");
        customer1.setFirstName("John");
        customer1.setLastName("Smith");

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setPhoneNumber("+420645789333");
        customer2.setFirstName("Joe");
        customer2.setLastName("Smith");

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setPhoneNumber("+420645789666");
        customer3.setFirstName("Pepa");
        customer3.setLastName("Novak");

        entityManager.persist(customer1);
        entityManager.persist(customer2);
        CustomerEntity savedCustomer3 = entityManager.persist(customer3);

        var foundCustomer = customerDao.getByPhoneNumber(savedCustomer3.getPhoneNumber());
        assertThat(foundCustomer).isEqualTo(savedCustomer3);
        assertThat(customerDao.getByPhoneNumber("+6666666")).isEqualTo(null);
    }
}

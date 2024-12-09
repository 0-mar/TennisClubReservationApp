package cz.omar.tennisclubreservationapp.customer.storage;

import cz.omar.tennisclubreservationapp.common.storage.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDaoImpl extends AbstractDao<CustomerEntity> implements CustomerDao {

    public CustomerDaoImpl() {
        setClazz(CustomerEntity.class);
    }

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {
        return save(customerEntity);
    }

    @Override
    public CustomerEntity get(Long id) {
        return findById(id);
    }

    @Override
    public List<CustomerEntity> getAll() {
        return findAll();
    }

    @Override
    public CustomerEntity delete(Long id) {
        return remove(id);
    }


//    @PersistenceContext
//    protected EntityManager entityManager;
//
//    private final Class<Customer> customerClass;
//
//    public CustomerDaoImpl() {
//        customerClass = Customer.class;
//    }
//
//    @Transactional
//    @Override
//    public void create(CreateCustomerDto customer) {
//        Customer cust = new Customer();
//        cust.setName(customer.name());
//        cust.setPhoneNumber(customer.phoneNumber());
//        entityManager.persist(cust);
//    }
//
//    @Transactional
//    @Override
//    public Customer get(Long id) {
//        return entityManager.createQuery(
//                        "SELECT e FROM " + customerClass.getSimpleName() + " e WHERE e.id = :id AND e.deleted = false", customerClass)
//                .setParameter("id", id)
//                .getResultStream()
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Transactional
//    @Override
//    public List<Customer> getAll() {
//        return entityManager.createQuery(
//                        "SELECT e FROM " + customerClass.getSimpleName() + " e WHERE e.deleted = false", customerClass)
//                .getResultList();
//    }
//
//    @Transactional
//    @Override
//    public Customer delete(Long id) {
//        var customer = get(id);
//        if (customer == null) {
//            return null;
//        }
//
//        customer.setDeleted(true);
//        entityManager.merge(customer);
//
//        return customer;
//    }
}

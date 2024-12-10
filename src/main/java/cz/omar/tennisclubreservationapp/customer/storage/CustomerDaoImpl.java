package cz.omar.tennisclubreservationapp.customer.storage;

import cz.omar.tennisclubreservationapp.common.storage.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDaoImpl extends AbstractDao<CustomerEntity> implements CustomerDao {

    public CustomerDaoImpl() {
        super(CustomerEntity.class);
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

}

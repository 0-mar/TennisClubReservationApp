package cz.omar.tennisclubreservationapp.customer.storage;

import cz.omar.tennisclubreservationapp.common.storage.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity extends BaseEntity {

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "phoneNumber", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}

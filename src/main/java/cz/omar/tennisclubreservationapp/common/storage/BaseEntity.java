package cz.omar.tennisclubreservationapp.common.storage;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Data;

/**
 * BaseEntity serves as the abstract base class for all database entity classes in the application.
 *
 * The `BaseEntity` class is intended to be extended by all other database entities in the system.
 *
 * Fields:
 * - `id`: Represents the unique identifier of the entity. It is generated automatically
 *   using the defined strategy.
 * - `deleted`: Denotes logical deletion state, allowing entities to be marked as deleted
 *   without physically removing them from the database.
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "deleted", nullable = false)
    protected boolean deleted;
}

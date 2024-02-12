package ru.on8off.crud.backend.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "kv_objects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KVObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date_created", nullable = false)
    protected ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(name = "date_updated", nullable = false)
    protected ZonedDateTime dateUpdated = ZonedDateTime.now();

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String value;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KVObject obj = (KVObject) o;
        return Objects.equals(id, obj.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

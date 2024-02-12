package ru.on8off.crud.backend.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ru.on8off.crud.backend.repository.entity.KVObject;

public interface KVObjectRepository extends CrudRepository<KVObject, Integer>, JpaSpecificationExecutor<KVObject> {

}

package ru.on8off.crud.backend.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ru.on8off.crud.backend.repository.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

}

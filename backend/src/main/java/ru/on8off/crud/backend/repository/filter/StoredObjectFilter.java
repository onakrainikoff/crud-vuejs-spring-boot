package ru.on8off.crud.backend.repository.filter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.on8off.crud.backend.repository.entity.StoredObject;

public class StoredObjectFilter implements Specification<StoredObject>{
    private String name;
    private String value;
    private ZonedDateTime dateCreatedFrom;
    private ZonedDateTime dateCreatedTo;
    private ZonedDateTime dateUpdatedFrom;
    private ZonedDateTime dateUpdatedTo;

    public StoredObjectFilter(String name, String value, ZonedDateTime dateCreatedFrom, ZonedDateTime dateCreatedTo,
            ZonedDateTime dateUpdatedFrom, ZonedDateTime dateUpdatedTo) {
        this.name = name;
        this.value = value;
        this.dateCreatedFrom = dateCreatedFrom;
        this.dateCreatedTo = dateCreatedTo;
        this.dateUpdatedFrom = dateUpdatedFrom;
        this.dateUpdatedTo = dateUpdatedTo;
    }

    @Override
    public Predicate toPredicate(Root<StoredObject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(name!=null && !name.isEmpty()) {
            predicates.add(
                cb.like(
                    root.<String>get("name"),
                    (name.contains("%") || name.contains("_")) ? name: "%" + name + "%"
                )
            );   
        }
        if(value!=null && !value.isEmpty()){
            predicates.add(
                cb.like(
                    root.<String>get("value"),
                    (value.contains("%") || value.contains("_")) ? value :  "%" + value + "%"
                )
            );
        }

        if(dateCreatedFrom!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.<ZonedDateTime>get("dateCreated"), dateCreatedFrom));
        }
        if(dateCreatedTo!=null){
            predicates.add(cb.lessThanOrEqualTo(root.<ZonedDateTime>get("dateCreated"), dateCreatedFrom));
        }

        if(dateUpdatedFrom!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.<ZonedDateTime>get("dateUpdated"), dateUpdatedFrom));
        }
        if(dateUpdatedTo!=null){
            predicates.add(cb.lessThanOrEqualTo(root.<ZonedDateTime>get("dateUpdated"), dateUpdatedTo));
        }
        
        return predicates.stream().reduce(cb::and).orElse(null);
    }
}

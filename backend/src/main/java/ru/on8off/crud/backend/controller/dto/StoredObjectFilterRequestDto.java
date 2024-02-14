package ru.on8off.crud.backend.controller.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.filter.StoredObjectFilter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoredObjectFilterRequestDto {
    private String name;
    private String value;
    private ZonedDateTime dateCreatedFrom;
    private ZonedDateTime dateCreatedTo;
    private ZonedDateTime dateUpdatedFrom;
    private ZonedDateTime dateUpdatedTo;
    
    public StoredObjectFilter toStoredObjectFilter(){
        return new StoredObjectFilter(name, value, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo);
    }
}
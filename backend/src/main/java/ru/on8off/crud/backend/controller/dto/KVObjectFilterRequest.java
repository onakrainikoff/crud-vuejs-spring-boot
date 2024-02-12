package ru.on8off.crud.backend.controller.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.filter.KVObjectFilter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KVObjectFilterRequest {
    private String code;
    private String name;
    private ZonedDateTime dateCreatedFrom;
    private ZonedDateTime dateCreatedTo;
    private ZonedDateTime dateUpdatedFrom;
    private ZonedDateTime dateUpdatedTo;
    
    public KVObjectFilter getFilter(){
        return new KVObjectFilter(code, name, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo);
    }
}
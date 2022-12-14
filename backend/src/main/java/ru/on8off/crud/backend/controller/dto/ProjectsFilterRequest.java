package ru.on8off.crud.backend.controller.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.filter.ProjectsFilter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectsFilterRequest {
    private String code;
    private String name;
    private ZonedDateTime dateCreatedFrom;
    private ZonedDateTime dateCreatedTo;
    private ZonedDateTime dateUpdatedFrom;
    private ZonedDateTime dateUpdatedTo;
    
    public ProjectsFilter getFilter(){
        return new ProjectsFilter(code, name, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo);
    }
}
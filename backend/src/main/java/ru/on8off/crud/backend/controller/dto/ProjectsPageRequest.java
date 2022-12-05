package ru.on8off.crud.backend.controller.dto;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.filter.ProjectsFilter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectsPageRequest {
    private Integer pageSize;
    private Integer pageNumber;
    private String sortBy;
    private String sortOrder;
    private String code;
    private String name;
    private ZonedDateTime dateCreatedFrom;
    private ZonedDateTime dateCreatedTo;
    private ZonedDateTime dateUpdatedFrom;
    private ZonedDateTime dateUpdatedTo;

    private static Integer PAGE_SIZE = 50;

    
    public PageRequest getPageRequest(){
        pageNumber = Objects.requireNonNullElse(pageNumber, 0);
        pageSize= Objects.requireNonNullElse(pageSize, PAGE_SIZE);
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        
        if(sortBy != null && !sortBy.isEmpty()){
            var direction = "DESC".equals(sortOrder) ? Direction.DESC : Direction.ASC;
            pageRequest = pageRequest.withSort(direction, sortBy);
        }
        return pageRequest;
    }
        
    public ProjectsFilter getFilter(){
        return new ProjectsFilter(code, name, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo);
    }

}
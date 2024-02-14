package ru.on8off.crud.backend.controller.dto;

import java.util.Objects;
import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageRequestDto {
    private Integer pageSize;
    private Integer pageNumber;
    private String sortBy;
    private boolean sortOrderDesc = false;

    private static Integer PAGE_SIZE = 50;

    public org.springframework.data.domain.PageRequest toPageRequest(){
        pageNumber = Objects.requireNonNullElse(pageNumber, 0);
        pageSize= Objects.requireNonNullElse(pageSize, PAGE_SIZE);
        var pageRequest = org.springframework.data.domain.PageRequest.of(pageNumber, pageSize);
        if(sortBy != null && !sortBy.isEmpty()){
            var direction = sortOrderDesc ? Direction.DESC : Direction.ASC;
            pageRequest = pageRequest.withSort(direction, sortBy);
        }
        return pageRequest;
    }
}

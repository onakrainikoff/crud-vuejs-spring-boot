package ru.on8off.crud.backend.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageDto<T> {
    private List<T> content;
    private int pageSize;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
}

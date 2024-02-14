package ru.on8off.crud.backend.controller.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

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


    public static <T,E> PageDto<T> fromPage(Page<E> page, Function<E, T> mapper) {
        return new PageDto<>(
            page.get().map(mapper).collect(Collectors.toList()),
            page.getSize(),
            page.getNumber(),
            page.getTotalPages(),
            page.getTotalElements()
        );
    }
    
}

package com.example.wanted_6.module.common.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long totalElements;
    private Integer totalPages;
    private List<? extends T> data;
    private Integer size;
    private Integer number;
    private boolean first;
    private boolean last;
    private boolean empty;

    public static PageResult of(Page<?> page) {
        PageResult result = new PageResult();
        result.setTotalElements(page.getTotalElements());
        result.setTotalPages(page.getTotalPages());
        result.setSize(page.getSize());
        result.setNumber(page.getNumber());
        result.setFirst(page.isFirst());
        result.setLast(page.isLast());
        result.setEmpty(page.isEmpty());
        result.setData(page.getContent());
        return result;
    }
}

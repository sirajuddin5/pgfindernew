package com.thryve.pgfinder.model.common.page;

import java.util.Objects;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
	
	private Integer pageNumber = 0;
    private Integer pageSize = 10;
    private Sort.Direction sort;
    private String sortByColumn;
    
    
    public Pageable getPageable(PageRequestDTO pageRequestDTO) {
        Integer pageNumber = Objects.nonNull(pageRequestDTO.getPageNumber()) ? pageRequestDTO.getPageNumber() : this.pageNumber;
        Integer pageSize = Objects.nonNull(pageRequestDTO.getPageSize()) ? pageRequestDTO.getPageSize() : this.pageSize;
        Sort.Direction sort = Objects.nonNull(pageRequestDTO.getSort()) ? pageRequestDTO.getSort() : this.sort;
        String sortByColumn = Objects.nonNull(pageRequestDTO.getSortByColumn()) ? pageRequestDTO.getSortByColumn() : this.sortByColumn;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort, new String[]{sortByColumn});
        return pageRequest;
    }

}

package com.thryve.pgfinder.model.common;

import java.util.List;
import com.thryve.pgfinder.model.common.filter.Filter;
import com.thryve.pgfinder.model.common.page.PageRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchAPIRequest {
		private List<Filter> filterList;
	    private GlobalOperator globalOperator;
	    private PageRequestDTO pageRequestDTO;

}

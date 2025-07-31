package com.thryve.pgfinder.model.common.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {

	private String key;
	private String value;
	private Operation operation;
	private String joinObject;
	private JoinOperation joinOperation;
	
	
	 public static enum Operation {
        EQUAL,
        DATE_EQUAL,
        DATE_BETWEEN,
        NOT_EQUAL,
        LIKE,
        IN,
        GREATER_THAN,
        LESS_THAN,
        BETWEEN,
        JOIN;

        private Operation() {
        }
    }
	 
	 public static enum JoinOperation {
        EQUAL,
        DATE_EQUAL,
        LIKE,
        BETWEEN,
        DATE_GREATER_THAN_EQUAL_TO,
        DATE_LESS_THAN,
        DATE_BETWEEN;

        private JoinOperation() {
        }
    }
}

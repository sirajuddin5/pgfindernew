//package com.thryve.pgfinder.model.common.filter.specification;
//
//import org.springframework.stereotype.Service;
//
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import org.springframework.data.jpa.domain.Specification;
//
//import com.thryve.pgfinder.model.common.GlobalOperator;
//import com.thryve.pgfinder.model.common.filter.Filter;
//
//@Service
//public class FiltersSpecification<T> {
//	
//	public Specification<T> getSearchSpecification(final Filter filter) {
//        return new Specification<T>() {
//            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get(filter.getKey()), filter.getValue());
//            }
//        };
//    }
//	
//	
//	
//	   public Specification<T> getSearchSpecification(List<Filter> filterList, GlobalOperator globalOperator) {
//	        return (root, query, criteriaBuilder) -> {
//	            List<Predicate> predicateList = new ArrayList();
//
//	            for(Filter filter : filterList) {
//	                switch (filter.getOperation()) {
//	                    case EQUAL:
//	                        Predicate predicate = criteriaBuilder.equal(root.get(filter.getKey()), filter.getValue());
//	                        predicateList.add(predicate);
//	                        break;
//	                    case DATE_EQUAL:
//	                        try {
//	                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//	                            Date date = simpleDateFormat.parse(filter.getValue());
//	                            Predicate predicate = criteriaBuilder.equal(root.get(filter.getKey()), date);
//	                            predicateList.add(predicate);
//	                        } catch (Exception e) {
//	                            e.printStackTrace();
//	                        }
//	                        break;
//	                    case DATE_BETWEEN:
//	                        try {
//	                            String[] split = filter.getValue().split(",");
//	                            String startDateStr = split[0];
//	                            String endDateStr = split[1];
//	                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//	                            Date startDate = simpleDateFormat.parse(startDateStr);
//	                            Date endDate = simpleDateFormat.parse(endDateStr);
//	                            Predicate predicate = criteriaBuilder.between(root.get(filter.getKey()), startDate, endDate);
//	                            predicateList.add(predicate);
//	                        } catch (Exception e) {
//	                            e.printStackTrace();
//	                        }
//	                        break;
//	                    case NOT_EQUAL:
//	                        Predicate predicate = criteriaBuilder.notEqual(root.get(filter.getKey()), filter.getValue());
//	                        predicateList.add(predicate);
//	                        break;
//	                    case LIKE:
//	                        Predicate predicate = criteriaBuilder.like(root.get(filter.getKey()), "%" + filter.getValue() + "%");
//	                        predicateList.add(predicate);
//	                        break;
//	                    case IN:
//	                        String[] split = filter.getValue().split(",");
//	                        Predicate predicate = root.get(filter.getKey()).in(Arrays.asList(split));
//	                        predicateList.add(predicate);
//	                        break;
//	                    case GREATER_THAN:
//	                        Predicate predicate = criteriaBuilder.greaterThan(root.get(filter.getKey()), filter.getValue());
//	                        predicateList.add(predicate);
//	                        break;
//	                    case LESS_THAN:
//	                        Predicate predicate = criteriaBuilder.lessThan(root.get(filter.getKey()), filter.getValue());
//	                        predicateList.add(predicate);
//	                        break;
//	                    case BETWEEN:
//	                        String[] split = filter.getValue().split(",");
//	                        Predicate predicate = criteriaBuilder.between(root.get(filter.getKey()), Long.parseLong(split[0]), Long.parseLong(split[1]));
//	                        predicateList.add(predicate);
//	                        break;
//	                    case JOIN:
//	                        switch (filter.getJoinOperation()) {
//	                            case EQUAL:
//	                                Predicate predicate = criteriaBuilder.equal(root.join(filter.getJoinObject()).get(filter.getKey()), filter.getValue());
//	                                predicateList.add(predicate);
//	                                continue;
//	                            case DATE_EQUAL:
//	                                try {
//	                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//	                                    Date date = simpleDateFormat.parse(filter.getValue());
//	                                    Predicate predicate = criteriaBuilder.equal(root.get(filter.getKey()), date);
//	                                    predicateList.add(predicate);
//	                                } catch (Exception e) {
//	                                    e.printStackTrace();
//	                                }
//	                                continue;
//	                            case LIKE:
//	                                Predicate predicate = criteriaBuilder.like(root.join(filter.getJoinObject()).get(filter.getKey()), "%" + filter.getValue() + "%");
//	                                predicateList.add(predicate);
//	                                continue;
//	                            case BETWEEN:
//	                                String[] split = filter.getValue().split(",");
//	                                Predicate predicate = criteriaBuilder.between(root.join(filter.getJoinObject()).get(filter.getKey()), Long.parseLong(split[0]), Long.parseLong(split[1]));
//	                                predicateList.add(predicate);
//	                                continue;
//	                            case DATE_GREATER_THAN_EQUAL_TO:
//	                                try {
//	                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//	                                    Date date = simpleDateFormat.parse(filter.getValue());
//	                                    Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.join(filter.getJoinObject()).get(filter.getKey()), date);
//	                                    predicateList.add(predicate);
//	                                } catch (Exception e) {
//	                                    e.printStackTrace();
//	                                }
//	                                continue;
//	                            case DATE_LESS_THAN:
//	                                try {
//	                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//	                                    Date date = simpleDateFormat.parse(filter.getValue());
//	                                    Predicate predicate = criteriaBuilder.lessThan(root.join(filter.getJoinObject()).get(filter.getKey()), date);
//	                                    predicateList.add(predicate);
//	                                } catch (Exception e) {
//	                                    e.printStackTrace();
//	                                }
//	                                continue;
//	                            case DATE_BETWEEN:
//	                                try {
//	                                    String[] split = filter.getValue().split(",");
//	                                    String startDateStr = split[0];
//	                                    String endDateStr = split[1];
//	                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//	                                    Date startDate = simpleDateFormat.parse(startDateStr);
//	                                    Date endDate = simpleDateFormat.parse(endDateStr);
//	                                    Predicate predicate = criteriaBuilder.between(root.join(filter.getJoinObject()).get(filter.getKey()), startDate, endDate);
//	                                    predicateList.add(predicate);
//	                                } catch (Exception e) {
//	                                    e.printStackTrace();
//	                                }
//	                                continue;
//	                            default:
//	                                throw new IllegalStateException("Unexpected value: " + filter.getJoinOperation());
//	                        }
//	                    default:
//	                        throw new IllegalStateException("Unexpected value: " + filter.getOperation());
//	                }
//	            }
//
//	            if (globalOperator.equals(GlobalOperator.AND)) {
//	                return criteriaBuilder.and((Predicate[])predicateList.toArray(new Predicate[0]));
//	            } else {
//	                return criteriaBuilder.or((Predicate[])predicateList.toArray(new Predicate[0]));
//	            }
//	        };
//	    }
//
//}


package com.thryve.pgfinder.model.common.filter.specification;

import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.thryve.pgfinder.model.common.GlobalOperator;
import com.thryve.pgfinder.model.common.filter.Filter;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FiltersSpecification<T> {

    public Specification<T> getSearchSpecification(final Filter filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(filter.getKey()), filter.getValue());
    }

    public Specification<T> getSearchSpecification(List<Filter> filterList, GlobalOperator globalOperator) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            for (Filter filter : filterList) {
                try {
                    switch (filter.getOperation()) {
                        case EQUAL:
                            predicateList.add(criteriaBuilder.equal(root.get(filter.getKey()), filter.getValue()));
                            break;
                        case NOT_EQUAL:
                            predicateList.add(criteriaBuilder.notEqual(root.get(filter.getKey()), filter.getValue()));
                            break;
                        case LIKE:
                            predicateList.add(criteriaBuilder.like(root.get(filter.getKey()), "%" + filter.getValue() + "%"));
                            break;
                        case IN:
                            predicateList.add(root.get(filter.getKey()).in(Arrays.asList(filter.getValue().split(","))));
                            break;
                        case GREATER_THAN:
                            predicateList.add(criteriaBuilder.greaterThan(root.get(filter.getKey()), filter.getValue()));
                            break;
                        case LESS_THAN:
                            predicateList.add(criteriaBuilder.lessThan(root.get(filter.getKey()), filter.getValue()));
                            break;
                        case DATE_EQUAL: {
                            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(filter.getValue());
                            predicateList.add(criteriaBuilder.equal(root.get(filter.getKey()), date));
                            break;
                        }
                        case DATE_BETWEEN: {
                            String[] split = filter.getValue().split(",");
                            Date startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(split[0]);
                            Date endDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(split[1]);
                            predicateList.add(criteriaBuilder.between(root.get(filter.getKey()), startDate, endDate));
                            break;
                        }
                        case BETWEEN: {
                            String[] split = filter.getValue().split(",");
                            predicateList.add(criteriaBuilder.between(root.get(filter.getKey()), Long.parseLong(split[0]), Long.parseLong(split[1])));
                            break;
                        }
                        case JOIN:
                            switch (filter.getJoinOperation()) {
                                case EQUAL:
                                    predicateList.add(criteriaBuilder.equal(root.join(filter.getJoinObject()).get(filter.getKey()), filter.getValue()));
                                    break;
                                case LIKE:
                                    predicateList.add(criteriaBuilder.like(root.join(filter.getJoinObject()).get(filter.getKey()), "%" + filter.getValue() + "%"));
                                    break;
                                case BETWEEN: {
                                    String[] split = filter.getValue().split(",");
                                    predicateList.add(criteriaBuilder.between(root.join(filter.getJoinObject()).get(filter.getKey()), Long.parseLong(split[0]), Long.parseLong(split[1])));
                                    break;
                                }
                                case DATE_EQUAL: {
                                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(filter.getValue());
                                    predicateList.add(criteriaBuilder.equal(root.join(filter.getJoinObject()).get(filter.getKey()), date));
                                    break;
                                }
                                case DATE_GREATER_THAN_EQUAL_TO: {
                                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(filter.getValue());
                                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.join(filter.getJoinObject()).get(filter.getKey()), date));
                                    break;
                                }
                                case DATE_LESS_THAN: {
                                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(filter.getValue());
                                    predicateList.add(criteriaBuilder.lessThan(root.join(filter.getJoinObject()).get(filter.getKey()), date));
                                    break;
                                }
                                case DATE_BETWEEN: {
                                    String[] split = filter.getValue().split(",");
                                    Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(split[0]);
                                    Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(split[1]);
                                    predicateList.add(criteriaBuilder.between(root.join(filter.getJoinObject()).get(filter.getKey()), startDate, endDate));
                                    break;
                                }
                                default:
                                    throw new IllegalStateException("Unexpected join operation: " + filter.getJoinOperation());
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected operation: " + filter.getOperation());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // You may choose to log and continue, or throw exception
                }
            }

            return globalOperator.equals(GlobalOperator.AND)
                    ? criteriaBuilder.and(predicateList.toArray(new Predicate[0]))
                    : criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
        };
    }
}


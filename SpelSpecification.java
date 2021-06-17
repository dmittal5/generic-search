package com.baeldung.persistence.dao.spelEx;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.TypedValue;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class SpelSpecification<T> implements Specification<T> {
    private com.baeldung.persistence.dao.spelEx.SearchCriteria criteria;

    public SpelSpecification() {
        super();
    }

    public SpelSpecification(com.baeldung.persistence.dao.spelEx.SearchCriteria searchCriteria) {
        super();
        this.criteria = searchCriteria;
    }

    public com.baeldung.persistence.dao.spelEx.SearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        if(criteria.getOperator().equals("==")){
            if(root.get(criteria.getKey()).getJavaType().getSuperclass() == Number.class){
                return builder.equal(root.get(criteria.getKey()), ((Number)criteria.getValue()).doubleValue());
            }
            if(root.get(criteria.getKey()).getJavaType() == Boolean.class){
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
            return builder.equal(root.get(criteria.getKey()).as(String.class), String.valueOf(criteria.getValue()));
        }
        if(criteria.getOperator().equals(">")){
            if(root.get(criteria.getKey()).getJavaType().getSuperclass() == Number.class){
                return builder.greaterThan(root.get(criteria.getKey()), ((Number)criteria.getValue()).doubleValue());
            }
            return builder.greaterThan(root.get(criteria.getKey()).as(String.class), String.valueOf(criteria.getValue()));
        }
        if(criteria.getOperator().equals("<")){
            if(root.get(criteria.getKey()).getJavaType().getSuperclass() == Number.class){
                return builder.lessThan(root.get(criteria.getKey()), ((Number)criteria.getValue()).doubleValue());
            }
            return builder.lessThan(root.get(criteria.getKey()).as(String.class), String.valueOf(criteria.getValue()));
        }
        if(criteria.getOperator().equals("in")){
            Object[] valuesList = ((List) ((TypedValue)criteria.getValue()).getValue()).toArray();
            return builder.and(root.get(criteria.getKey()).in(valuesList));
        }
        return null;
    }
}

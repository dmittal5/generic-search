package com.baeldung.persistence.dao.spelEx;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.spel.SpelNode;

public interface CustomOperator<T> {
    Specification<T> getSpecification(SpelNode node, Specification<T> specLeft, Specification<T> specRight);
}

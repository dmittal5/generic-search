package com.baeldung.persistence.dao.spelEx;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class MethodReference<T> implements CustomOperator<T>{

    Function<SearchCriteria, Specification<T>> converter;

    public MethodReference(){
        super();
        converter = SpelSpecification::new;
    }

    @Override
    public Specification<T> getSpecification(SpelNode node, Specification<T> specLeft, Specification<T> specRight) {
        String colName = ((PropertyOrFieldReference)node.getChild(0)).getName();
        String operator = ((org.springframework.expression.spel.ast.MethodReference) node.getChild(1)).getName();
        EvaluationContext ec = new StandardEvaluationContext(List.class);
        ExpressionState es = new ExpressionState(ec);
        TypedValue value = node.getChild(1).getChild(0).getTypedValue(es);
        return converter.apply(new SearchCriteria(colName, operator, value));
    }
}

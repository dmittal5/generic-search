package com.baeldung.persistence.dao.spelEx;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.*;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OperatorReference<T> implements CustomOperator<T> {

    Function<SearchCriteria, Specification<T>> converter;

    public OperatorReference(){
        super();
        converter = SpelSpecification::new;
    }

    @Override
    public Specification<T> getSpecification(SpelNode node, Specification<T> specLeft, Specification<T> specRight) {
        Operator op = (Operator) node;
        if (op instanceof OpAnd) return Specification.where(specLeft).and(specRight);
        if (op instanceof OpOr) return Specification.where(specLeft).or(specRight);
        String colName = node.getChild(0).toStringAST();
        String valueStr = getValueString(node.getChild(1));
        return converter.apply(new SearchCriteria(colName, op.getOperatorName(), valueStr));
    }

    private String getValueString(SpelNode valueNode) {
        if(valueNode instanceof StringLiteral) {
            String valueStr = valueNode.toString();
            return valueStr.substring(1, valueStr.length() - 1);
        }
        return valueNode.toStringAST();
    }
}

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
        String colName = ((PropertyOrFieldReference)node.getChild(0)).getName();
        Object valueStr = getValueString(node.getChild(1));
        return converter.apply(new SearchCriteria(colName, op.getOperatorName(), valueStr));
    }

    private Object getValueString(SpelNode valueNode) {
        if(valueNode instanceof Literal){
            return ((Literal) valueNode).getLiteralValue().getValue(); //boolean support
        }
        return valueNode.toStringAST();
    }
}

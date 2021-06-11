package com.baeldung.persistence.dao.spelEx;

import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.CompoundExpression;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Component;

@Component
public class OperatorFactory {
    public OperatorFactory(){
        super();
    }

    public CustomOperator getCustomOperator(SpelNode node){
        if(node instanceof Operator) return new OperatorReference();
        if(node instanceof CompoundExpression) return new MethodReference();
        return null;
    }
}

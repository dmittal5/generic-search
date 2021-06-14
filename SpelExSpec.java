package com.baeldung.persistence.dao.spelEx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.MethodReference;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Component
public class SpelExSpec<T> {

    @Autowired
    private OperatorFactory operatorFactory;

    public SpelExSpec(){
        super();
        this.operatorFactory = new OperatorFactory();
    }

    public Specification<T> evaluate(String expressionString){
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(expressionString);
        return buildSpecificationfromAST(((SpelExpression)expression).getAST());
    }

    public Specification<T> buildSpecificationfromAST(SpelNode node){
        if(node.getChildCount() == 0 || node instanceof MethodReference) return null;
        Specification<T> specLeft = buildSpecificationfromAST(node.getChild(0));
        Specification<T> specRight = buildSpecificationfromAST(node.getChild(1));
        return operatorFactory.getCustomOperator(node).getSpecification(node, specLeft, specRight);
    }

}



package com.baeldung.persistence.dao.spelEx;

import org.springframework.stereotype.Component;

@Component
public class SearchCriteria {
    private String key;
    private String operator;
    private Object value;

    public SearchCriteria(){

    }

    public SearchCriteria(String key, String operator, Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}


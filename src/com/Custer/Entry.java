package com.Custer;

import java.util.HashMap;

public class Entry {
    private String classification;
    HashMap<String, String> values;

    public Entry(String _class) {
        classification = _class;
        values = new HashMap<>();
    }

    public String getClassification() {
        return classification;
    }

    public void addValue(String property, String value) {
        values.put(property, value);
    }

    public String getValue(String property) {
        return values.get(property);
    }

}

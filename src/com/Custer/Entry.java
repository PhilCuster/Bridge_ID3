package com.Custer;

import java.util.HashMap;

public class Entry {
    private String classification;
    HashMap<String, String> values;
    private String name;

    public Entry() {
        values = new HashMap<>();
    }

    public void setClassification (String _classi) {
        classification = _classi;
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

    public void setName(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

}

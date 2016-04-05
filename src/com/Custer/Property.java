package com.Custer;

public class Property {
    private String name;
    private String[] values;

    public Property(String _name, String[] _values) {
        name = _name;
        values = _values;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }
}

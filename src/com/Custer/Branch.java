package com.Custer;

/**
 * Created by Phil on 4/5/2016.
 */
public class Branch {
    private Node parent;
    private Node child;
    private String name;

    public Branch(Node _parent, String _name) {
        parent = _parent;
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setChild(Node _child) {
        child = _child;
    }

    public Node getChild() {
        return child;
    }
}

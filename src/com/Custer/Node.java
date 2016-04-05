package com.Custer;

import java.util.ArrayList;

public class Node {
    private Branch parent;
    private ArrayList<Branch> children;
    private String classification;
    private boolean leaf;

    public Node(String _classification, boolean _leaf) {
        classification = _classification;
        children = new ArrayList<>();
        leaf = _leaf;
    }

    public void addChild(Branch child) {
        children.add(child);
    }

    public void setParent(Branch _parent) {
        _parent = parent;
    }

    public boolean isLeaf() {
        return leaf;
    }
}

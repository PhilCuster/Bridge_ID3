package com.Custer;

import java.util.ArrayList;

public class Node {
    private Branch parent;
    private ArrayList<Branch> children;
    private String classification;
    private boolean leaf;
    private int level;

    public Node(String _classification, boolean _leaf, int _level) {
        classification = _classification;
        children = new ArrayList<>();
        leaf = _leaf;
        level = _level;
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

    public int getLevel() {
        return level;
    }
}

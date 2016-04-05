package com.Custer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Building our library of properties.
        ArrayList<Property> properties = new ArrayList<>();

        properties.add(new Property("RIVER", new String[]{"A", "M", "O", "?"}));
        properties.add(new Property("ERECTED", new String[]{"CRAFTS", "EMERGING", "MATURE", "MODERN", "?"}));
        properties.add(new Property("PURPOSE", new String[]{"WALK", "AQUEDUCT", "RR", "HIGHWAY", "?"}));
        properties.add(new Property("LENGTH", new String[]{"SHORT", "MEDIUM", "LONG", "?"}));
        properties.add(new Property("LANES", new String[]{"1", "2", "4", "6", "?"}));
        properties.add(new Property("CLEAR-G", new String[]{"N", "G", "?"}));
        properties.add(new Property("T-OR-D", new String[]{"THROUGH", "DECK", "?"}));
        properties.add(new Property("MATERIAL", new String[]{"WOOD", "IRON", "STEEL", "?"}));
        properties.add(new Property("SPAN", new String[]{"SHORT", "MEDIUM", "LONG", "?"}));
        properties.add(new Property("REL-L", new String[]{"S", "S-F", "F", "?"}));
        properties.add(new Property("TYPE", new String[]{"WOOD", "SUSPEN", "SIMPLE-T", "ARCH", "CATILEV", "CONT-T", "?"}));
        String[] temp = new String[53];
        for(int i = 1; i < 53; i++) {
            temp[i-1] = String.valueOf(i);
        }
        temp[52] = "?";
        properties.add(new Property("LOCATION", temp));


        // Read from a file and generate the example set.



    }

    private Node induceTree(ArrayList<Entry> example_set, ArrayList<Property> properties) {
        String x = checkClasses(example_set);
        if (!(x.equals("-"))) {
            return new Node(x, true);
        }
        else if (properties.size() == 0) {
            return new Node(mostCommonClass(example_set), true);
        }
        else {
            Property currentProp = properties.get(0);
            Node newNode = new Node(currentProp.getName(), false);
            properties.remove(0);

            for (String value : currentProp.getValues()) {
                Branch newBranch = new Branch(newNode, value);
                newNode.addChild(newBranch);
                newBranch.setChild(induceTree(generateSet(example_set, value, currentProp), properties));
            }
            return newNode;
        }
    }

    private ArrayList<Entry> generateSet(ArrayList<Entry> example_set, String value, Property property) {
        ArrayList<Entry> partition = new ArrayList<>();
        for (Entry e : example_set) {
            String temp = e.getValue(property.getName());
            if (temp.equals(value)) {
                partition.add(e);
            }
        }
        return partition;
    }

    private String checkClasses(ArrayList<Entry> example_set) {
        String temp = example_set.get(0).getClassification();
        for (int i = 1; i < example_set.size(); i++) {
            if (!(example_set.get(i).getClassification().equals(temp))) {
                return "-";
            }
        }
        return temp;
    }

    private String mostCommonClass(ArrayList<Entry> example_set) {
        HashMap<String, Integer> hm = new HashMap<>();
        for (int i = 0; i < example_set.size(); i++) {
            String temp = example_set.get(i).getClassification();
            if (hm.putIfAbsent(temp, 1) != null) {
                hm.put(temp, hm.get(temp) + 1);
            }
        }
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : hm.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }
}

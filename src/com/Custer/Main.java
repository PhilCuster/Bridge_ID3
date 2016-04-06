package com.Custer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static int maxDepth;

    public static void main(String[] args) {

        maxDepth = 0;

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
        String inFile = "bridge_in.txt";
        String line = null;

        ArrayList<Entry> example_set = new ArrayList<>();

        try {
            System.out.println("Reading " + inFile + "...");

            BufferedReader in = new BufferedReader(new FileReader(inFile));

            while ((line = in.readLine()) != null) {
                // System.out.println(line);
                Entry tempEntry = new Entry();
                String[] split_line = line.split(",");

                tempEntry.addValue("RIVER", split_line[1]);
                tempEntry.addValue("LOCATION", split_line[2]);
                tempEntry.addValue("ERECTED", split_line[3]);
                tempEntry.addValue("PURPOSE", split_line[4]);
                tempEntry.addValue("LENGTH", split_line[5]);
                tempEntry.addValue("LANES", split_line[6]);
                tempEntry.addValue("CLEAR-G", split_line[7]);
                tempEntry.addValue("T-OR-D", split_line[8]);
                tempEntry.addValue("MATERIAL", split_line[9]);
                tempEntry.addValue("SPAN", split_line[10]);
                tempEntry.addValue("REL-L", split_line[11]);
                tempEntry.addValue("TYPE", split_line[12]);

                example_set.add(tempEntry);
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file: " + inFile);
            return;
        } catch (IOException e) {
            System.out.println("IO Error...");
            return;
        }

        inFile = "bridge_out.txt";

        try {
            System.out.println("Reading " + inFile + "...");
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            int count = 0;
            while ((line = in.readLine()) != null) {
                line = line.replace("\n", "");
                example_set.get(count).setClassification(line);
                count++;
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file: " + inFile);
            return;
        } catch (IOException e) {
            System.out.println("IO Error...");
            return;
        }

        System.out.println("Input complete! Example set includes " + example_set.size() + " entries.");

        System.out.println("Building tree...");
        Node startNode = induceTree(example_set, properties, 1);
        System.out.println("Created " + maxDepth + " levels...");
        System.out.println("Tree complete!");

        if (args.length == 0) {
            System.out.println("No input string given!!!");
        } else {
            Entry myEntry = new Entry();
           String[] inputSplit = args[0].split(",");
            myEntry.setName(inputSplit[0]);
            myEntry.addValue("RIVER", inputSplit[1]);
            myEntry.addValue("LOCATION", inputSplit[2]);
            myEntry.addValue("ERECTED", inputSplit[3]);
            myEntry.addValue("PURPOSE", inputSplit[4]);
            myEntry.addValue("LENGTH", inputSplit[5]);
            myEntry.addValue("LANES", inputSplit[6]);
            myEntry.addValue("CLEAR-G", inputSplit[7]);
            myEntry.addValue("T-OR-D", inputSplit[8]);
            myEntry.addValue("MATERIAL", inputSplit[9]);
            myEntry.addValue("SPAN", inputSplit[10]);
            myEntry.addValue("REL-L", inputSplit[11]);
            myEntry.addValue("TYPE", inputSplit[12]);
            System.out.println("ID: " + myEntry.getName() + "  Class: " + checkTree(myEntry, startNode));
        }

    }

    public static Node induceTree(ArrayList<Entry> example_set, ArrayList<Property> properties, int depth) {
        String x = checkClasses(example_set);
        if (!(x.equals("-"))) {
            if (depth > maxDepth) {
                maxDepth = depth;
            }
            return new Node(x, true, depth);
        }
        else if (properties.size() == 0) {
            if (depth > maxDepth) {
                maxDepth = depth;
            }
            return new Node(mostCommonClass(example_set), true, depth);
        }
        else {
            Property currentProp = properties.get(0);
            Node newNode = new Node(currentProp.getName(), false, depth);
            properties.remove(0);

            for (String value : currentProp.getValues()) {
                Branch newBranch = new Branch(newNode, value);
                newNode.addChild(newBranch);
                ArrayList<Entry> tempSet = generateSet(example_set, value, currentProp);
                if (!(tempSet.size() == 0)) {
                    newBranch.setChild(induceTree(tempSet, properties, depth++));
                }
            }
            return newNode;
        }
    }

    public static String checkTree(Entry entry, Node root) {
        Node currentNode = root;
        // Check if the current node is terminal.
        while(true) {
            if (currentNode.isLeaf()) {
                return currentNode.getClassification();
            } else {
                String currentProp = currentNode.getClassification();
                String currentValue = entry.getValue(currentProp);
                for (Branch b : currentNode.getChildren()) {
                    if (b.getName().equals(currentValue)) {
                        currentNode = b.getChild();
                    }
                }
            }
        }
    }

    private static ArrayList<Entry> generateSet(ArrayList<Entry> example_set, String value, Property property) {
        ArrayList<Entry> partition = new ArrayList<>();
        for (Entry e : example_set) {
            String temp = e.getValue(property.getName());
            if (temp.equals(value)) {
                partition.add(e);
            }
        }
        return partition;
    }

    private static String checkClasses(ArrayList<Entry> example_set) {
        String temp = example_set.get(0).getClassification();
        for (int i = 1; i < example_set.size(); i++) {
            if (!(example_set.get(i).getClassification().equals(temp))) {
                return "-";
            }
        }
        return temp;
    }

    private static String mostCommonClass(ArrayList<Entry> example_set) {
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

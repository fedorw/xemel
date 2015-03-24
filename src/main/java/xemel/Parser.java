package xemel;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;

public class Parser {
    public static int BUFSIZE = 2048;
    NodeList nodes=new NodeList();
    public NodeList getNodes() {
        return nodes;
    }
    private Node root=null;
    public Parser(InputStream in) throws IOException  {
        char[] data=null;
        try  {
            // TODO interpret procession instruction
            data=IOUtils.toCharArray(in, "UTF-8");
        }
        finally {
            in.close();
        }
        tokenize(data);
        buildTree();
//        printTree(root);
    }

    public Node getRoot() {
        return root;
    }


//    void printNodes() {
//        int indent =0;
//        for (Node node:nodes) {
//            if (node.end) {
//                indent--;
//            }
//            for (int i=0;i<indent;i++) {
//                System.err.print(" ");
//            }
//            System.err.println(node);
//            if (node.begin) {
//                indent++;
//            }
//        }
//    }
    void buildTree() {
        Stack<Node> stack=new Stack();
        boolean gotroot=false;
        for (Node node:nodes) {
            if (!gotroot&&node.isDeclaration()) {
                continue;
            }
            if (root==null) {
                gotroot=true;
                root = node;
                stack.push(root);
                continue;
            }
            if (!node.isClose()) {
                stack.peek().addChild(node);
            }
            if (node.isOpen() && !node.isClose() ) {
                stack.push(node);
                continue;
            }

            if (node.isClose() && !node.isOpen()) {
                stack.pop();
                continue;
            }
        }
    }
    private void tokenize(char[] buf) {
        Node node = null;
        boolean inTag = false;
        StringBuilder data=new StringBuilder("");
        int startTag=0,endTag=0;
        for (int i = 0; i < buf.length; i++)
        {
            char c=buf[i];
//            data.append(c);
            switch (c) {
                case '<':
                    if (data.length()>0) {
                        nodes.add(Node.parse(data));
                        data.setLength(0);
                    }
                    data.append(c);
                    inTag=true;
                    break;
                case '>':
                    data.append(c);
                    nodes.add(Node.parse(data));
                    data.setLength(0);
                    inTag=false;
                    break;
                default:
                    data.append(c);
                    break;
            }
        }
        if (data.length()>0) {
            nodes.add(Node.parse(data));
        }
    }
}



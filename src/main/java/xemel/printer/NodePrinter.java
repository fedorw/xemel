package xemel.printer;

import xemel.Node;
import xemel.NodeList;
import xemel.Parser;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by fedor on 3/24/15.
 */
public abstract class NodePrinter {
    protected NodePrinter() {}

    public void print(Parser parser, PrintStream out) {
        printNodes(parser.getRoot(), 0, out);
//        printNodes(parser.getNodes().get(0), 0, out);
    }
    private void printNodes(Node node, int indent, PrintStream out) {
        if (node.textOnly()) {
            out.print(node2string(node, indent)+"\n");
        } else {
            out.print(node2string(node, indent) + "\n");
        }
        for (Node n:node.getChildren()) {
            printNodes(n,indent+1, out);
            out.print(indent(indent)+closeNode(node)+"\n");
        }
    }
    public String indent(int len) {
        String s="";
        for (int i=0;i<len;i++) {
            s+=" ";
        }
        return s;
    }
    public abstract String node2string(Node node, int indent) ;

    public abstract String closeNode(Node node) ;


}

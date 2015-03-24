package xemel.printer;

import xemel.Node;

import java.io.OutputStream;

/**
 * Created by fedor on 3/24/15.
 */
public class SExpressionPrinter extends NodePrinter {

    @Override
    public String node2string(Node node, int indent) {
        if (node.getText()!=null) {
            return  indent(indent)+"\""+node.getText()+"\"";
        }
        String s="";
        if (node.isOpen()) {
            s+=indent(indent)+"("+node.getName();
            if (node.getAttributes().size()>0) {
                s += "\n" + indent(indent + 1) + "(@ ";
                int count=0;
                for (String k : node.getAttributes().keySet()) {
                    count++;
                    s += "(" + k + " \"" + node.getAttributes().get(k) + "\")";
                    if (count<node.getAttributes().size()) {
                        s+="\n"+indent(indent+1);
                        s+="   ";
                    }
                }
                s+=")\n";
            }
        }
        if (node.isClose()) {
            if (!node.textOnly()) {
                s+=indent(indent) + ")";
            }
            else {
                s+="";
            }
        }
        return s;
    }
}

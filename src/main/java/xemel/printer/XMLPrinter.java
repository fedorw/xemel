package xemel.printer;

import xemel.Node;

/**
 * Created by fedor on 3/24/15.
 */
public class XMLPrinter extends NodePrinter {

    @Override
    public String node2string(Node node, int indent) {
        if (node.getText()!=null) {
            return indent(indent)+node.getText().trim();
        }

        StringBuilder sb=new StringBuilder("");
        if (!node.isOpen()&& node.isClose()) {
            indent--;
            sb.append(indent(indent));
            sb.append("<");
            sb.append("/");
        } else {
            sb.append(indent(indent));
            sb.append("<");
        }
        sb.append(node.getName());
        for (String v:node.getAttributes().keySet()) {
            sb.append(" "+v+"='");
            sb.append(node.getAttributes().get(v));
            sb.append("'");
        }
        if (node.isOpen()&& node.isClose()) {
            sb.append("/");
        }
        sb.append(">");
        return sb.toString();
    }

    @Override
    public String closeNode(Node node) {
        return "</"+node.getName()+">";
    }
}

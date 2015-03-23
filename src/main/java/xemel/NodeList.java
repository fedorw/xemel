package xemel;

import java.util.ArrayList;

class NodeList extends ArrayList<Node> {
    public boolean add(Node node) {
        if (node==null){
            return false;
        }
        return  super.add(node);
    }
}

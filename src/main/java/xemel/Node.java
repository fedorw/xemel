package xemel;
import java.util.*;

public class Node {
    private String name;
    private String text;
    boolean begin=false,end=false;
    Map<String,String> attributes=new HashMap<>();
    NodeList children= new NodeList();

    public String getText() {
        return text;
    }
public boolean textOnly() {
    return children.size()==1 && children.get(0).isTextNode();
}
    public NodeList getChildren() {
        return children;
    }
    public boolean isOpen() {
        return begin;
    }
    public boolean isClose() {
        return end;
    }
    public String getName() {
        return name;
    }
    public boolean isDeclaration() {
//        <?xml version="version_number" encoding="encoding_declaration" standalone="standalone_status" ?>
        return this.name!=null&& this.name.startsWith("<?xml");
    }
    public boolean isTextNode() {
        return text!=null;
    }
    public void addChild(Node node) {
        children.add(node);
    }
    public static Node parse(final StringBuilder str) {
        final String s = str.toString().trim();
        if (s.length()==0) {
            return null;
        }
        if (s.contains("<namespace ")) {
            int uu=0;
        }
        Node n = new Node();
        if (s.startsWith("<")) {
            if (s.startsWith("</")) {
                n.end = true;
            } else {
                n.begin = true;
            }
        }
        if (s.endsWith("/>")) {
            n.end = true;
        }

        if (!n.begin && !n.end) {
            n.text=s.trim();
            return n;
        }
        char inString = 0;
        StringBuilder data=new StringBuilder();
        String key=null,value=null;
        boolean gotname=false;
        char prev=0;
        char c=0;
        for (int i=0;i<s.length();i++) {
            prev =c;
            c = s.charAt(i);
            switch (c) {
                case ' ':
                    if (inString==0 && !gotname) {
                        n.name = data.toString();
                        data.setLength(0);
                        gotname=true;
                    }
                    break;
                case '/': break;
                case '<': break;
                case '>': break;
                case '"':
                case '\'':
                    if (inString == 0 ) {
                        inString = c;
                    } else {
                        if (inString != 0 && inString == c) {
                            // endoff attr
                            inString = 0;
                            value = data.toString();
                            n.attributes.put(key, value);
                            data.setLength(0);
                        }
                    }
                    break;
                case '=':
                    if (inString!=0)  {
                        data.append(c);
                    } else {
                        key = data.toString();
                        data.setLength(0);
                        // data = key
                    }
                    break;
                default:
                    data.append(c);
                    break;

            }
        }
        if (!gotname) { n.name=data.toString(); }
        return n;
    }

    public String toString() {
        if (this.text!=null) {
            return text.trim();
        }

        StringBuilder sb=new StringBuilder("<");
        if (!begin && end) {
            sb.append("/");
        }
        sb.append(name);
        for (String v:attributes.keySet()) {
            sb.append(" "+v+"='");
            sb.append(attributes.get(v));
            sb.append("'");
        }
        if (end && begin) {
            sb.append("/");
        }
        sb.append(">");
        return sb.toString();
    }

    private String indent(int size) {
        String s="";
        for (int i=0;i<size;i++) {
            s+=" ";
        }
        return s;
    }

    public String toSExpr(int indent) {
        if (text!=null) {
            return  indent(indent)+"\""+text+"\"";
        }
        String s="";
        if (isOpen()) {
            s+=indent(indent)+"("+name;
            if (attributes.size()>0) {
                s += "\n" + indent(indent + 1) + "(@ ";
                int count=0;
                for (String k : attributes.keySet()) {
                    count++;
                    s += "(" + k + " \"" + attributes.get(k) + "\")";
                    if (count<attributes.size()) {
                        s+="\n"+indent(indent+1);
                        s+="   ";
                    }
                }
                s+=")\n";
            }
        }
        if (isClose()) {
            if (!textOnly()) {
                s+=indent(indent) + ")\n";
            }
            else {
                s+="\n";
            }
        }
        return s;

    }
}

package xemel;


import junit.framework.TestCase;
import xemel.printer.SExpressionPrinter;
import xemel.printer.XMLPrinter;

import java.io.InputStream;

public class ParserTest extends TestCase {
    public void testParser() throws Exception {
        Parser p = new Parser(in("wiki.xml"));
//        new SExpressionPrinter().print(p, System.out);
        new XMLPrinter().print(p,System.out);
    }

    private InputStream in(String name) {
        return this.getClass().getResourceAsStream(name);
    }
}

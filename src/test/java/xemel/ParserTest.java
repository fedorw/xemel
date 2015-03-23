package xemel;


import junit.framework.TestCase;

import java.io.InputStream;

public class ParserTest extends TestCase {
    public void testParser() throws Exception {
        Parser p = new Parser(in("wiki.xml"));
    }

    private InputStream in(String name) {
        return this.getClass().getResourceAsStream(name);
    }
}

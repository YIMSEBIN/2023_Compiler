import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;


public class Main {
    public static void main(String[] args) throws Exception {
        tinyPythonLexer lexer = new tinyPythonLexer(CharStreams.fromFileName("test.tpy"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tinyPythonParser parser = new tinyPythonParser(tokens);
        ParseTree tree = parser.program();
    }
}

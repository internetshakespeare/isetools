/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.nines.ise;

import ca.nines.ise.grammar.ISELexer;
import ca.nines.ise.grammar.ISEParser;
import ca.nines.ise.node.Node;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Michael Joyce <ubermichael@gmail.com>
 */
public class Main {

  public static void main(String[] args) throws IOException {
    String in = "hel<t n='3'>";
    ANTLRInputStream ais = new ANTLRInputStream(in);
    ISELexer lexer = new ISELexer(ais);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ISEParser parser = new ISEParser(tokens);
    ParseTree tree = parser.document();
    System.out.println(tree.toStringTree(parser));
  }

}

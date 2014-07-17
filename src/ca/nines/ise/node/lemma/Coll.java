/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.nines.ise.node.lemma;

import ca.nines.ise.util.BuilderInterface;
import ca.nines.ise.util.XMLDriver;
import java.io.IOException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author michael
 */
public class Coll extends Lemma {

  private final String lemNote;
  private final String lemResp;
  private final Map<String, String> readingNotes;
  private final Map<String, String> readings;

  public static class CollBuilder extends Lemma.LemmaBuilder
          implements BuilderInterface<Coll> {

    private String lemNote;
    private String lemResp;
    private final Map<String, String> readingNotes;
    private final Map<String, String> readings;

    private CollBuilder() {
      super();
      lemResp = "";
      lemNote = "";
      readings = new HashMap<>();
      readingNotes = new HashMap<>();
    }

    public CollBuilder from(Node in) {
      super.from(in);
      return this;
    }

    public CollBuilder from(String in) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerConfigurationException, TransformerException {
      Document doc = new XMLDriver().drive(in);
      Node n = doc.getElementsByTagName("coll").item(0);
      return from(n);
    }
    public CollBuilder addReading(String resp, String reading) {
      readings.put(resp, reading);
      return this;
    }

    public CollBuilder addReadingNote(String resp, String readingNote) {
      readingNotes.put(resp, readingNote);
      return this;
    }

    @Override
    public Coll build() {
      return new Coll(lem, lineNumber, node, source, tln, lemResp, lemNote, asl, readings, readingNotes);
    }

    /**
     * @param lemNote the lemNote to set
     */
    public CollBuilder setLemNote(String lemNote) {
      this.lemNote = lemNote;
      return this;
    }

    /**
     * @param lemResp the lemResp to set
     */
    public CollBuilder setLemResp(String lemResp) {
      this.lemResp = lemResp;
      return this;
    }

  }

  public static CollBuilder builder() {
    return new CollBuilder();
  }

  private Coll(String lem, int lineNumber, String node, String source, String tln, String lemResp, String lemNote, String asl,  Map<String, String> readings, Map<String, String> readingNotes) {
    super(lem, lineNumber, node, source, tln, asl);
    this.lemResp = lemResp;
    this.lemNote = lemNote;
    this.readings = new HashMap<>(readings);
    this.readingNotes = new HashMap<>(readingNotes);
  }

  /**
   * @return the lemNote
   */
  public String getLemNote() {
    return lemNote;
  }

  /**
   * @return the lemResp
   */
  public String getLemResp() {
    return lemResp;
  }

  public String getReading(String resp) {
    return readings.get(resp);
  }

  public String getReadingNote(String resp) {
    return readingNotes.get(resp);
  }

  public String[] getReadingNotes() {
    String[] rdgs = readingNotes.keySet().toArray(new String[readingNotes.size()]);
    Arrays.sort(rdgs);
    return rdgs;
  }

  public String[] getReadings() {
    String[] rdgs = readings.keySet().toArray(new String[readings.size()]);
    Arrays.sort(rdgs);
    return rdgs;
  }

  public boolean hasReading(String resp) {
    return readings.containsKey(resp);
  }

  public boolean hasReadingNote(String resp) {
    return readingNotes.containsKey(resp);
  }

  @Override
  public String toString() {
    Formatter formatter = new Formatter();
    formatter.format("%s%n", super.toString());
    for (String rdg : getReadings()) {
      formatter.format("    %s:%s%n", rdg, getReading(rdg));
      if (hasReadingNote(rdg)) {
        formatter.format("    note: %s%n", getReadingNote(rdg));
      }
    }
    return formatter.toString();
  }

}

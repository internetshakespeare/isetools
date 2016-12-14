/*
 * Copyright (C) 2015 Malcolm Moran <malcolm.moran@outlook.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package ca.nines.ise.validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import ca.nines.ise.annotation.ErrorCode;
import ca.nines.ise.dom.DOM;
import ca.nines.ise.log.Log;
import ca.nines.ise.log.Message;
import ca.nines.ise.node.Node;
import ca.nines.ise.node.NodeType;
import ca.nines.ise.node.StartNode;

/**
 * Ensures FOREIGN tags have valid @lang attributes, with respect to ISO 639-1/3
 * 
 * @author Malcolm Moran <malcolm.moran@outlook.com>
 */
public class ForeignValidator {
  private final List<String> langCodes;
  
  public ForeignValidator(){
    langCodes = new ArrayList<String>();
    try {
      InputStream in = getClass().getResourceAsStream("/data/iso-639-3_20160115.tab");
      StringWriter wr = new StringWriter();
      IOUtils.copy(in, wr);
      CSVParser parser = CSVParser.parse(wr.toString(), CSVFormat.TDF.withHeader("3", "2B", "2T", "1").withSkipHeaderRecord(true));
      
      for (CSVRecord record : parser){
        if (!record.get("3").equals(""))
          langCodes.add(record.get("3"));
        if (!record.get("1").equals(""))
          langCodes.add(record.get("1"));
      }
      
    } catch (IOException e) {
        e.printStackTrace();
    } 
  }
  
  @ErrorCode(code = {
    "validator.foreign.invalidCode",
    "validator.foreign.missingCode"
  })
  private void process_start(StartNode n) {
    if (n.hasAttribute("lang")){
      String code = n.getAttribute("lang");
      String lang = code;
      if (code.contains("-")){
        lang = code.substring(0, code.indexOf('-'));
      }
      if (!lang.equals("x") && !langCodes.contains(lang)){
        Message m = Message.builder("validator.foreign.invalidCode")
            .fromNode(n)
            .addNote("Tag " + n.getName() + " has an invalid @lang attribute")
            .addNote("@lang must contain a valid ISO 639-1 or 639-3 language code")
            .build();
        Log.addMessage(m);
      }
    }else{
      Message m = Message.builder("validator.foreign.missingCode")
          .fromNode(n)
          .addNote("Tag " + n.getName() + " is missing mandatory @lang attribute")
          .addNote("@lang must contain a valid ISO 639-1 or 639-3 language code")
          .build();
      Log.addMessage(m);
    }
  }

  public void validate(DOM dom) {
    for (Node n : dom) {
      if (n.getName().toLowerCase().equals("foreign") &&
          n.type() == NodeType.START){
            process_start((StartNode) n);
      }
    }
  }

}
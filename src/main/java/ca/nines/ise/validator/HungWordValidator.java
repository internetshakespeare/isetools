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

import java.util.ArrayDeque;

import ca.nines.ise.annotation.ErrorCode;
import ca.nines.ise.dom.DOM;
import ca.nines.ise.log.Log;
import ca.nines.ise.log.Message;
import ca.nines.ise.node.EmptyNode;
import ca.nines.ise.node.EndNode;
import ca.nines.ise.node.Node;
import ca.nines.ise.node.NodeType;
import ca.nines.ise.node.StartNode;
import ca.nines.ise.node.TextNode;
/**
 * 
 * Ensures HW tags are always used at the end of a line.
 * 
 * @author Malcolm Moran <malcolm.moran@outlook.com>
 */
public class HungWordValidator {
  Boolean after_hw;
  
  private void process_end(EndNode n) {
    if (n.getName().toLowerCase().equals("hw"))
      after_hw = true;
  }
  
  private void process_text(TextNode n) {
    if (n.getText().contains("\n") && n.getText().trim().equals(""))
      after_hw = false;
    else if (after_hw && !n.getText().trim().equals("")){
      Message m = Message.builder("validator.hungWord.endOfLine")
          .fromNode(n)
          .addNote("Text cannot follow an HW tag on the same line")
          .build();
      Log.addMessage(m);
    }
  }

  public void validate(DOM dom) {
    after_hw = false;
    
    for (Node n : dom) {
      switch (n.type()) {
        case END:
          process_end((EndNode) n);
          break;
        case TEXT:
          process_text((TextNode) n);
          break;
      }
    }
  }

}
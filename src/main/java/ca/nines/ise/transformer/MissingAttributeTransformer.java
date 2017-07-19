/*
 * Copyright (C) 2016 Malcolm Moran
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
package ca.nines.ise.transformer;

import java.io.IOException;

import ca.nines.ise.dom.DOM;
import ca.nines.ise.node.CharNode;
import ca.nines.ise.node.EndNode;
import ca.nines.ise.node.StartNode;
import ca.nines.ise.node.TextNode;
import ca.nines.ise.node.chr.AccentCharNode;
import ca.nines.ise.node.chr.DigraphCharNode;
import ca.nines.ise.node.chr.LigatureCharNode;
import ca.nines.ise.node.chr.NestedCharNode;
import ca.nines.ise.node.chr.TypographicCharNode;
import ca.nines.ise.node.chr.UnicodeCharNode;

/**
 * Adds mandatory attributes to tags if they are missing
 * and can be added programmatically
 *    -currently only affects @norm for SP tags
 *
 */
public class MissingAttributeTransformer extends IdentityTransform {
  private StartNode current_sp;
  
  public DOM transform(DOM dom) throws IOException {
    current_sp = null;
    return super.transform(dom);
  }
  
  public void text(TextNode n) {
    if (current_sp != null){
      String norm = current_sp.hasAttribute("norm")? current_sp.getAttribute("norm") : "";
      current_sp.setAttribute("norm", norm + n.getText());
    }
    dom.add(n);
  }
  
  private void charNode(CharNode n){
    if (current_sp != null){
      String norm = current_sp.hasAttribute("norm")? current_sp.getAttribute("norm") : "";
      current_sp.setAttribute("norm", norm + n.plain());
    }
    dom.add(n);
  }
  
  public void character(NestedCharNode n){
    charNode(n);
  }
  public void character(AccentCharNode n){
    charNode(n);
  }
  public void character(LigatureCharNode n){
    charNode(n);
  }
  public void character(UnicodeCharNode n){
    charNode(n);
  }
  public void character(DigraphCharNode n){
    charNode(n);
  }
  public void character(TypographicCharNode n){
    charNode(n);
  }
  
  public void start_sp(StartNode n) {
    current_sp = n.hasAttribute("norm")? null : n;
    dom.add(n);
  }
  public void end_sp(EndNode n) {
    current_sp = null;
    dom.add(n);
  }
}
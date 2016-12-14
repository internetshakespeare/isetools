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
import java.util.ArrayList;
import java.util.LinkedList;

import ca.nines.ise.dom.DOM;
import ca.nines.ise.node.AbbrNode;
import ca.nines.ise.node.CharNode;
import ca.nines.ise.node.CommentNode;
import ca.nines.ise.node.EmptyNode;
import ca.nines.ise.node.EndNode;
import ca.nines.ise.node.Node;
import ca.nines.ise.node.StartNode;
import ca.nines.ise.node.TextNode;

/**
 * Converts deprecated tags to correct tags
 *
 */
public class DeprecatedTransformer extends IdentityTransform {
  
  String last_tag;
  StartNode this_page;
  LinkedList<String> startStack;
  
  public DOM transform(DOM dom) throws IOException {
    last_tag = "";
    this_page = null;
    startStack = new LinkedList<String>();
    return super.transform(dom);
  }
  
  public void text(TextNode n) {
    //if the last tag was removed entirely, 
    //don't add the following whitespace
    if (n.getText().trim().equals("") && 
        (last_tag.toLowerCase().equals("link") || 
        last_tag.toLowerCase().equals("meta") || 
        last_tag.toLowerCase().equals("iseheader")))
      return;
    else
        dom.add(n);
  }
  
  private boolean end_tag(EndNode n){
    for (String s : startStack){
      if (s.toLowerCase().equals(n.getName().toLowerCase())){
        startStack.remove(s);
        return true;
      }
    }
    defaultTransform(n);
    return false;
  }
  
  @Override
  public void defaultTransform(Node n) {
    last_tag = n.getName();
    dom.add(n);
  }
  
  public void empty_link(EmptyNode n) {
    last_tag = n.getName();
    //dom.add(n);
  }
  
  public void empty_meta(EmptyNode n) {
    last_tag = n.getName();
    //dom.add(n);
  }

 
  public void empty_space(EmptyNode n) {
    last_tag = n.getName();
    String l = n.getAttribute("n");
	  n.deleteAttribute("n");
	  if (l != null)
	    n.setAttribute("l",l);
    dom.add(n);
  }

 
  public void end_blockquote(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("QUOTE");  
    dom.add(n);
  }
  
  
  public void end_fontgroup(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("FONT");
    dom.add(n);
  }

  
  public void end_h1(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void end_h2(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void end_h3(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void end_h4(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void end_h5(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("LD");
	  dom.add(n);
  }

  
  public void end_h6(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }
  
  
  public void end_iseheader(EndNode n) {
    last_tag = n.getName();
    //dom.add(n);
  }
  
  public void end_page(EndNode n){
    this_page = null;
    defaultTransform(n);
  }
  
  
  public void end_poem(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("DIV");
    dom.add(n);
  }

  
  public void end_prosequote(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("QUOTE");  
    dom.add(n);
    EndNode mode = new  EndNode("MODE");
    dom.add(mode);
  }

  
  public void end_section(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("DIV");
    dom.add(n);
  }
  
  public void end_sig(EndNode n){
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    dom.add(n);
  }
  
  public void end_titlehead(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("TITLE");
    dom.add(n);
  }

  public void end_titlepage(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("DIV");
    dom.add(n);
  }
  
  public void end_versequote(EndNode n) {
    if (!end_tag(n))
      return;
    last_tag = n.getName();
    n.setName("QUOTE");  
    dom.add(n);
    EndNode mode = new  EndNode("MODE");
    dom.add(mode);
  }

  
  public void start_blockquote(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("QUOTE");  		
    dom.add(n);
  }
  
  
  public void start_fontgroup(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("FONT");
    String size = n.getAttribute("n");
    n.deleteAttribute("n");
    if (size != null)
      n.setAttribute("size", size);
    dom.add(n);
  }
  
  
  public void start_h1(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void start_h2(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void start_h3(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void start_h4(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void start_h5(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }

  
  public void start_h6(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("LD");
    dom.add(n);
  }
  
  
  public void start_indent(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    String l = n.getAttribute("n");
    n.deleteAttribute("n");
    if (l != null)
      n.setAttribute("l",l);
    dom.add(n);
  }

  
  public void start_iseheader(StartNode n) {
    last_tag = n.getName();
    //dom.add(n);
  }

  
  public void start_linegroup(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    String form = n.getAttribute("form");
    n.deleteAttribute("form");
    if (form != null)
      n.setAttribute("t", form);
    dom.add(n);
  }
  
  public void start_page(StartNode n){
    this_page = n;
    defaultTransform(n);
  }
  
  public void start_poem(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("DIV");
    dom.add(n);
  }
  
  
  public void start_prosequote(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    StartNode en = new StartNode();
  	en.setName("MODE");
  	en.setAttribute("t", "prose");
  	dom.add(en);
  	n.setName("QUOTE");  
  	dom.add(n);
  }

  
  public void start_section(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
  	n.setName("DIV");
  	String name = n.getAttribute("n");
  	n.deleteAttribute("n");
  	if (name != null)
      n.setAttribute("name", name);
  	dom.add(n);
  }
  
  public void start_sig(StartNode n) {
    startStack.push(n.getName());
    if (this_page != null){
      last_tag = n.getName();
      String s = n.getAttribute("n");
      if (s != null){
        if (!this_page.hasAttribute("sig")){
          this_page.setAttribute("sig", s);
          n.deleteAttribute("n");
        }else if (this_page.getAttribute("sig").equals(s))
          n.deleteAttribute("n");
      }
    }
    defaultTransform(n);
  }
  
  public void start_titlehead(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
    n.setName("TITLE");
    dom.add(n);
  }
  
  public void start_titlepage(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
  	n.setName("DIV");
  	n.setAttribute("name", "Title page");
    dom.add(n);
  }

  public void start_versequote(StartNode n) {
    startStack.push(n.getName());
    last_tag = n.getName();
  	StartNode en = new StartNode();
  	en.setName("MODE");
  	en.setAttribute("t", "verse");
  	dom.add(en);
  	n.setName("QUOTE");  
    dom.add(n);
  }

}

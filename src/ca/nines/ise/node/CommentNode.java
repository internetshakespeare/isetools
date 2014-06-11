/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.nines.ise.node;

import ca.nines.ise.dom.Fragment;

/**
 *
 * @author Michael Joyce <ubermichael@gmail.com>
 */
public class CommentNode extends Node {

  public String type() {
    return "#COMMENT";
  }

  @Override
  public String getName() {
    return "#COMMENT";
  }

  @Override
  public Fragment expanded() {
    TextNode n = new TextNode();
    Fragment f = new Fragment();
    f.add(n);
    return f;
  }

  @Override
  public String plain() {
    return getText();
  }

  @Override
  public String unicode() {
    return getText();
  }

}

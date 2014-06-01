/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.nines.ise.node;

/**
 *
 * @author Michael Joyce <michael@negativespace.net>
 */
public class CommentNode extends Node {

  @Override
  String tagname() {
    return "#COMMENT";
  }

  @Override
  String expanded() {
    return getText();
  }

  @Override
  String plain() {
    return getText();
  }

  @Override
  String unicode() {
    return getText();
  }

}
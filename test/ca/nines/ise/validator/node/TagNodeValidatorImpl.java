/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.nines.ise.validator.node;

import ca.nines.ise.node.TagNode;
import ca.nines.ise.schema.Schema;

/**
 *
 * @author michael
 */
public class TagNodeValidatorImpl extends TagNodeValidator {

  public TagNodeValidatorImpl(Schema schema) {
    super(schema);
  }

  @Override
  public void validate(TagNode node) throws Exception {
    validate_attributes(node);
  }
}
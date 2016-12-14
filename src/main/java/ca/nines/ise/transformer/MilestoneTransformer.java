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

import ca.nines.ise.node.EmptyNode;
import ca.nines.ise.node.StartNode;

/**
 * Converts open milestone tags to self-closing
 * Removes closing milestone tags
 *
 */
public class MilestoneTransformer extends IdentityTransform {
  

  private void fix_start(StartNode n) {
    EmptyNode ms = new EmptyNode(n.getName());
    for(String a : n.getAttributeNames(true)){
      ms.setAttribute(a, n.getAttribute(a));
    }
    dom.add(ms);
  }
  
  public void start_l(StartNode n) {
    fix_start(n);
  }

  public void start_tln(StartNode n) {
    fix_start(n);
  }
  
  public void start_qln(StartNode n) {
    fix_start(n);
  }
  
  public void start_wln(StartNode n) {
    fix_start(n);
  }
  
  public void start_ms(StartNode n) {
    fix_start(n);
  }
}
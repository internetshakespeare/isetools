/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.nines.ise.node.chr;

import ca.nines.ise.dom.Fragment;
import ca.nines.ise.log.Log;
import ca.nines.ise.log.Message;
import ca.nines.ise.node.CharNode;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;

/**
 *
 * @author Michael Joyce <ubermichael@gmail.com>
 */
public class AccentCharNode extends CharNode {

  private static final HashMap<Character, String> charMap = new HashMap<>();
  static {
    charMap.put('^', "\u0302");
    charMap.put('"', "\u0308");
    charMap.put('\'', "\u0301");
    charMap.put('`', "\u0300");
    charMap.put('_', "\u0304");
    charMap.put('~', "\u0303");
  }

  /**
   * @return the charType
   */
  @Override
  public CharType getCharType() {
    return CharType.ACCENT;
  }

  @Override
  public Fragment expanded() {
    Fragment dom = new Fragment();
    char[] cs = super.innerText().toCharArray();
    
    String accent = charMap.get(cs[0]);
    if(accent == null) {
      accent = "\uFFFD";
      Message m = Log.getInstance().error("char.accent.unknown", this);
      m.addNote("Character " + text + " cannot be expanded.");      
    }    
    String str = cs[1] + accent;
    str = Normalizer.normalize(str, Form.NFC);
    return wrap("ACCENT", str);
  }

}

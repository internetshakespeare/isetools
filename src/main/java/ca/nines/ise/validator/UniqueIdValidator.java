package ca.nines.ise.validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.nines.ise.annotation.ErrorCode;
import ca.nines.ise.dom.DOM;
import ca.nines.ise.node.EndNode;
import ca.nines.ise.node.Node;
import ca.nines.ise.node.StartNode;

public class UniqueIdValidator {
    
    //<parent, list of nodes unique to it
    private static Map<String , List<String>> PARENT_MAP = new HashMap<String , List<String>>() {{
      put("act", Arrays.asList("scene"));
      put("scene", Arrays.asList("l"));
     }};
    //<node, list of IDs used so far
    private Map<String , List<String>> map;
  
    @ErrorCode(code = {
        "validator.uniqueId.multiple"
    })
    public void process_start(StartNode n){
      //name of unique attribute for this type of node
      String uniq = "test";
      
      //if the map contains an entry for n
      if (map.get(n.getName().toLowerCase()) != null){
        //if the id already exists for this node type
        if (map.get(n.getName().toLowerCase()).contains(n.getAttribute(uniq))){
          //then error
        } else
          //add id to map
          map.get(n.getName().toLowerCase()).add(n.getAttribute(uniq));
      }
      //otherwise add a new entry 
      else{
        map.put(n.getName().toLowerCase(), Arrays.asList(n.getAttribute(uniq)));
      }
    }
    
    public void process_end(EndNode n){
      
      List<String> nodes = PARENT_MAP.get(n.getName().toLowerCase());
      
      //if this node has any nodes that are unique to it
      if (nodes != null){
        //remove entries for those nodes from map
        for(String e : nodes)
          map.remove(e);
      }
    }
  
    public void validate(DOM dom) {
      map = new HashMap<String, List<String>>();
      
      for (Node n : dom) {
        switch (n.type()) {
          case START:
            process_start((StartNode) n);
            break;
          case END:
            process_end((EndNode) n);
            break;
        }
      }
    }
}

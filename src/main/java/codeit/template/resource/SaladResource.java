package codeit.template.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SaladResource {
   @RequestMapping(value = "/salad-spree",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> saladSpree(@RequestBody Map<String, Object> body) throws JSONException {
       Integer numSalads = Integer.parseInt(body.get("number_of_salads").toString());
       boolean changed = false;
       int price = Integer.MAX_VALUE;
       String jsonString = body.get("salad_prices_street_map").toString();
       JSONArray array = new JSONArray(jsonString);
       String[][] map = new String[array.length()][];

       for(int i = 0; i < array.length(); i++) {
           String[] x_coor = array.get(i).toString().replace("\"", "")
                   .replace("[", "")
                   .replace("]", "")
                   .split(",");
           map[i] = x_coor;
       }

       for (int i = 0; i < map.length; i++) {
           String[] currMap = map[i];

           for (int j = 0; j < currMap.length - numSalads; j++) {
               boolean hasX = false;
               int tempPrice = 0;

               for (int k = j; k < j + numSalads; k++) {
                   if (currMap[k].equals("X")) {
                       hasX = true;
                       j = j + k;
                       break;
                   } else {
                       int saladPrice = Integer.parseInt(currMap[k]);
                       tempPrice += saladPrice;
                   }
               }

               if (!hasX) {
                   if (tempPrice < price) {
                       price = tempPrice;
                       changed = true;
                   }
               }
           }
       }
       HashMap<String, Integer> resultMap = new HashMap<>();
       if (changed) {
           resultMap.put("result", price);
       } else {
           resultMap.put("result", 0);
       }

       return resultMap;
    }
}

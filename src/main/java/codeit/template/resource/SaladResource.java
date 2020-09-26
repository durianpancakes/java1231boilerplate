package codeit.template.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SaladResource {
    Logger logger = LoggerFactory.getLogger(SaladResource.class);

   @RequestMapping(value = "/salad-spree",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer calculateSquare(@RequestBody Map<String, String> body){
       Integer value = Integer.parseInt(body.get("number_of_salads"));
       Integer value2 = Integer.parseInt(body.get("salad_prices_street_map"));
       return value* value2;
    }
}

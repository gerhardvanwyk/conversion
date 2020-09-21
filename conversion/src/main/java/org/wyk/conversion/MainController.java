package org.wyk.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Controller
@RequestMapping("/conversions")
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    /**
     * Type Conversions
     */
    Map<String, Function<Double, Double>> lookup = new HashMap<>();
    {
        lookup.put("celsius-kelvin", ck -> ck + 273.15);
        lookup.put("kelvin-celsius", kv -> kv - 273.15);
        lookup.put("celsius-fahrenheit", cel -> (cel * 1.8) + 32);
        lookup.put("fahrenheit-celsius", fh -> (fh - 32)*1.8);
        lookup.put("kilogram-pound", kl -> kl/0.45359237);
        lookup.put("pound-kilogram", pd -> pd * 0.45359237 );
        lookup.put("mile-kilometer", ml -> ml * 1.609344 );
        lookup.put("kilometer-mile", kl -> kl/1.609344 );
        lookup.put("inch-centimeter", in -> in/0.39370);
        lookup.put("centimeter-inch", cm -> cm * 0.39370);

    }

    @Autowired
    ObjectMapper mapper;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(){
        return "index";
    }

    @GetMapping("/ktoc/{kelvin}")
    public ResponseEntity<String> getKelvinToCelsius(@PathVariable String kelvin){
        return convert("kelvin-celsius", kelvin);
    }

    @GetMapping("/ctok/{celsius}")
    public ResponseEntity<String> getCelsiusToKelvin(@PathVariable String celsius){
        return convert("celsius-kelvin", celsius);
    }

    @GetMapping("/mtok/{miles}")
    public ResponseEntity<String> getMileToKilometer(@PathVariable String miles){
        return convert("mile-kilometer", miles);
    }

    @GetMapping("/ktom/{kilometers}")
    public ResponseEntity<String> getKilometerToMile(@PathVariable String kilometers){
        return convert("kilometer-mile", kilometers);
    }

    /**
     * Returns all the conversions available
     * @return
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> conversions(){
        List<String> result = new ArrayList<>();
        for(String s: lookup.keySet()){
            String[] spl = StringUtils.split(s, "-");
            result.add(spl[0]);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Convert the 'selected' type
     * @param selected
     * @param value
     * @return
     */
    @GetMapping("/convert/{selected}/{value}")
    public ResponseEntity<String> convert(@PathVariable String selected, @PathVariable String value){
        Function func = lookup.get(selected);
        if(func == null){
            return new ResponseEntity<>( "Conversion not supported", HttpStatus.NO_CONTENT);
        }
        Double result;
        try {
            result = (Double) func.apply(Double.valueOf(value));
        }catch (NumberFormatException e){
            return new ResponseEntity("Value not a number " + value, HttpStatus.BAD_REQUEST);
        }
        log.debug(String.format("Convert: %s, value %s result %s", selected, value, result));
        return new ResponseEntity<>( Double.toString(result), HttpStatus.OK);
    }

}

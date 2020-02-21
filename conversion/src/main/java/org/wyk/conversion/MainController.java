package org.wyk.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    Map<String, Function<Double, Double>> lookup = new HashMap<>();
    {
        lookup.put("celsius-fahrenheit", cel -> (cel * 1.8) + 32);
        lookup.put("fahrenheit-celsius", fh -> (fh - 32)*1.8);
        lookup.put("kilogram-pound", kl -> kl/0.45359237);
        lookup.put("pound-kilogram", pd -> pd * 0.45359237 );
        lookup.put("inches-centimeters", in -> in/0.39370);
        lookup.put("centimeters-inches", cm -> cm * 0.39370);

    }

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(){
        return "index";
    }

    @GetMapping("/conversions")
    public ResponseEntity<List<String>> conversions(){
        List<String> result = new ArrayList<>();
        for(String s: lookup.keySet()){
            String[] spl = StringUtils.split(s, "-");
            result.add(spl[0]);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/convert/{selected}/{value}")
    public ResponseEntity<String> convert(@PathVariable String selected, @PathVariable String value){
        Function func = lookup.get(selected);
        if(func == null){
            return new ResponseEntity<>( "Conversion not supported", HttpStatus.NO_CONTENT);
        }

        Double result = (Double) func.apply(new Double(value));
        log.debug(String.format("Convert: %s, value %s result %s", selected, value, result));
        return new ResponseEntity<>( Double.toString(result), HttpStatus.OK);
    }

}

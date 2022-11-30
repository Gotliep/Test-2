package com.conversion.conversion;

import java.util.Collection;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnitConverterController {

    @GetMapping("/convert")
    public Convertor convertor(@RequestParam(value = "amount") String amount,@RequestParam(value = "from") String from,@RequestParam(value = "to") String to) {
        double d=Double.parseDouble(amount);
        return new Convertor(from, to, d);
    }

    @GetMapping("/getUnitTypes")
    public Collection<String> getUnitTypes(@RequestParam(value = "name") String name) {
        return UnitConverter.Unit.getByUnitType(name);
    }

    @GetMapping("/getTypes")
    public Collection<String> getTypes() {
        return UnitConverter.Unit.getByType();
    }

}

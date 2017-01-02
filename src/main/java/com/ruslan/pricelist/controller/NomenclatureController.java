package com.ruslan.pricelist.controller;

import com.ruslan.pricelist.beans.Nomenclature;
import com.ruslan.pricelist.service.NomenclatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ruslan on 12/28/2016.
 */
@Controller
@RequestMapping(value = "/nomenclature")
public class NomenclatureController {
    @Autowired
    NomenclatureService nomenService;

    @RequestMapping("/nomenclatureList.json")
    public @ResponseBody List<Nomenclature> getNomenclatureList() throws IOException {
        return nomenService.getNomenclatures();
    }

    @RequestMapping(value = "/addNomenclature", method=RequestMethod.POST)
    public @ResponseBody
    ResponseEntity addNomenclature(@RequestBody Nomenclature nomenclature){
        try{
            nomenService.addNomenclature(nomenclature);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Номенклатура успешно добавлено!",HttpStatus.OK);
    }

    @RequestMapping("/removeNomenclature")
    public @ResponseBody void removeNomenclature (@RequestBody Nomenclature nomenclature){
        nomenService.removeNomenclature(nomenclature);
    }

    @RequestMapping("/removeAllNomenclatures")
    public @ResponseBody void removeAllNomenclatures(){
        nomenService.removeAllNomenclatures();
    }

    @RequestMapping
    public String getNomenclaturePage(){
        return "index";
    }
}

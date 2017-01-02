package com.ruslan.pricelist.controller;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Nomenclature;
import com.ruslan.pricelist.service.FileStorageService;
import com.ruslan.pricelist.service.PriceComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ruslan on 12/28/2016.
 */
@Controller
@RequestMapping("/priceComparison")
public class PriceComparisonController {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    PriceComparisonService priceComparisonService;

    @RequestMapping
    public String getPriceComparisonPage(){
        return "priceComparison";
    }

    @RequestMapping(value="/getNomenclaturePrices", method= RequestMethod.POST)
    public @ResponseBody
    List<Distributor> getNomenclaturePrices(@RequestBody Nomenclature nomenclature){
        List<Distributor> distributors = fileStorageService.getAllDistributors();
        return priceComparisonService.getDistributorsPriceForItem(nomenclature.getName(),distributors);
    }

}

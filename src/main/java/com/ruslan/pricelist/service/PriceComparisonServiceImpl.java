package com.ruslan.pricelist.service;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Item;
import com.ruslan.pricelist.utility.StringComparisonUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 12/30/2016.
 */
@Service("priceComparisonService")
public class PriceComparisonServiceImpl implements PriceComparisonService {
    @Override
    public List<Distributor> getDistributorsPriceForItem(String nomenclatureName, List<Distributor> distributors) {
        List<Distributor> distributorsPrices = new ArrayList<>();
        for (Distributor distributor : distributors) {
            for (Item item : distributor.getPriceList()) {
                if(StringComparisonUtility.isItemsSame(nomenclatureName,item.getName())){
                    distributor.setItem(item);
                    distributorsPrices.add(distributor);
                    break;
                }
            }
        }
        // sort ascending order

        return sortAscendingOrder(distributorsPrices);
    }

    private List<Distributor> sortAscendingOrder(List<Distributor> distributors){
        distributors.sort((Distributor d1, Distributor d2)->d1.getItem().getPrice().intValue()-d2.getItem().getPrice().intValue());
        return distributors;
    }
}

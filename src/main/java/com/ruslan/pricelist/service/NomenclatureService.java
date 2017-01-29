package com.ruslan.pricelist.service;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Nomenclature;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ruslan on 12/28/2016.
 */
public interface NomenclatureService {
    public List<Nomenclature> getNomenclatures() throws IOException;

    public void addNomenclature(Nomenclature nomenclatureName);

    void removeNomenclature(Nomenclature nomenclature);

    void removeAllNomenclatures();

    void generateNomenclaturesByPriceLists(List<Distributor> allDistributors) throws Exception;

}

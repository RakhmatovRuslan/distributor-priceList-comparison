package com.ruslan.pricelist.service;

import com.ruslan.pricelist.beans.Nomenclature;
import com.ruslan.pricelist.utility.NomenclatureParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ruslan on 12/28/2016.
 */
@Service("nomeclatureService")
public class NomenclatureServiceImpl implements NomenclatureService {
    private List<Nomenclature> nomenclatures ;

    @Override
    public List<Nomenclature> getNomenclatures() throws IOException {
        nomenclatures = NomenclatureParser.getNomenclaturesFromFile();
        return nomenclatures;
    }

    @Override
    public void removeNomenclature(Nomenclature nomenclature) {
        try {
            NomenclatureParser.removeNomenclature(nomenclature.getName());
            nomenclatures.remove(nomenclature);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNomenclature(Nomenclature nomenclature) {
        if(nomenclatures.contains(nomenclature))
            throw new IllegalArgumentException("Такая номенклатура уже существует в списке!");
        try {
            NomenclatureParser.addNewNomenclature(nomenclature.getName());
            nomenclatures.add(nomenclature);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeAllNomenclatures() {
        try {
            NomenclatureParser.clearNomenclatures();
            nomenclatures.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

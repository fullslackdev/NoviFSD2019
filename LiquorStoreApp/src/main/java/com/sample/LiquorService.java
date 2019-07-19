package com.sample;

import com.sample.model.LiquorType;

import java.util.ArrayList;
import java.util.List;

public class LiquorService {

    public List getAvailableBrands(LiquorType type) {
        List<String> brands = new ArrayList<String>();

        if (type.equals(LiquorType.WINE)) {
            brands.add("Adrianne Vineyard");
            brands.add(("J. P. Chenet"));
            brands.add("Château Migraine");
        } else if (type.equals(LiquorType.WHISKY)) {
            brands.add("Glenfiddich");
            brands.add("Johnnie Walker");
            brands.add("Macallan");
        } else if (type.equals(LiquorType.BEER)) {
            brands.add("Corona");
            brands.add("Bavaria");
            brands.add("Brugse Zot");
            brands.add("Amstel");
        } else {
            brands.add("No Brand Available");
        }

        return brands;
    }
}

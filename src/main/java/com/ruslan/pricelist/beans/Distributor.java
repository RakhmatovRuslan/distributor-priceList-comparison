package com.ruslan.pricelist.beans;

import java.util.List;

/**
 * Created by Ruslan on 12/29/2016.
 */
public class Distributor {
    private Long id;
    private String name;
    private List<Item> priceList;
    private Item item;

    public Distributor() {
    }

    public Distributor(String name, List<Item> priceList) {
        this.name = name;
        this.priceList = priceList;
    }

    public Distributor(Long id, String name, List<Item> priceList) {
        this.id = id;
        this.name = name;
        this.priceList = priceList;
    }

    public Distributor(Long id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Item> priceList) {
        this.priceList = priceList;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceList=" + priceList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Distributor)) return false;

        Distributor that = (Distributor) o;

        if (!name.equals(that.name)) return false;
        return priceList.size() == that.priceList.size();

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + priceList.hashCode();
        return result;
    }
}

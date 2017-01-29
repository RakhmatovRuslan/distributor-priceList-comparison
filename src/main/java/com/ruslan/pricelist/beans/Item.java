package com.ruslan.pricelist.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruslan.pricelist.utility.StringComparisonUtility;

import java.util.Date;

/**
 * Created by Ruslan on 12/29/2016.
 */
public class Item {
    private String name;
    private Double price;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date expireDate;
    private String producer;

    public Item() {
    }

    public Item(String name, Double price, Date expireDate, String producer) {
        this.name = name;
        this.price = price;
        this.expireDate = expireDate;
        this.producer = producer;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", expireDate=" + expireDate +
                ", producer='" + producer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return StringComparisonUtility.isSame(name,item.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

package com.ruslan.pricelist.beans;

import com.ruslan.pricelist.utility.StringComparisonUtility;

/**
 * Created by Ruslan on 12/30/2016.
 */
public class Nomenclature {
    private Long id;
    private String name;

    public Nomenclature() {
    }

    public Nomenclature(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nomenclature)) return false;

        Nomenclature that = (Nomenclature) o;

        return StringComparisonUtility.isItemsSame(name,that.name);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Nomenclature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

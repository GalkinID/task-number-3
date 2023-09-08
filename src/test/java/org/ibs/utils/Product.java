package org.ibs.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Product<T> {
    private String name;
    private String type;
    private T exotic;
    public Product() {
    }

    public Product(String name, String type, T exotic) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public T getExotic() {
        return exotic;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", exotic=" + exotic +
                '}';
    }
}

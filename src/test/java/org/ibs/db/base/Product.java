package org.ibs.db.base;

public class Product<T> {
    private String name;
    private String type;
    private T isExotic;

    public Product(String name, String type, T isExotic) {
        this.name = name;
        this.type = type;
        this.isExotic = isExotic;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public T getIsExotic() {
        return isExotic;
    }
}

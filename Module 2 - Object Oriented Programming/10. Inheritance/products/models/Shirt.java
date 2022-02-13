package models;

import java.util.Objects;

public class Shirt extends Product {
    @Override
    public void fold() {
        System.out.println("Flat surface");
        System.out.println("Fold once");
        System.out.println("Fold twice");
        System.out.println("Done");
    }

    public enum Size{
        SMALL, MEDIUM, LARGE
    }
    private Size size;


    public Shirt(Size size, double price, String color, String brand) {
        super(price, color, brand);
        this.size = size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Shirt(Shirt source){
        super(source);
        this.size = source.size;
    }

    public Size getSize() {
        return size;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shirt)) return false;
        Shirt shirt = (Shirt) o;
        return size.equals(shirt.size)
                && super.getPrice() == shirt.getPrice()
                && super.getColor().equals(shirt.getColor())
                && super.getBrand().equals(shirt.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, super.getPrice(), super.getColor(), super.getBrand());
    }

    @Override
    public String toString() {
        return "Shirt{" +
                "size='" + size + '\'' +
                '}';
    }
}

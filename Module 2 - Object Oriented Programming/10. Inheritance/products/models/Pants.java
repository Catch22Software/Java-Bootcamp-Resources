package models;

import java.util.Objects;

public class Pants extends Product implements Discountable, Comparable<Pants>{

    private int waist;

    public Pants(int waist, double price, String color, String brand) {
        super(price,color,brand);
        this.waist = waist;
    }

    public Pants(Pants source){
        super(source);
        this.waist = source.waist;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pants)) return false;
        Pants pants = (Pants) o;
        return getWaist() == pants.getWaist()
                && super.getPrice() == pants.getPrice()
                && super.getColor().equals(pants.getColor())
                && super.getBrand().equals(pants.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWaist());
    }

    @Override
    public void fold() {
        System.out.println("Lay flat");
        System.out.println("Roll it");
        System.out.println("Pull one leg to side");
        System.out.println("FOld into side");
    }

    @Override
    public void discount() {
        super.setPrice(super.getPrice() / 2);
    }

    @Override
    public int compareTo(Pants specifiedObject) {
        return (int)Math.round(super.getPrice() - specifiedObject.getPrice());
    }
}
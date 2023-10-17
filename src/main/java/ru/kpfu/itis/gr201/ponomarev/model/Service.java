package ru.kpfu.itis.gr201.ponomarev.model;

import java.util.Objects;

public class Service {
    private int id;
    private String name;
    private int duration;
    private int price;

    public Service(int id, String name, int duration, int price) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " (" + duration + " minutes, " + price + " RUB)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return getId() == service.getId() && getDuration() == service.getDuration() && getPrice() == service.getPrice() && Objects.equals(getName(), service.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDuration(), getPrice());
    }
}

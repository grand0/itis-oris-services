package ru.kpfu.itis.gr201.ponomarev.model;

import java.util.Objects;

public class Master {
    private int id;
    private String name;
    private int experience_years;

    public Master(int id, String name, int experience_years) {
        this.id = id;
        this.name = name;
        this.experience_years = experience_years;
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

    public int getExperience_years() {
        return experience_years;
    }

    public void setExperience_years(int experience_years) {
        this.experience_years = experience_years;
    }

    @Override
    public String toString() {
        return name + " (" + experience_years + " years of exp.)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Master master = (Master) o;
        return getId() == master.getId() && getExperience_years() == master.getExperience_years() && Objects.equals(getName(), master.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getExperience_years());
    }
}

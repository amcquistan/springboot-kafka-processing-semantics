package com.thecodinginterface.kafkaprocessingsemantics.models;

public class Person {

    private String profession;
    private String name;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{ profession=" + profession + ", name=" + name + " }";
    }
}

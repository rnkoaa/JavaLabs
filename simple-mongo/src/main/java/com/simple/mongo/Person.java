package com.simple.mongo;

import java.util.UUID;


public record Person(UUID id, String name, int age, Address address, ItemId itemId) {
//  public class Person {
    /*@Id
    private UUID id;

    private String name;
    private int age;
    private Address address;
    private ItemId itemId;

    public Person() {
    }

    public Person(UUID id, String name, int age, Address address, ItemId itemId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.itemId = itemId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public ItemId getItemId() {
        return itemId;
    }

    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", itemId=" + itemId +
                '}';
    }*/

  // Rest of implementation
}

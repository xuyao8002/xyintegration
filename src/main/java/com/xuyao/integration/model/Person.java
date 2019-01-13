package com.xuyao.integration.model;

/**
 * Created by xuyao on 2018/9/5.
 */
//@Document(collection="haha")
public class Person {

    private String _id;
    private String name;

    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

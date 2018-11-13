package com.assignment.leaf.leaf;

import java.util.Comparator;

public class Model {
    String first,last,title,phone,thumbnail,medium,large,email,dob;
    int age;
    public Model(){}
    public Model(String first,String last,String title,String phone,
                 String thumbnail,String medium,String large,String email,String dob,int age){

        this.first=first;
        this.large=large;
        this.large=large;
        this.title=title;
        this.thumbnail=thumbnail;
        this.medium=medium;
        this.last=last;
        this.dob=dob;
        this.age=age;
        this.email=email;
        this.phone=phone;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }






    public static class NameComparator implements Comparator<Model> {

        public int compare(Model obj1, Model obj2) {
            return obj1.getFirst().compareTo(obj2.getFirst());
        }
    }

        public static class EmailComparator implements Comparator<Model> {

            public int compare(Model obj1, Model obj2) {
                return obj1.getEmail().compareTo(obj2.getEmail());
            }

        }
    public static class PhoneComparator implements Comparator<Model> {

        public int compare(Model obj1, Model obj2) {
            return obj1.getPhone().compareTo(obj2.getPhone());
        }

    }
    public static class DOBComparator implements Comparator<Model> {

        public int compare(Model obj1, Model obj2) {
            return obj1.getDob().compareTo(obj2.getDob());
        }

    }


}

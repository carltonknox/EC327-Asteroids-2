package com.example.asteroids_engine_test;

public class Person implements Comparable<Person> {
    String score;
    String name;

    public Person() {
        this.score = "temp";
        this.name = "tempp";
    }

    public int getScore() {
        try {
            return Integer.parseInt(score);
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setScore(String s) {
        this.score = s;
    }

    public void setName(String s) {
        this.name = s;
    }

    @Override

    public int compareTo(Person p) {
        int compareScore = ((Person)p).getScore();
        return compareScore-this.getScore();
    }
    //This is required for the Collections sort function to work


}

package edu.neu.csye6200.Model;

public abstract class Person
{
    protected String name;

    public Person(String name)
    {
        this.name = name;
    }

    public abstract String toCSV();

    @Override
    public String toString()
    {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
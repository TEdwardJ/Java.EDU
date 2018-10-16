package edu.luxoft.dbservice;

import edu.luxoft.dbservice.annotations.Column;
import edu.luxoft.dbservice.annotations.Id;
import edu.luxoft.dbservice.annotations.Table;

import java.lang.reflect.Field;

@Table
public class Employee extends Human{
    @Id
    private String inn;
    @Column
    private double salary;
    @Column(name="Employee_Name")
    private String name;
    @Column(name="employee_last_name")
    private String lastName;
    @Column
    private int sinceYear;
    @Column
    private char sex;

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSinceYear() {
        return sinceYear;
    }

    public void setSinceYear(int sinceYear) {
        this.sinceYear = sinceYear;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }
}

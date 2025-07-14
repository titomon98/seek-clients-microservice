package com.arturo.clients_microservice.dtos;

import java.time.LocalDate;

public class ClientDto {
    private String name;
    private String surname;
    private Integer age;
    private LocalDate birthday;
    private LocalDate lifeExpectation;
    private Integer remainingYears;

    public ClientDto(String name, String surname, Integer age, LocalDate birthday, LocalDate lifeExpectation, Integer remainingYears) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.birthday = birthday;
        this.lifeExpectation = lifeExpectation;
        this.remainingYears = remainingYears;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getLifeExpectation() {
        return lifeExpectation;
    }

    public void setLifeExpectation(LocalDate lifeExpectation) {
        this.lifeExpectation = lifeExpectation;
    }

    public Integer getRemainingYears() {
        return remainingYears;
    }

    public void setRemainingYears(Integer remainingYears) {
        this.remainingYears = remainingYears;
    }
}

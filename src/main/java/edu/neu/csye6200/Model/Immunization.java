package edu.neu.csye6200.Model;

import java.time.LocalDate;

public class Immunization
{

    private String vaccineType;
    private int dose;
    private LocalDate time;

    public Immunization(String vaccineType, int dose, LocalDate time)
    {
        this.vaccineType = vaccineType;
        this.dose = dose;
        this.time = time;
    }

    @Override
    public String toString()
    {
        return "Immunization{" +
                "vaccineType='" + vaccineType + '\'' +
                ", dose=" + dose +
                ", time=" + time +
                '}';
    }

    // ========= setters and getters ==========

    public String getVaccineType()
    {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType)
    {
        this.vaccineType = vaccineType;
    }

    public int getDose()
    {
        return dose;
    }

    public void setDose(int dose)
    {
        this.dose = dose;
    }

    public LocalDate getTime()
    {
        return time;
    }

    public void setTime(LocalDate time)
    {
        this.time = time;
    }
}
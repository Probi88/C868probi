package com.example.c868probi.Model;

/**
 * model class used for reports of reports within appointments page, this is for total and type report table
 * */
public class ReportType {

    private Integer total;

    private String monthType;

    /**
     * constructor for new reports object for total amount of appointments for each appointment type
     * @param total
     * @param monthType
     * */
    public ReportType(Integer total, String monthType) {
        this.total = total;
        this.monthType = monthType;
    }

    /**
     * sets total number
     * @param total
     * */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * sets type of appointment
     * @param monthType
     * */
    public void setMonthType(String monthType) {
        this.monthType = monthType;
    }

    /**
     * gets appointment type
     * @return monthType
     * */
    public String getMonthType() {
        return monthType;
    }

    /**
     * gets total number
     * @return total
     * */
    public Integer getTotal() {
        return total;
    }

}


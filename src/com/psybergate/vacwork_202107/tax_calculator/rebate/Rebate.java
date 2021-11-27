package com.psybergate.vacwork_202107.tax_calculator.rebate;

public abstract class Rebate {
    private String id;

    private String type;

    public Rebate(String id, String type) {
        setId(id);
        setType(type);
    }

    public abstract double calculateRebate();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

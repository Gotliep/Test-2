package com.conversion.conversion;


import com.conversion.conversion.UnitConverter.Unit;

public class Convertor {

    private Unit unit_from;
    private Unit unit_to;
    private double amount;
    private double answer;
    private String unit_type;

    public Unit getUnitFrom() {
        return unit_from;
    }
    public Unit getUnitTo() {
        return unit_to;
    }
    public double getAnswer() {
        return answer;
    }
    public double getAmount() {
        return amount;
    }
    public String getUnitType() {
        return unit_type;
    }

    public void setUnitFrom(String unit_from) {
        this.unit_from = Unit.getByName(unit_from);
    }

    public void setUnitTo(String unit_to) {
        this.unit_to = Unit.getByName(unit_to);
    }

    public Convertor(String unit_from,String unit_to,double amount) {
        this.setUnitFrom(unit_from);
        this.setUnitTo(unit_to);
        this.amount = amount;
        this.answer = UnitConverter.convert(amount, this.getUnitFrom(), this.getUnitTo());
    }

    public Convertor() {

    }

}
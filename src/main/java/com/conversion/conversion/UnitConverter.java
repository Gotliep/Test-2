package com.conversion.conversion;

import java.util.ArrayList;
import java.util.Collection;

public class UnitConverter {

    public enum Unit{

        //Temperature
        KELVIN(UnitType.TEMPERATURE,"K",UnitSystem.METRIC,"Kelvin"),
        FAHRENHEIT(UnitType.TEMPERATURE,"F",UnitSystem.IMPERIAL,"Fahrenheit",Unit.KELVIN, amount -> (5.0d/9.0d*(amount-32.0d)) + 273.0d, amount -> (9.0d/5.0d)*(amount-273.0d)+32.0d),
        CELSIUS(UnitType.TEMPERATURE, "C", UnitSystem.METRIC, "Celsius", Unit.KELVIN, amount -> amount + 273.15d, amount -> amount-273.15d),

        //MASS
        KILOGRAM(UnitType.MASS,"kg",UnitSystem.METRIC,"Kilogram"),
        OUNCE(UnitType.MASS, "oz",UnitSystem.IMPERIAL, "Ounce", Unit.KILOGRAM, 0.0283495d, 35.274d),
        POUND(UnitType.MASS,"lb",UnitSystem.IMPERIAL,"Pound", Unit.KILOGRAM, 0.453592d, 2.20462d),
        STONE(UnitType.MASS,"st",UnitSystem.IMPERIAL,"Stone", Unit.KILOGRAM, 6.35029d, 0.157473d),
        IMPERIAL_TON(UnitType.MASS,"tn", UnitSystem.IMPERIAL,"Imperial Tonne", Unit.KILOGRAM, 0.157473d, 0.000984207d),
        GRAM(UnitType.MASS,"g",UnitSystem.METRIC, "Gram", Unit.KILOGRAM, 0.001d, 1000.0d),
        TONNE(UnitType.MASS, "t", UnitSystem.METRIC, "Tonne", Unit.KILOGRAM, 1000.0d, 0.001d),

        //LENGTH
        METRE(UnitType.LENGTH,"m",UnitSystem.METRIC, "Metre"),
        INCH(UnitType.LENGTH,"in",UnitSystem.IMPERIAL,"Inch", Unit.METRE, 0.0254d, 39.3701d),
        FOOT(UnitType.LENGTH,"ft",UnitSystem.IMPERIAL,"Feet", Unit.METRE, 0.3048d, 3.28084d),
        YARD(UnitType.LENGTH,"yd",UnitSystem.IMPERIAL,"Yard", Unit.METRE, 0.9144d, 1.09361d),
        MILE(UnitType.LENGTH,"mi",UnitSystem.IMPERIAL,"Mile", Unit.METRE, 1609.34d, 0.000621371d),
        MILLIMETRE(UnitType.LENGTH,"mm",UnitSystem.METRIC,"Millimetre", Unit.METRE, 0.001d, 1000.0d),
        CENTIMETRE(UnitType.LENGTH,"cm",UnitSystem.METRIC, "Centimetre", Unit.METRE, 0.01d, 100.0d),
        KILOMETRE(UnitType.LENGTH,"km",UnitSystem.METRIC,"Kilometre", Unit.METRE, 1000.0d, 0.001d);



        static {
            KILOGRAM.equivalent = POUND;
            METRE.equivalent = YARD;

            FAHRENHEIT.equivalent = CELSIUS;
            CELSIUS.equivalent = FAHRENHEIT;

            OUNCE.equivalent = GRAM;
            POUND.equivalent = KILOGRAM;
            STONE.equivalent = KILOGRAM;
            IMPERIAL_TON.equivalent = TONNE;
            GRAM.equivalent = OUNCE;
            TONNE.equivalent = IMPERIAL_TON;

            INCH.equivalent = CENTIMETRE;
            FOOT.equivalent = CENTIMETRE;
            YARD.equivalent = METRE;
            MILE.equivalent = KILOMETRE;
            MILLIMETRE.equivalent = INCH;
            CENTIMETRE.equivalent = INCH;
            KILOMETRE.equivalent = MILE;
        }

        private UnitType type;
        private String unit;
        private UnitSystem system;
        private String name;
        private Unit reference;
        private Converter to;
        private Converter from;
        private boolean base;
        private Unit equivalent;

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         * @param multtoref the amount to multiply the amount with to convert it to the base unit
         */
        Unit(UnitType type, String unit, UnitSystem system, String name, Unit reference, double multtoref, double multfromref){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = reference;
            this.to = new MultiplicationConverter(multtoref);
            this.from = new MultiplicationConverter(multfromref);
            base = false;
        }

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         */
        Unit(UnitType type, String unit, UnitSystem system, String name, Unit reference, Converter converterto, Converter converterfrom){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = reference;
            this.to = converterto;
            this.from = converterfrom;
            base = false;
        }

        /**
         * Creates a new Base Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         */
        Unit(UnitType type, String unit, UnitSystem system, String name){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = null;
            this.to = null;
            this.from = null;
            base = true;
        }

        public UnitType getType(){
            return type;
        }

        public String getSymbol() {
            return unit;
        }

        public UnitSystem getSystem() {
            return system;
        }

        public String getName() {
            return name;
        }

        public Unit getReference() {
            return reference;
        }

        public Converter getTo() {
            return to;
        }

        public Converter getFrom() {
            return from;
        }

        public boolean isBase() {
            return base;
        }

        public static Collection<Unit> getByType(UnitType type){
            Collection<Unit> col = new ArrayList<>();
            for(Unit unit : Unit.values()){
                if(unit.getType().equals(type)) col.add(unit);
            }
            return col;
        }

        public static Collection<String> getByUnitType(String type){
            Collection<String> col = new ArrayList<>();
            for(Unit unit : Unit.values()){
                if(unit.getType().getName().equals(type)) col.add(unit.getName());
            }
            return col;
        }

        public static Collection<String> getByType(){
            Collection<String> col = new ArrayList<>();
            for(UnitType unit : UnitType.values()){
                col.add(unit.getName());
            }
            return col;
        }

        public static Unit getByName(String Name) {
            Unit return_unit = null;
            for(Unit unit : Unit.values()){
                if(unit.getName().equals(Name)) {
                    return_unit = unit;
                }
            }
            return return_unit;
        }


        public Unit getEquivalent() {
            return equivalent;
        }
    }

    public enum UnitType {
        TEMPERATURE("Temperature"),
        LENGTH("Length"),
        MASS("Mass");

        private String name;

        UnitType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum UnitSystem {
        IMPERIAL("Imperial"),
        METRIC("Metric");

        private String name;

        UnitSystem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    // METHODS
    /**
     * Convert a unit to another
     * @param amount the number
     * @param from the unit the number is in
     * @param to the unit the number should be converted to
     * @return the converted amount
     */
    public static double convert(double amount, Unit from, Unit to){

        if(!from.getType().equals(to.getType())){

        }

        double based;
        if(from.isBase()){
            based = amount;
        }
        else {
            based = from.getTo().convert(amount);
        }

        double result;
        if(to.isBase()){
            result = based;
        }
        else {
            result = to.getFrom().convert(based);
        }

        return result;

    }

    // CONVERTERS

    private interface Converter {
        double convert(double amount);
    }

    private static class MultiplicationConverter implements Converter{

        private double fac;

        public MultiplicationConverter(double factor){
            this.fac = factor;
        }

        @Override
        public double convert(double amount) {
            return amount*fac;
        }

    }
}
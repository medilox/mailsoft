package com.mailsoft.domain.enumerations;

public enum NatureCourrier {

    InterneDrfp("Interne DRFP"),
    InterneMinsante("Interne MINSANTE"),
    Externe("Externe");

    private final String value;

    NatureCourrier(String value) {
        this.value = value;
    }

    public static NatureCourrier fromValue(String value) {
        if (value != null) {
            for (NatureCourrier nc : values()) {
                if (nc.value.equals(value)) {
                    return nc;
                }
            }
        }

        // you may return a default value
        return getDefault();
        // or throw an exception
        // throw new IllegalArgumentException("Invalid reliure: " + value);
    }

    public String toValue() {
        return value;
    }

    public static NatureCourrier getDefault() {
    return InterneDrfp;
}

}
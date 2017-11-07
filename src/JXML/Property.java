package jxml;

/**
 * Created by daf28 on 6/25/2017.
 */

public class Property {
    private String stringValue, propertyName, propertyValue;
    public Property(String propertyName, String propertyValue) {
        this.stringValue = propertyName + "=\"" + propertyValue + '"';
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        //System.out.println("Property created: " + this.propertyName + ", " + this.propertyValue);
    }
    public String toString() {
        return this.stringValue;
    }
    public String getPropertyName() {
        return propertyName;
    }
    public String getPropertyValue() {
        return propertyValue;
    }
    public boolean equals(Property property) {
        if(this.propertyName.equals(property.getPropertyName()) && this.propertyValue.equals(property.getPropertyValue())) {
            return true;
        }
        return false;
    }
}

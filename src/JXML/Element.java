package jxml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daf28 on 6/25/2017.
 */

public class Element {
    private String stringValue, tagName, text;
    //private Property[] properties = new Property[10];
    private List<Property> properties = new ArrayList<>();
    private List<Element> children = new ArrayList<>();
    private Element parent;
    public void setTagName(String name) {
        this.tagName = name;
    }
    public void addChild(Element element) {
        children.add(element);
    }
    public String getTagName() {
        return this.tagName;
    }
    public String getText() {
        return text;
    }
    public void addProperty(Property p) {
        properties.add(p);
    }
    public boolean hasProperty(Property p) {

        if(properties.contains(p)) return true;

        return false;
    }
    public List<Property> getProperties() { return properties; }
    public List<Element> getChildren() { return children; }
    public Element getParent() { return parent; }

    public Element() {}
    public Element(Element parent) {
        this.parent = parent;
    }
}
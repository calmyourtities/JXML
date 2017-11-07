package jxml;

/**
 * Created by daf28 on 4/17/2017.
 */
public class Main {
    public static void main(String[] args) {
        JXMLFile myXML = new JXMLFile("my.xml");
        Document myXMLDoc = new Document(myXML);
        myXMLDoc.reallyParse();
        System.out.println();
        System.out.println(myXMLDoc.getContent());

        //Element el = myXMLDoc.getElementsByProperty(new Property("property", "\"1\"")).get(0);
        Element el = myXMLDoc.getAllElements().get(0);
        System.out.println(el.getTagName());
        System.out.println(el.getProperties().get(0).getPropertyName());
        System.out.println(el.getProperties().get(0).getPropertyValue());
        System.out.println(el.getText());

        //el = myXMLDoc.elementList.get(0).getChildren().get(0);
        el = el.getChildren().get(0);
        System.out.println(el.getTagName());
        System.out.println(el.getProperties().get(0).getPropertyName());
        System.out.println(el.getProperties().get(0).getPropertyValue());
        System.out.println(el.getText());

        //el = myXMLDoc.elementList.get(0).getChildren().get(1);
        el = myXMLDoc.getAllElements().get(0).getChildren().get(1);
        System.out.println(el.getTagName());
        System.out.println(el.getProperties().get(0).getPropertyName());
        System.out.println(el.getProperties().get(0).getPropertyValue());
        System.out.println(el.getText());
    }
}

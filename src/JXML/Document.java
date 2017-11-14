package jxml;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Danny
 * @see JXMLFile
 * @see Element
 * @see Property
 */

public class Document {
    private String content;
    private String log = "";
    private List<Element> elementList = new ArrayList<>();

    /**
     *
     * @param jxmlFile the file to use
     *
     */
    public Document(JXMLFile jxmlFile) {
        this.content = jxmlFile.getContent();
    }


    /**
     *
     * @param property the property to search for
     * @return a list of elements with the specified property
     */

    private void log(String s) {
        //System.out.println(s);
        log += s + '\n';
    }
    //public Element[] getAllElements() { return elements; }

    /**
     * @return all elements in the Document
     */
    public List<Element> getAllElements() { return elementList; }

    /*
     * @return the xml file as a string
     */
    public String getContent() {
        return content;
    }

    /**
     * Really parses the document. Like, actually does it. You know, really.
     */
    public void reallyParse() {

        Stack<Element> elementStack = new Stack<>();

        for(int i = 0; i < content.length(); i++) {

            Element currentElement = new Element();

            if(content.charAt(i) == '<' && content.charAt(i+1) != '/') {

                //System.out.println(i + ", " + content.charAt(i) +", 1");
                String elementName = "";
                i++;
                for(/* just use i */; content.charAt(i) != ' ' && content.charAt(i) != '>'; i++) {
                    //System.out.println(i + ", " + content.charAt(i) +", 2");
                    elementName += content.charAt(i);
                }
                currentElement.setTagName(elementName);

                //System.out.println("tag name: " + currentElement.getTagName());

                if(content.charAt(i) == ' ') { //TODO: IT ONLY WORKS WITH ONE PROPERTY
                    //System.out.println(i + ", " + content.charAt(i) +", 3");
                    i++;
                    String property[] = {"", ""}; //property[0] = name, property[1] = value
                    short on = 0;
                    while(true) {
                        //System.out.println(i + ", " + content.charAt(i) + ", 4");

                        if((content.charAt(i) == ' ' && on == 1) || content.charAt(i) == '>') {
                            on = 0;
                            currentElement.addProperty(new Property(property[0], property[1]));
                            property[0] = "";
                            property[1] = "";
                        }

                        if(content.charAt(i) == '>')
                            break;

                        if(content.charAt(i) == '=')
                            on = 1;
                        else
                            property[on] += content.charAt(i);

                        i++;
                    }
                }

                if(elementStack.size() > 0) {
                    elementStack.peek().addChild(currentElement);
                }

                elementStack.push(currentElement);
                elementList.add(currentElement);

            } else if(content.charAt(i) == '<' && content.charAt(i+1) == '/') {
                elementStack.pop();
            }

        }

    }

    /**
     * doesn't do anything
     * @link Document.reallyParse()
     */
    public void parse() {
        this.reallyParse();
    }

    public String getLog() {
        return log;
    }

    public Element getElementByTagName(String tagName) {
        Element[] els = (Element[]) elementList.toArray();

        for(Element el : els) {
            if(el.getTagName().equals(tagName))
                return el;
        }

        return null;
    }

    public List<Element> getElementsByProperty(Property property) {
        List<Element> toReturn = new ArrayList<>();
        for(int i = 0; i < elementList.size(); i++) {
            if(elementList.get(i).hasProperty(property)) {
                toReturn.add(elementList.get(i));
            }
        }
        return toReturn;
    }

    public List<Element> getElementsByPropertyName(String propertyName) {
        List<Element> toReturn = new ArrayList<>();
        for(int i = 0; i < elementList.size(); i++) {
            List<Property> properties = elementList.get(i).getProperties();
            for(int j = 0; j < properties.size(); j++) {
                if(properties.get(j).getPropertyName().equals(propertyName)){}
                    //TODO
            }
        }
        return toReturn;
    }
}

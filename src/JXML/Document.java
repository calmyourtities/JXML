package jxml;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by daf28 on 6/25/2017.
 */

public class Document {
    private String content;
    private String log = "";
    public Element[] elements = {};
    public List<Element> elementList = new ArrayList<>();
    public Document(JXMLFile m) {
        this.content = m.getContent();
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
    public void log(String s) {
        //System.out.println(s);
        log += s + '\n';
    }
    //public Element[] getAllElements() { return elements; }

    public List<Element> getAllElements() { return elementList; }

    public String getContent() {
        return content;
    }
    public Document parse2() {
        //TODO: make use of the elementStack to add children to elements
        log += "Parsing...\n";
        //TODO: let it handle any # of elements
        int elementsAmount = 0;
        Element[] elementStack = new Element[20]; //0 = most recent, 20 = oldest
        int elementStackAmount = 0;
        //Element prevElement = new Element();
        for(int i = 0; i < content.length(); i++) {
            if(content.charAt(i) == '<') {
                log += "\nFound a < at index: " + i;
                Element element = new Element();
                boolean rootElement;
                if(content.charAt(i + 1) != '/') {
                    log += "\nIt's an opening tag";
                    String elementContext = "";
                    while(content.charAt(i) != '>') {
                        elementContext += content.charAt(i);
                        i++;
                    }
                    String[] elementHandler = elementContext.split(" ");
                    Element el = new Element();
                    if(elementStack[0] != null) {
                        rootElement = false;
                        log += "\nThere are elements in the stack";
                        el.setTagName(elementHandler[0].split(elementHandler[0].charAt(0) + "", 2)[1]); //just removes the '<' from the beginning
                        for(int j = 1; j < elementHandler.length; j++) {
                            String[] propertyHandler = elementHandler[j].split("=");
                            Property property = new Property(propertyHandler[0], propertyHandler[1]);
                            el.addProperty(property);
                        }
                        log += "\nAdding " + el.getTagName() + " child to element " + elementStack[0].getTagName();
                        elementStack[0].addChild(el);
                    } else {
                        rootElement = true;
                        log +="There aren't elements in the stack";
                        elements[elementsAmount] = new Element();
                        elements[elementsAmount].setTagName(elementHandler[0].split(elementHandler[0].charAt(0) + "", 2)[1]); //just removes the '<' from the beginning
                        for(int j = 1; j < elementHandler.length; j++) {
                            String[] propertyHandler = elementHandler[j].split("=");
                            Property property = new Property(propertyHandler[0], propertyHandler[1]);
                            elements[elementsAmount].addProperty(property);
                        }
                        el = elements[elementsAmount];
                        elementsAmount++;
                    }

                    //time to push the element in the stack

                    for(int j = 0; j < elementStack.length; j++) {
                        if(elementStack[j] != null) {
                            elementStack[j] = elementStack[j + 1];
                        } else {
                            if(!rootElement) {
                                elementStack[0] = el;
                            } else {
                                elementStack[0] = elements[elementsAmount];
                            }
                            break;
                        }
                    }

                } else {
                    //TODO: handle ending element tag
                    log += "\nIt's an ending tag";

                    //time to pop it :]
                    //TODO: LOOOOOOOK! the problem is that things are being added to the stack and not being removed

                    log += "ElementStack:";
                    for(int j = 0; j < elementStack.length; j++) {
                        if(elementStack[j] != null) {
                            log += "\n" + elementStack[j].getTagName();
                        } else {
                            log += "\nIt's null";
                        }
                    }



                    log += "Popping " + elementStack[0].getTagName();

//                        for(/*int j = elementStack.length - 1; j > 0; j--*/int j = 0; j < elementStack.length - 1; j++) {
//                            try {
//                                elementStack[j] = elementStack[j - 1];
//                            } catch (Exception e) {
//                                log +="\nCaught 2");
//                                e.printStackTrace();
//                            }
//                        }

                    for(int j = 0; j < elementStack.length; j++) {
                        if(elementStack[j] != null) {
                            elementStack[j] = elementStack[j + 1];
                        }
                    }
                }
            }
        }
        return this;
    }

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
                    /*for(/* just use i *//*; content.charAt(i) != '>'; i++) {
                        //System.out.println(i + ", " + content.charAt(i) +", 4");
                        if(content.charAt(i) != '=') {
                            property[on] += content.charAt(i);
                        } else {
                            on = 1;
                        }

                        if(content.charAt(i) == ' ') {
                            currentElement.addProperty(new Property(property[0], property[1]));
                            on = 0;
                        }
                    }

                    property[1] = property[1].substring(1, property[1].length()-1);
                    //System.out.println("19, " + property[0] + ", " + property[1]);*/
                }

                if(elementStack.size() > 0) {
                    elementStack.peek().addChild(currentElement);
                }

                //System.out.println("pushing " + currentElement.getTagName());
                elementStack.push(currentElement);
                elementList.add(currentElement);

            } else if(content.charAt(i) == '<' && content.charAt(i+1) == '/') {

                //System.out.println(i + ", " + content.charAt(i) +", 57");
                //System.out.println("popping " + elementStack.peek().getTagName());

                elementStack.pop();

            }

        }

    }

    public void parse3() {
        int nth = 0;
        Stack<Element> elementStack = new Stack<>();

        while(true) {
            Element currentElement = new Element();
            int index = ordinalIndexOf(content, "<", nth);
            if(content.charAt(index+1) != '/') {
                int index2 = ordinalIndexOf(content, ">", nth);
                String innerArrows = content.substring(index, index2);
                String[] tokens = innerArrows.split(" ");
                currentElement.setTagName(tokens[0]);
                for (int i = 1; i < tokens.length; i++) {
                    String[] prop = tokens[i].split("=");
                    currentElement.addProperty(new Property(prop[0], prop[1]));
                }
            } else {

            }
        }
    }

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

    public void parse() {

        //ArrayList<Element> ElementStack = new  ArrayList<Element>();

        Stack<Element> ElementStack = new Stack();

        Element el = new Element();

        el.setTagName("taggg");

        ElementStack.push(el);

        //System.out.println(ElementStack.peek().getTagName());

        ElementStack.pop();

        for(int i = 0; i < content.length(); i++) {

            if(content.charAt(i) == '<') {

                if (content.charAt(i + 1) != '/') { // check for closing tag
                    Element currentElement = new Element();

                    String inBetweenArrows = "";

                    while (content.charAt(i) != '>') {
                        inBetweenArrows += content.charAt(i);
                        i++;
                    }

                    String[] elementHandler = inBetweenArrows.split(" ");

                    currentElement.setTagName(elementHandler[0]);

                    //add properties to the current element (start at 1 because 1 index is the tag name)
                    for (int j = 1; j < elementHandler.length - 2; j++) {

                        String[] propertyHandler = elementHandler[j].split("=");
                        currentElement.addProperty(new Property(propertyHandler[0], propertyHandler[1]));

                    }

                    ElementStack.push(currentElement);
                } else {
                    ElementStack.pop();
                }
            }
                    /*

                    i++;

                    String next = "";
                    Stack<String> propertyStack = new Stack<>(); //first is tag name, new are properties and property values

                    for(; content.charAt(i) != '>'; i++) { //(i is declared; check for end of tag; increment)


                        if(content.charAt(i) == ' ') { //it's a space, so skip to the next character, go to the next in the stack, clear next

                            log("pushing next: " + next);
                            propertyStack.push(next);
                            next = "";

                        } else if(content.charAt(i) == '/') { //check for self closing tag
                            //TODO: handle self-closing tags
                        } else {

                            log("concat " + content.charAt(i) + " to next: " + next);
                            next += content.charAt(i);

                        }

                    }

                    //now we have a stack with the tag name, properties, and values
                    Element currentElement = new Element();

                    //because of the order of the stack, we have to do the properties first, and set the tag name first

                    //System.out.println(propertyStack.pop());

                    while(propertyStack.size() > 2) {

                        currentElement.addProperty(new Property(propertyStack.pop(), propertyStack.pop()));

                    }

                    currentElement.setTagName(propertyStack.pop());

                }

                */

        }

        //get array from stack
        elements = new Element[ElementStack.size()];
        for(int i = 0; i < ElementStack.size(); i++) {
            elements[i] = ElementStack.pop();
        }

        //System.out.println(elements.length);

    }

    public String getLog() {
        return log;
    }
}

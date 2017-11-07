package jxml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by daf28 on 6/25/2017.
 */

public class JXMLFile {
    private String content;
    public JXMLFile(String filename) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line;

            if(!((line = br.readLine()).contains("<?xml"))) {
                sb.append(line);
            }

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            String everything = sb.toString();
            content = everything;
        } catch(IOException e) {
            System.out.println("JMXL File Read Error:");
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Document getDocument() {
        return new Document(this);
    }
    public String getContent() {
        return content;
    }
}
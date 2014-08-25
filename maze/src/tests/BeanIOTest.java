package tests;

import org.beanio.*;

import java.io.*;
import java.util.Map;

public class BeanIOTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // create a BeanIO StreamFactory
        StreamFactory factory = StreamFactory.newInstance();
        // load the mapping file from the working directory
        factory.load("../resources/bestiary.xml");

        // create a BeanReader to read from "input.csv"
        BeanReader in = factory.createReader("bestiary", new File("bestiaryinput.csv"));
        // create a BeanWriter to write to "output.csv"
        BeanWriter out = factory.createWriter("bestiary", new File("bestiaryoutput.csv"));

        Object record = null;

        // read records from "input.csv"
        while ((record = in.read()) != null) {

            // process each record
            if ("header".equals(in.getRecordName())) {
                Map<String,Object> header = (Map<String,Object>) record;
                System.out.println(header.get("fileDate"));
            }
            else if ("contact".equals(in.getRecordName())) {
                //Contact contact = (Contact) record;
                // process the contact...
            }
            else if ("trailer".equals(in.getRecordName())) {
                Integer recordCount = (Integer) record;
                System.out.println(recordCount + " contacts processed");
            }

            // write the record to "output.csv"
            out.write(record);
        }

        in.close();

        out.flush();
        out.close();

    }

}



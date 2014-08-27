package tests;

import maze.BestiaryBean;

import org.beanio.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class BeanIOTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // create a BeanIO StreamFactory
        StreamFactory factory = StreamFactory.newInstance();
        // load the mapping file from the working directory
        factory.load("resources/bestiary.xml");

        // create a BeanReader to read from "input.csv"
        BeanReader in = factory.createReader("bestiary", new File("resources/data/bestiary.csv"));
        // create a BeanWriter to write to "output.csv"
        //BeanWriter out = factory.createWriter("bestiary", new File("bestiaryoutput.csv"));

        Object record = null;
        ArrayList<BestiaryBean> monsters = new ArrayList<BestiaryBean>();

        // read records from "input.csv"
        while ((record = in.read()) != null) {
            //System.out.println(record);
            //System.out.println(in.getRecordName());
            // process each record
            if ("header".equals(in.getRecordName())) {
                Map<String,Object> header = (Map<String,Object>) record;
                System.out.println(header.get("fileDate"));
            }
            else if ("detail".equals(in.getRecordName())) {
                BestiaryBean monster = (BestiaryBean) record;
                // process the contact...
                monsters.add(monster);
                //System.out.println(monster.getName());
            }
            else if ("trailer".equals(in.getRecordName())) {
                Integer recordCount = (Integer) record;
                System.out.println(recordCount + " contacts processed");
            }



            // write the record to "output.csv"
            //out.write(record);
        }

        for (int i =0;i < monsters.size();i++) {
            System.out.println(monsters.get(i).getName());
        }

        in.close();

        //out.flush();
        //out.close();

    }

}



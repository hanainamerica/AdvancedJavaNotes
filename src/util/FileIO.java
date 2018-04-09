package util;

import exception.AutoException;
import model.Automobile;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;


public class FileIO {

    public void SerializeAuto(Automobile automobile) throws AutoException {
        try {

            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("TestAuto.ser"));
            os.writeObject(automobile);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new AutoException(6);
        }
    }

    public Automobile DeserializeAuto(String name) {
        Automobile automobile = new Automobile();
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(name));
            automobile = (Automobile) is.readObject();

        } catch (IOException ioe) {
            System.out.println("error reading file!");
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe){
            System.out.println("error loading Automotive!");
            cnfe.printStackTrace();
        }
        return automobile;
    }

    public String readCarType(String carType) throws AutoException{
        if (carType.trim().isEmpty()){
            throw new AutoException(7);
        }
        return carType;
    }

    public int readYear(String yearString) throws AutoException{
        int year;
        try{
            year = Integer.parseInt(yearString);
        }catch(NumberFormatException nfe){
            throw new AutoException(8);
        }
        return year;
    }

    public String readMake(String make) throws AutoException{
        if (make.trim().isEmpty()){    //trim() method returns a copy of the string
            throw new AutoException(1);
        }
        return make;
    }

    public String readModel(String model) throws AutoException{
        if (model.trim().isEmpty()){
            throw new AutoException(2);
        }
        return model;
    }

    public int readBaseprice(String priceString) throws AutoException{
        int baseprice;
        try{
            baseprice = Integer.parseInt(priceString);
        }catch(NumberFormatException nfe){
            throw new AutoException(3);
        }
        return baseprice;
    }

    public int readOptionSetSize(String optionSetSize) throws AutoException{
        int size;
        try{
            size = Integer.parseInt(optionSetSize);
        }catch(NumberFormatException nfe){
            throw new AutoException(4);
        }
        return size;
    }

    public String checkOption(String line) throws AutoException{

        if (line == null){
            throw new AutoException(5);
        }
        return line;
    }

    // Create object from text file
    public Automobile buildAutoObject(String filename){

        Automobile automobile = null;

        String carType = "";
        String make = "";
        String model = "";
        int year = 0;
        int baseprice = 0;
        int optionSetSize = 0;

        try{
            //Read the file with the given name
            BufferedReader buff = new BufferedReader(new FileReader(filename));

            //First line is the type name of the make, read it
            String firstline = buff.readLine();
            try{
                carType = readCarType(firstline);
            }catch (AutoException e){
                log(e.toString());
                carType = e.fix(e, e.getErrorNumber());
            }


            //Second line is the year of the make, read it
            String secondline = buff.readLine();
            try{
                year = readYear(secondline);
            }catch (AutoException e){
                log(e.toString());
                year = Integer.parseInt(e.fix(e, e.getErrorNumber()));
            }

            //Third line is the name of the make, read it
            String thirdline = buff.readLine();
            try{
                make = readMake(thirdline);
            }catch (AutoException e){
                log(e.toString());
                make = e.fix(e, e.getErrorNumber());
            }

            //Forth line is the name of the model, read it
            String forthline = buff.readLine();
            try{
                model = readModel(forthline);
            }catch (AutoException e){
                log(e.toString());
                model = e.fix(e, e.getErrorNumber());
            }

            //Fifth line is the baseprice of the model, read it
            String fifthline = buff.readLine();
            try{
                baseprice = readBaseprice(fifthline);
            }catch (AutoException e){
                log(e.toString());
                baseprice = Integer.parseInt(e.fix(e, e.getErrorNumber()));
            }

            automobile = new Automobile(carType, year, make, model, baseprice);

            //Sixth line is the optionSetSize of the model, read it
            String sixthline = buff.readLine();
            try{
                optionSetSize = readOptionSetSize(sixthline);
                automobile.setOptionSet(optionSetSize);
            }catch (AutoException e){
                log(e.toString());
                optionSetSize = Integer.parseInt(e.fix(e, e.getErrorNumber()));
            }

            //Seventh line has the sizes of all optionsets
            String seventhline = buff.readLine();
            StringTokenizer st = new StringTokenizer(seventhline);
            int index = 0;
            int[] size = new int[optionSetSize];
            int totalsize = 0;
            while (st.hasMoreTokens()) {
                size[index] = Integer.parseInt(st.nextToken());
                totalsize += size[index];
                automobile.setOption(automobile.getOpset(index),
                        size[index]);
                index++;
                if (index == optionSetSize) {
                    break;
                }
            }

            // the eighth line has the name of the optionsets
            int indexOption = 0;
            int indexMoveOn = 0;
            int indexTotal = 0;
            int indexPrices = 0;
            int[] prices = new int[totalsize];

            String eighthline = buff.readLine();
            StringTokenizer st2 = new StringTokenizer(eighthline);

            while (st2.hasMoreTokens()) {
                prices[indexPrices] = Integer.parseInt(st2.nextToken());
                indexPrices++;
            }

            for (int i = 0; i < totalsize; i++) {
                automobile.setOptionPrice(indexMoveOn, indexOption,
                        prices[i]);
                indexTotal++;
                indexMoveOn++;
                if (indexMoveOn == size[indexOption]) {
                    indexOption++;
                    indexMoveOn = 0;
                }
                if (indexTotal == totalsize) {
                    break;
                }
                if (indexOption == optionSetSize) {
                    break;
                }
            }

            String[] lines = new String[optionSetSize];
            int indexOptionSetName = 0;
            for (int i = 0; i < optionSetSize; i++) {
                lines[i] = buff.readLine();
                automobile.setOptionSetName(indexOptionSetName, lines[i]);
                indexOptionSetName++;
            }


            // The rest are the names of the options
            boolean eof = false;
            int indexOfValue = 0;
            String[] values = new String[totalsize];

            while (!eof) {
                String line = buff.readLine();
                if (line == null){
                    eof = true;
                } else{
                    try {
                        checkOption(line);
                    } catch (AutoException e) {
                        log(e.toString());
                        e.fix(e, e.getErrorNumber());
                    }

                    values[indexOfValue] = line;
                }
                indexOfValue++;
            }

            int index2 = 0;
            int index3 = 0;
            for (int i = 0; i < totalsize; i++) {
                automobile.setOptionValue(index3, index2, values[i]);
                index3++;
                if (index3 == size[index2]) {
                    index3 = 0;
                    index2++;
                }
            }

            buff.close();
        }catch (IOException e){
            System.out.println("Error-- " + e.toString());
        }
        return automobile;
    }

    public Automobile buildAutoObjectFromPropertiesObject(Properties props){
        Automobile automobile = new Automobile();
        String CarMake = props.getProperty("CarMake");

        if (CarMake!=null) {
            String CarType = props.getProperty("CarType");
            String CarYear = props.getProperty("CarYear");
            String CarModel = props.getProperty("CarModel");
            String CarBasePrice = props.getProperty("CarBasePrice");
            String OptionSetSize = props.getProperty("OptionSetSize");
            String OptionSet1 = props.getProperty("OptionSet1");
            String OptionValue1a = props.getProperty("OptionValue1a");
            String OptionValue1b = props.getProperty("OptionValue1b");
            String OptionValue1c = props.getProperty("OptionValue1c");
            String OptionValue1d = props.getProperty("OptionValue1d");
            String OptionValue1e = props.getProperty("OptionValue1e");
            String OptionValue1f = props.getProperty("OptionValue1f");
            String OptionValue1g = props.getProperty("OptionValue1g");
            String OptionValue1h = props.getProperty("OptionValue1h");
            String OptionValue1i = props.getProperty("OptionValue1i");
            String OptionValue1j = props.getProperty("OptionValue1j");
            String OptionSet2 = props.getProperty("OptionSet2");
            String OptionValue2a = props.getProperty("OptionValue2a");
            String OptionValue2b = props.getProperty("OptionValue2b");
            String OptionSet3 = props.getProperty("OptionSet3");
            String OptionValue3a = props.getProperty("OptionValue3a");
            String OptionValue3b = props.getProperty("OptionValue3b");
            String OptionValue3c = props.getProperty("OptionValue3c");
            String OptionSet4 = props.getProperty("OptionSet4");
            String OptionValue4a = props.getProperty("OptionValue4a");
            String OptionValue4b = props.getProperty("OptionValue4b");
            String OptionSet5 = props.getProperty("OptionSet5");
            String OptionValue5a = props.getProperty("OptionValue5a");
            String OptionValue5b = props.getProperty("OptionValue5b");

            String OptionPrice1a = props.getProperty("OptionPrice1a");
            String OptionPrice1b = props.getProperty("OptionPrice1b");
            String OptionPrice1c = props.getProperty("OptionPrice1c");
            String OptionPrice1d = props.getProperty("OptionPrice1d");
            String OptionPrice1e = props.getProperty("OptionPrice1e");
            String OptionPrice1f = props.getProperty("OptionPrice1f");
            String OptionPrice1g = props.getProperty("OptionPrice1g");
            String OptionPrice1h = props.getProperty("OptionPrice1h");
            String OptionPrice1i = props.getProperty("OptionPrice1i");
            String OptionPrice1j = props.getProperty("OptionPrice1j");
            String OptionPrice2a = props.getProperty("OptionPrice2a");
            String OptionPrice2b = props.getProperty("OptionPrice2b");
            String OptionPrice3a = props.getProperty("OptionPrice3a");
            String OptionPrice3b = props.getProperty("OptionPrice3b");
            String OptionPrice3c = props.getProperty("OptionPrice3c");
            String OptionPrice4a = props.getProperty("OptionPrice4a");
            String OptionPrice4b = props.getProperty("OptionPrice4b");
            String OptionPrice5a = props.getProperty("OptionPrice5a");
            String OptionPrice5b = props.getProperty("OptionPrice5b");

            automobile.setCarType(CarType);
            automobile.setYear(Integer.parseInt(CarYear));
            automobile.setMake(CarMake);
            automobile.setModel(CarModel);
            automobile.setBasePrice(Integer.parseInt(CarBasePrice));
            automobile.setOptionSet(Integer.parseInt(OptionSetSize));
            automobile.setOptionSetName(0, OptionSet1);
            automobile.setOptionSetName(1, OptionSet2);
            automobile.setOptionSetName(2, OptionSet3);
            automobile.setOptionSetName(3, OptionSet4);
            automobile.setOptionSetName(4, OptionSet5);

            automobile.setOption(automobile.getOpset(0), 10);
            automobile.setOptionPrice(0, 0, Integer.parseInt(OptionPrice1a));
            automobile.setOptionPrice(1, 0, Integer.parseInt(OptionPrice1b));
            automobile.setOptionPrice(2, 0, Integer.parseInt(OptionPrice1c));
            automobile.setOptionPrice(3, 0, Integer.parseInt(OptionPrice1d));
            automobile.setOptionPrice(4, 0, Integer.parseInt(OptionPrice1e));
            automobile.setOptionPrice(5, 0, Integer.parseInt(OptionPrice1f));
            automobile.setOptionPrice(6, 0, Integer.parseInt(OptionPrice1g));
            automobile.setOptionPrice(7, 0, Integer.parseInt(OptionPrice1h));
            automobile.setOptionPrice(8, 0, Integer.parseInt(OptionPrice1i));
            automobile.setOptionPrice(9, 0, Integer.parseInt(OptionPrice1j));

            automobile.setOptionValue(0, 0, OptionValue1a);
            automobile.setOptionValue(1, 0, OptionValue1b);
            automobile.setOptionValue(2, 0, OptionValue1c);
            automobile.setOptionValue(3, 0, OptionValue1d);
            automobile.setOptionValue(4, 0, OptionValue1e);
            automobile.setOptionValue(5, 0, OptionValue1f);
            automobile.setOptionValue(6, 0, OptionValue1g);
            automobile.setOptionValue(7, 0, OptionValue1h);
            automobile.setOptionValue(8, 0, OptionValue1i);
            automobile.setOptionValue(9, 0, OptionValue1j);

            automobile.setOption(automobile.getOpset(1),2);
            automobile.setOptionPrice(0, 1, Integer.parseInt(OptionPrice2a));
            automobile.setOptionPrice(1, 1, Integer.parseInt(OptionPrice2b));
            automobile.setOptionValue(0, 1, OptionValue2a);
            automobile.setOptionValue(1, 1, OptionValue2b);

            automobile.setOption(automobile.getOpset(2),3);
            automobile.setOptionPrice(0, 2, Integer.parseInt(OptionPrice3a));
            automobile.setOptionPrice(1, 2, Integer.parseInt(OptionPrice3b));
            automobile.setOptionPrice(2, 2, Integer.parseInt(OptionPrice3c));
            automobile.setOptionValue(0, 2, OptionValue3a);
            automobile.setOptionValue(1, 2, OptionValue3b);
            automobile.setOptionValue(2, 2, OptionValue3c);

            automobile.setOption(automobile.getOpset(3),2);
            automobile.setOptionPrice(0, 3, Integer.parseInt(OptionPrice4a));
            automobile.setOptionPrice(1, 3, Integer.parseInt(OptionPrice4b));
            automobile.setOptionValue(0, 3, OptionValue4a);
            automobile.setOptionValue(1, 3, OptionValue4b);

            automobile.setOption(automobile.getOpset(4),2);
            automobile.setOptionPrice(0, 4, Integer.parseInt(OptionPrice5a));
            automobile.setOptionPrice(1, 4, Integer.parseInt(OptionPrice5b));
            automobile.setOptionValue(0, 4, OptionValue5a);
            automobile.setOptionValue(1, 4, OptionValue5b);
        }

        return automobile;

    }



    public Automobile buildAutoObjectFromPropertiesFile(String filename) throws IOException {
        Automobile automobile = new Automobile();
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(filename);
        props.load(in);
        String CarMake = props.getProperty("CarMake");

        if (CarMake!=null) {
            String CarType = props.getProperty("CarType");
            String CarYear = props.getProperty("CarYear");
            String CarModel = props.getProperty("CarModel");
            String CarBasePrice = props.getProperty("CarBasePrice");
            String OptionSetSize = props.getProperty("OptionSetSize");
            String OptionSet1 = props.getProperty("OptionSet1");
            String OptionValue1a = props.getProperty("OptionValue1a");
            String OptionValue1b = props.getProperty("OptionValue1b");
            String OptionValue1c = props.getProperty("OptionValue1c");
            String OptionValue1d = props.getProperty("OptionValue1d");
            String OptionValue1e = props.getProperty("OptionValue1e");
            String OptionValue1f = props.getProperty("OptionValue1f");
            String OptionValue1g = props.getProperty("OptionValue1g");
            String OptionValue1h = props.getProperty("OptionValue1h");
            String OptionValue1i = props.getProperty("OptionValue1i");
            String OptionValue1j = props.getProperty("OptionValue1j");
            String OptionSet2 = props.getProperty("OptionSet2");
            String OptionValue2a = props.getProperty("OptionValue2a");
            String OptionValue2b = props.getProperty("OptionValue2b");
            String OptionSet3 = props.getProperty("OptionSet3");
            String OptionValue3a = props.getProperty("OptionValue3a");
            String OptionValue3b = props.getProperty("OptionValue3b");
            String OptionValue3c = props.getProperty("OptionValue3c");
            String OptionSet4 = props.getProperty("OptionSet4");
            String OptionValue4a = props.getProperty("OptionValue4a");
            String OptionValue4b = props.getProperty("OptionValue4b");
            String OptionSet5 = props.getProperty("OptionSet5");
            String OptionValue5a = props.getProperty("OptionValue5a");
            String OptionValue5b = props.getProperty("OptionValue5b");

            String OptionPrice1a = props.getProperty("OptionPrice1a");
            String OptionPrice1b = props.getProperty("OptionPrice1b");
            String OptionPrice1c = props.getProperty("OptionPrice1c");
            String OptionPrice1d = props.getProperty("OptionPrice1d");
            String OptionPrice1e = props.getProperty("OptionPrice1e");
            String OptionPrice1f = props.getProperty("OptionPrice1f");
            String OptionPrice1g = props.getProperty("OptionPrice1g");
            String OptionPrice1h = props.getProperty("OptionPrice1h");
            String OptionPrice1i = props.getProperty("OptionPrice1i");
            String OptionPrice1j = props.getProperty("OptionPrice1j");
            String OptionPrice2a = props.getProperty("OptionPrice2a");
            String OptionPrice2b = props.getProperty("OptionPrice2b");
            String OptionPrice3a = props.getProperty("OptionPrice3a");
            String OptionPrice3b = props.getProperty("OptionPrice3b");
            String OptionPrice3c = props.getProperty("OptionPrice3c");
            String OptionPrice4a = props.getProperty("OptionPrice4a");
            String OptionPrice4b = props.getProperty("OptionPrice4b");
            String OptionPrice5a = props.getProperty("OptionPrice5a");
            String OptionPrice5b = props.getProperty("OptionPrice5b");

            automobile.setCarType(CarType);
            automobile.setYear(Integer.parseInt(CarYear));
            automobile.setMake(CarMake);
            automobile.setModel(CarModel);
            automobile.setBasePrice(Integer.parseInt(CarBasePrice));
            automobile.setOptionSet(Integer.parseInt(OptionSetSize));
            automobile.setOptionSetName(0, OptionSet1);
            automobile.setOptionSetName(1, OptionSet2);
            automobile.setOptionSetName(2, OptionSet3);
            automobile.setOptionSetName(3, OptionSet4);
            automobile.setOptionSetName(4, OptionSet5);

            automobile.setOption(automobile.getOpset(0), 10);
            automobile.setOptionPrice(0, 0, Integer.parseInt(OptionPrice1a));
            automobile.setOptionPrice(1, 0, Integer.parseInt(OptionPrice1b));
            automobile.setOptionPrice(2, 0, Integer.parseInt(OptionPrice1c));
            automobile.setOptionPrice(3, 0, Integer.parseInt(OptionPrice1d));
            automobile.setOptionPrice(4, 0, Integer.parseInt(OptionPrice1e));
            automobile.setOptionPrice(5, 0, Integer.parseInt(OptionPrice1f));
            automobile.setOptionPrice(6, 0, Integer.parseInt(OptionPrice1g));
            automobile.setOptionPrice(7, 0, Integer.parseInt(OptionPrice1h));
            automobile.setOptionPrice(8, 0, Integer.parseInt(OptionPrice1i));
            automobile.setOptionPrice(9, 0, Integer.parseInt(OptionPrice1j));

            automobile.setOptionValue(0, 0, OptionValue1a);
            automobile.setOptionValue(1, 0, OptionValue1b);
            automobile.setOptionValue(2, 0, OptionValue1c);
            automobile.setOptionValue(3, 0, OptionValue1d);
            automobile.setOptionValue(4, 0, OptionValue1e);
            automobile.setOptionValue(5, 0, OptionValue1f);
            automobile.setOptionValue(6, 0, OptionValue1g);
            automobile.setOptionValue(7, 0, OptionValue1h);
            automobile.setOptionValue(8, 0, OptionValue1i);
            automobile.setOptionValue(9, 0, OptionValue1j);

            automobile.setOption(automobile.getOpset(1),2);
            automobile.setOptionPrice(0, 1, Integer.parseInt(OptionPrice2a));
            automobile.setOptionPrice(1, 1, Integer.parseInt(OptionPrice2b));
            automobile.setOptionValue(0, 1, OptionValue2a);
            automobile.setOptionValue(1, 1, OptionValue2b);

            automobile.setOption(automobile.getOpset(2),3);
            automobile.setOptionPrice(0, 2, Integer.parseInt(OptionPrice3a));
            automobile.setOptionPrice(1, 2, Integer.parseInt(OptionPrice3b));
            automobile.setOptionPrice(2, 2, Integer.parseInt(OptionPrice3c));
            automobile.setOptionValue(0, 2, OptionValue3a);
            automobile.setOptionValue(1, 2, OptionValue3b);
            automobile.setOptionValue(2, 2, OptionValue3c);

            automobile.setOption(automobile.getOpset(3),2);
            automobile.setOptionPrice(0, 3, Integer.parseInt(OptionPrice4a));
            automobile.setOptionPrice(1, 3, Integer.parseInt(OptionPrice4b));
            automobile.setOptionValue(0, 3, OptionValue4a);
            automobile.setOptionValue(1, 3, OptionValue4b);

            automobile.setOption(automobile.getOpset(4),2);
            automobile.setOptionPrice(0, 4, Integer.parseInt(OptionPrice5a));
            automobile.setOptionPrice(1, 4, Integer.parseInt(OptionPrice5b));
            automobile.setOptionValue(0, 4, OptionValue5a);
            automobile.setOptionValue(1, 4, OptionValue5b);
        }
        return automobile;
    }

    // log() method will write an exception information in a text file
    public void log(String message){
        try {
            BufferedWriter buffer = new BufferedWriter( new FileWriter("log.txt"));

            Date date = new Date();
            buffer.write("[" + date.toString() + "] " + message);
            buffer.newLine();
            buffer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

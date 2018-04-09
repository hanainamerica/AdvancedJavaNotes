package exception;

import java.io.*;

/**
 * To fix exceptions, we created AutoException class that extends exception.
 *
 */

public class AutoException extends Exception{

    private int errorNumber;
    private String errorMessage;
    private static String[] exceptionList = new String[100];

    public AutoException(int errorNumber) {
        super();
        readFile("exceptions.txt");
        this.errorNumber = errorNumber;
        this.errorMessage = exceptionList[errorNumber];
    }

    public AutoException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public AutoException(int errorNumber, String errorMessage) {
        super();
        this.errorNumber = errorNumber;
        this.errorMessage = errorMessage;
    }

    public void readFile(String fileName){
        boolean eof = false;
        int i = 1;
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            while(!eof) {
                String line = buffer.readLine();
                if(line == null) {
                    eof = true;
                } else {
                    String[] lineArray = line.split(",");
                    exceptionList[i] = lineArray[1];
                    i++;
                }
            }
            buffer.close();
        } catch(IOException ioe) {
            System.out.println("exception's file was not found");
        }
    }

    public int getErrorNumber(){
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber){
        this.errorNumber = errorNumber;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }


    public String fix(AutoException e, int errorNumber){
        Fix1to100 f1 = new Fix1to100();
        String s1 = null;
        switch (errorNumber){
            case 1: s1 =f1.fixMissingMakeName();break;
            case 2: s1 =f1.fixMissingModelName();break;
            case 3: s1 =f1.fixMissingAutoPrice();break;
            case 4: s1 =f1.fixMissingOptionSetSize();break;
            case 5: s1 =f1.fixMissingOption();break;
            case 6: f1.fixSerialization();break;
            case 7: s1 =f1.fixMissingAutoType();break;
            case 8: s1 =f1.fixMissingAutoYear();break;

        }
        return s1;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("errorNumber=");
        builder.append(errorNumber);
        builder.append(", errorMessage: \"");
        builder.append(errorMessage);
        builder.append("\"");
        return builder.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }




}


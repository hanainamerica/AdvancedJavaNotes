package model;

import java.io.Serializable;

public class Option implements Serializable{

    private static final long serialVersionUID = 3273899413985094659L;
    private String name;
    private int price;

    // constructor
    protected Option() { }                             // Create the constructor

    // getters
    protected String getName() {return name;}                 // Getter of the field name

    protected int getPrice() {return price;}                  // Getter of the field price

    //setters
    protected void setName(String name) {this.name = name;}   // Setter of the field name

    protected void setPrice(int price) {this.price = price;}     // Setter of the field price

    // Print all the information of an option
    protected void print() {
        StringBuffer sb = new StringBuffer();
        sb.append("The option name is: ");
        sb.append(name);
        sb.append("\nThe Price is: ");
        sb.append(price);
        System.out.println(sb);
    }
}

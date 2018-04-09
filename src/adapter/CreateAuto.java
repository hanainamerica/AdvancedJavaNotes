package adapter;

/**
 * The CreateAuto interface is made to build the automobile and print the info
 *
 */
public interface CreateAuto {

    public abstract void buildAuto(String filename, String filetype);     // BuildAuto creates an instance of Automobile from a text file

    public abstract void printAuto(String modelname);                     // PrintAuto prints the properties of a given Auto model


}

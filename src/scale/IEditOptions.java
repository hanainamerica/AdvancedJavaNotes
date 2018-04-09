package scale;

/**
 * IEditOptions interface which can edit the name and the price of the option
 */

public interface IEditOptions {

    public abstract void editOptionPrice(String modelName, String optionSetname,
                                String optionName, int newPrice);

    public abstract void editOptionSetName(String modelName,
                               String optionSetname, String newOptionSetname);



}

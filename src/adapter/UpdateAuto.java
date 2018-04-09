package adapter;

/**
 * The UpdateAuto interface has two method that can update the properties of optionsets and options
 *
 */
public interface UpdateAuto {


    // Search the Model for a given OptionSet, set the name to new name
    public abstract void updateOptionSetName(String modelname,
                                    String optionsetname, String newname);

    // Search the Model for a given OptionSet and Option, set the price to new price
    public abstract void updateOptionPrice(String modelname, String optionsetname,
                                  String optionname, int newprice);
}

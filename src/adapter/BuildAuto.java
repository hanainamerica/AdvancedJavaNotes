package adapter;


import scale.IEditOptions;
import server.AutoServer;

/**
 *
 * BuildAuto is empty but it extends ProxyAutomobile,
 * and implements the CreateAuto, UpdateAuto, FixAuto, IEditOptions, AutoServer.
 *
 * Through BuildAuto class, we can use the functionality of all the interfaces it implements:
 * CreateAuto, UpdateAuto, and FixAuto classes.
 *
 *
 * (inheritance, interface)
 *
 */

public class BuildAuto
        extends ProxyAutomobile implements CreateAuto, UpdateAuto, FixAuto, IEditOptions, AutoServer{ }

package com.globalpaysolutions.yocomprorecarga.models;

import java.util.HashMap;

/**
 * Created by Josué Chávez on 07/04/2017.
 */

public class RequirementsAR
{
    private HashMap<Integer, String> components;
    private boolean compatible;

    public RequirementsAR()
    {

    }

    public RequirementsAR(HashMap<Integer, String> components, boolean compatible)
    {
        this.components = components;
        this.compatible = compatible;
    }

    public HashMap<Integer, String> getComponents()
    {
        return components;
    }

    public boolean isCompatible()
    {
        return compatible;
    }

    public void setComponents(HashMap<Integer, String> components)
    {
        this.components = components;
    }

    public void setCompatible(boolean compatible)
    {
        this.compatible = compatible;
    }

}

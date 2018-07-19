package com.globalpaysolutions.yocomprorecarga.models;

import java.util.HashSet;
import java.util.Set;

public class SponsorsArray
{
    private HashSet<SponsorItem> array;

    public Set<SponsorItem> getArray()
    {
        return array;
    }

    public void setArray(HashSet<SponsorItem> array)
    {
        this.array = array;
    }
}

package com.nfkj.model.hardware;

import com.nfkj.basic.constans.Constants;
import com.nfkj.basic.util.NumberUtil;



public class MobileCategory
{
    protected int categoryId = Constants.VALUE_NOT_SET;

    protected String name;
    protected String checkedColor;

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCheckedColor()
    {
        return checkedColor;
    }

    public int getCheckedColorIntValue()
    {
        return NumberUtil.getColorIntValueByHexString(getCheckedColor());
    }

    public void setCheckedColor(String checkedColor)
    {
        this.checkedColor = checkedColor;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + categoryId;
        result = prime * result + ((checkedColor == null) ? 0 : checkedColor.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MobileCategory other = (MobileCategory) obj;
        if (categoryId != other.categoryId)
            return false;
        if (checkedColor == null)
        {
            if (other.checkedColor != null)
                return false;
        }
        else if (!checkedColor.equals(other.checkedColor))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return name;
    }

}

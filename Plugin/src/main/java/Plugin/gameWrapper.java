package Plugin;

import java.io.Serializable;

public class gameWrapper implements Serializable
{
    private byte[] object;
    private String label;

    public gameWrapper(byte[] object, String label)
    {
        this.object = object;
        this.label = label;
    }

    public byte[] getObject()
    {
        return object;
    }

    public void setObject(byte[] object)
    {
        this.object = object;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

}

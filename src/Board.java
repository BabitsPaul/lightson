import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Random;

public class Board
{
    public static final String PROPERTY_CELL = "cell";

    private static final double ON_FACTOR = 0.5;

    private PropertyChangeSupport propertySupport;

    private int size;

    private boolean[] field;

    public Board(int size)
    {
        this.size = size;

        field = new boolean[size * size];
        propertySupport = new PropertyChangeSupport(this);
    }

    public void trigger(int x, int y) {
        int index = x * size + y;

        field[index] = !field[index];

        propertySupport.fireIndexedPropertyChange("cell", index, !field[index], field[index]);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener l)
    {
        propertySupport.addPropertyChangeListener(property, l);
    }

    public void randomize()
    {
        Arrays.fill(field, false);

        Random rnd = new Random();
        for(int i = 0; i < size * size * ON_FACTOR; i++)
        {
            field[rnd.nextInt(size * size)] = true;
            propertySupport.fireIndexedPropertyChange("cell", i, !field[i], field[i]);
        }

    }
}

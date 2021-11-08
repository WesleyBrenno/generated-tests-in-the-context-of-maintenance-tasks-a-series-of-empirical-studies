package collections.comparators;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class FixedOrderComparator implements Comparator {
    public static final int UNKNOWN_BEFORE = 0;
    public static final int UNKNOWN_AFTER = 1;
    public static final int UNKNOWN_THROW_EXCEPTION = 2;
    private final Map map = new HashMap();
    private int counter = 0;
    private boolean isLocked = false;
    private int unknownObjectBehavior = UNKNOWN_THROW_EXCEPTION;
    public FixedOrderComparator() {
        super();
    }
    public FixedOrderComparator(Object[] items) {
        super();
        if (items == null) {
            throw new IllegalArgumentException("The list of items must not be null");
        }
        for (int i = 0; i < items.length; i++) {
            add(items[i]);
        }
    }
    public FixedOrderComparator(List items) {
        super();
        if (items == null) {
            throw new IllegalArgumentException("The list of items must not be null");
        }
        for (Iterator it = items.iterator(); it.hasNext();) {
            add(it.next());
        }
    }
    public boolean isLocked() {
        return isLocked;
    }
    protected void checkLocked() {
        if (isLocked()) {
            throw new UnsupportedOperationException("Cannot modify a FixedOrderComparator after a comparison");
        }
    }
    public int getUnknownObjectBehavior() {
        return unknownObjectBehavior;
    }
    public void setUnknownObjectBehavior(int unknownObjectBehavior) {
        checkLocked();
        if (unknownObjectBehavior != UNKNOWN_AFTER 
            && unknownObjectBehavior != UNKNOWN_BEFORE 
            && unknownObjectBehavior != UNKNOWN_THROW_EXCEPTION) {
            throw new IllegalArgumentException("Unrecognised value for unknown behaviour flag");    
        }
        this.unknownObjectBehavior = unknownObjectBehavior;
    }
    public boolean add(Object obj) {
        checkLocked();
        Object position = map.put(obj, new Integer(counter++));
        return (position == null);
    }
    public boolean addAsEqual(Object existingObj, Object newObj) {
        checkLocked();
        Integer position = (Integer) map.get(existingObj);
        if (position == null) {
            throw new IllegalArgumentException(existingObj + " not known to " + this);
        }
        Object result = map.put(newObj, position);
        return (result == null);
    }
    public int compare(Object obj1, Object obj2) {
        isLocked = true;
        Integer position1 = (Integer) map.get(obj1);
        Integer position2 = (Integer) map.get(obj2);
        if (position1 == null || position2 == null) {
            switch (unknownObjectBehavior) {
                case UNKNOWN_BEFORE :
                    if (position1 == null) {
                        return (position2 == null) ? 0 : -1;
                    } else {
                        return 1;
                    }
                case UNKNOWN_AFTER :
                    if (position1 == null) {
                        return (position2 == null) ? 0 : 1;
                    } else {
                        return -1;
                    }
                case UNKNOWN_THROW_EXCEPTION :
                    Object unknownObj = (position1 == null) ? obj1 : obj2;
                    throw new IllegalArgumentException("Attempting to compare unknown object " + unknownObj);
                default :
                    throw new UnsupportedOperationException("Unknown unknownObjectBehavior: " + unknownObjectBehavior);
            }
        } else {
            return position1.compareTo(position2);
        }
    }
}
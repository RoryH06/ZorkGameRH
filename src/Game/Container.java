package Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Container<T> implements Iterable<T> {

    private List<T> contents = new ArrayList<>();

    public void add(T element) {
        contents.add(element);
    }

    public boolean contains(T element) {
        return contents.contains(element);
    }

    public boolean remove(T element) {
        return contents.remove(element);
    }

    public List<T> getAll() {
        return contents;
    }

    @Override
    public Iterator<T> iterator() {
        return contents.iterator();
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }
}

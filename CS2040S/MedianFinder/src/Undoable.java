import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Function;

class CannotUndoException extends RuntimeException {
}

class Undoable<T> {
    T value;
    Deque<Object> history;

    Undoable(T t, Deque<Object> history) {
        this.value = t;
        this.history = history;
    }

    static <T> Undoable<T> of(T t) {
        return new Undoable<T>(t, new LinkedList<Object>());
    }
    public <R> Undoable<R> flatMap(Function<T, Undoable<R>> mapper) {
        // fill in the blank
        Undoable<R> r = mapper.apply(value);
        Deque<Object> newHistory = new LinkedList<>();
        newHistory.addAll(history);
        newHistory.addAll(r.history);
        return new Undoable<R>(r.value, newHistory);
    }
    public <R> Undoable<R> undo() {
        Deque<Object> newHistory = new LinkedList<>(this.history);
        R r;

        try {
            r = (R)newHistory.removeLast(); // Line A
        } catch (NoSuchElementException e) {
            // Missing line B
            throw new CannotUndoException();
        }
        return new Undoable<R>(r, newHistory);
    }

    static Undoable<Integer> length(String s) {
        Deque<Object> history;
        history = new LinkedList<>();
        history.add(s);
        return new Undoable<Integer>(s.length(), history);
    }

    public static void main(String[] args) {
        Undoable<Integer> i = Undoable.of("hello").flatMap(s -> Undoable.length(s));
        Undoable<Double> d = i.undo();
        System.out.println(d);

    }
}
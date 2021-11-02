

## Labs

### Maybe

```java
import java.util.NoSuchElementException;

public abstract class Maybe<T> {
  protected abstract T get();
  
  public abstract Maybe<T> filter(BooleanCondition<? super T> bool);
  
  public abstract <U> Maybe<U> map(Transformer<? super T, ? extends U> transformer);
  
  public abstract <U> Maybe<U> flatMap(Transformer<? super T, 
      ? extends Maybe<? extends U>> transformer);
  
  public abstract T orElse(T t);
  
  public abstract T orElseGet(Producer<? extends T> producer);


  public static <T> Maybe<T> none() {
    @SuppressWarnings("unchecked")
    Maybe<T> a = (Maybe<T>) None.NONE;
    return a;
  }

  public static <T> Some<T> some(T t) {
    return new Some<>(t);
  }

  public static <T> Maybe<T> of(T t) {
    if (t == null) {
      return none();
    } else {
      return some(t);
    }
  }


  public static class None extends Maybe<Object> {
    private static final None NONE = new None();

    @Override
    protected Object get() throws NoSuchElementException {
      throw new NoSuchElementException(); 
    }


    @Override
    public String toString() {
      return "[]";
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof None;
    }

    @Override
    public Maybe<Object> filter(BooleanCondition<? super Object> bool) {
      return Maybe.none();
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super Object, ? extends U> transformer) {
      return Maybe.none();
    }

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super Object, 
        ? extends Maybe<? extends U>> transformer) {
      return Maybe.none();
    }

    @Override
    public Object orElse(Object t) {
      return t;
    }

    @Override
    public Object orElseGet(Producer<?> producer) {
      return producer.produce();
    }

  }

  public static final class Some<T> extends Maybe<T> {
    private T t;

    private Some(T t) {
      this.t = t;
    }

    @Override
	protected T get() {
      return this.t;
    }

    @Override
    public String toString() {
      return "[" + this.get() + "]";
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if (obj instanceof Some) {
        Some<?> b = (Some<?>) obj;
        if (this.get() == null) {
          return this.get() == b.get();
        } else {
          return this.get().equals(b.get());
        }
      } else {
        return false;
      }
    }

    @Override
    public Maybe<T> filter(BooleanCondition<? super T> bool) {
      if (this.get() != null && !(bool.test(this.get()))) {
        return Maybe.none();
      } 
      return this;
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super T, ? extends U> transformer) {
      return Maybe.some(transformer.transform(this.get()));
    }

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super T, ? extends Maybe<? extends U>> transformer) {
      @SuppressWarnings("unchecked")
      // input can only be U and its subtypes so it is safe to type cast to U
      Maybe<U> a = (Maybe<U>) transformer.transform(this.get());
      return a;
    }

    @Override
    public T orElse(T t) {
      return this.get();
    } 

    @Override
    public T orElseGet(Producer<? extends T> producer) {
      return this.get();
    }
  }
}
```

### Lazy<T>

```java
public class Lazy<T> {
  private Maybe<T> value;
  private Producer<T> producer;

  private Lazy(T v) {
    this.value = Maybe.some(v);
    this.producer = null;
  }

  private Lazy(Producer<T> producer) {
    this.value = Maybe.none();
    this.producer = producer; 
  }
  
  public static <T> Lazy<T> of(T v) {
    return new Lazy<>(v);
  }

  public static <T> Lazy<T> of(Producer<T> s) {
    return new Lazy<>(s);
  }

  public T get() {
    this.value = Maybe.some(this.value.orElseGet(() -> this.producer.produce()));
    return this.value.orElseGet(this.producer);
  }

  @Override
  public String toString() {
    Transformer<T, String> strTransformer = x -> String.valueOf(x);
    Maybe<String> strMaybe = this.value.map(strTransformer);
    return strMaybe.orElse("?");
  }

  public <U> Lazy<U> map(Transformer<? super T, ? extends U> transformer) {
    return Lazy.of(() -> transformer.transform(this.get()));
  }

  public <U> Lazy<U> flatMap(Transformer<? super T, ? extends Lazy<? extends U>> transformer) {
    return Lazy.of(() -> transformer.transform(this.get()).get());
  }

  public Lazy<Boolean> filter(BooleanCondition<? super T> bool) {
    return Lazy.of(() -> bool.test(this.get()));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof Lazy) {
      Lazy<?> b = (Lazy<?>) obj;
      if (this.get() == null) {
        return b.get() == null;
      } else {
        return this.get().equals(b.get());
      }
    } else {
      return false;
    }
  }
  
  public <U, R> Lazy<R> combine(Lazy<U> lazyObj, Combiner<? super T, ? super U, ? extends R> 
      combiner) {
    return Lazy.of(() -> combiner.combine(this.get(), lazyObj.get()));
  }  
}
```

### Infinite List

```java
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class InfiniteList<T> {
  private final Lazy<Maybe<T>> head;
  private final Lazy<InfiniteList<T>> tail;
  private static InfiniteList<?> EMPTY = new EmptyList();

  private InfiniteList() { 
    head = null; 
    tail = null;
  }

  public static <T> InfiniteList<T> generate(Producer<T> producer) {
    return new InfiniteList<T>(
        Lazy.of(() -> Maybe.some(producer.produce())),
        Lazy.of(() -> InfiniteList.generate(producer)));
  }

  public static <T> InfiniteList<T> iterate(T seed, Transformer<T, T> next) {
    return new InfiniteList<>(seed, 
        () -> InfiniteList.iterate(next.transform(seed), next));
  }

  private InfiniteList(T head, Producer<InfiniteList<T>> tail) {
    this.head = Lazy.of(() -> Maybe.some(head));
    this.tail = Lazy.of(tail);
  }

  private InfiniteList(Lazy<Maybe<T>> head, Lazy<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  public T head() {
    return this.head.get().orElseGet(() -> this.tail.get().head());
  }

  public InfiniteList<T> tail() {
    Maybe<InfiniteList<T>> t = this.head.get().map(x -> this.tail.get());
    return t.orElseGet(() -> this.tail.get().tail());
  }

  public <R> InfiniteList<R> map(Transformer<? super T, ? extends R> mapper) {
    return new InfiniteList<>(
        this.head.map(h -> h.map(mapper)),
        this.tail.map(t -> t.map(mapper)));
  }

  public InfiniteList<T> filter(BooleanCondition<? super T> predicate) {
    return new InfiniteList<>(
        this.head.map(h -> h.filter(predicate)),
        this.tail.map(t -> t.filter(predicate)));
  }

  public static <T> InfiniteList<T> empty() {
    @SuppressWarnings("unchecked")
    InfiniteList<T> temp = (InfiniteList<T>) EMPTY;
    return temp;
  }

  public InfiniteList<T> limit(long n) {
    if (n <= 0) {
      return empty();
    } else {
      long num = this.head.get().map(x -> n - 1).orElseGet(() -> n);
      return new InfiniteList<>(this.head, this.tail.map(t -> t.limit(num)));
    }
  }

  public InfiniteList<T> takeWhile(BooleanCondition<? super T> predicate) {
    /*
    if (this.head.get().equals(Maybe.none()) && this.tail.get().equals(empty())) {
      return this;
    } else {
      Lazy<Boolean> bool = Lazy.of(() -> this.head()).filter(predicate);
      return new InfiniteList<>(
          Lazy.of(() -> bool.get() ? Maybe.some(this.head()) : Maybe.none()),
          Lazy.of(() -> bool.get() ? this.tail().takeWhile(predicate) : empty()));
    }
    */
    Lazy<Boolean> bool = this.head.filter(h -> h.filter(predicate).equals(h));
    return new InfiniteList<>(
        Lazy.of(() -> bool.get() ? this.head.get() : Maybe.none()),
        Lazy.of(() -> bool.get() ? this.tail.get().takeWhile(predicate) : empty()));
  }

  public boolean isEmpty() {
    return this instanceof EmptyList;
  }

  public <U> U reduce(U identity, Combiner<U, ? super T, U> accumulator) {
    U reducedVal = this.head.get().map(x -> accumulator.combine(identity, x)).orElse(identity);
    return this.tail.get().reduce(reducedVal, accumulator);
  }

  public long count() {
    long counter = 0L;;
    InfiniteList<T> temp = this;

    while (!temp.isEmpty()) {
      counter += temp.head.get().map(h -> 1L).orElse(0L);
      temp = temp.tail.get();
    }
    return counter;
  }

  public List<T> toList() {
    List<T> list = new ArrayList<>();
    InfiniteList<T> temp = this;

    while (!temp.isEmpty()) {
      temp.head.get().map(h -> list.add(h));
      temp = temp.tail.get(); 
    }
    return list;
  }

  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }


  private static class EmptyList extends InfiniteList<Object> {
    private EmptyList() {
      super();
    }

    @Override
    public Object head() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public InfiniteList<Object> tail() {
      return empty();
    }

    @Override
    public List<Object> toList() {
      return new ArrayList<>();
    }

    @Override
    public InfiniteList<Object> limit(long n) {
      return empty();
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public <R> InfiniteList<R> map(Transformer<? super Object, ? extends R> mapper) {
      return empty();
    }

    @Override
    public InfiniteList<Object> filter(BooleanCondition<? super Object> predicate) {
      return empty();
    }

    @Override 
    public InfiniteList<Object> takeWhile(BooleanCondition<? super Object> predicate) {
      return empty();
    }

    @Override
    public <U> U reduce(U identity, Combiner<U, ? super Object, U> accumulator) {
      return identity;
    }

    @Override
    public long count() {
      return 0;
    }
  }
}
```

### Boolean Condition

```java
@FunctionalInterface
public interface BooleanCondition<T> {
  boolean test(T t);
}
```

### Combiner

```java
@FunctionalInterface
public interface Combiner<S, T, R> {
  R combine(S s, T t);
}
```

### Producer

```java
@FunctionalInterface
public interface Producer<T> {
  T produce();
}
```

### Transformer

```java
@FunctionalInterface
public interface Transformer<U, T> {
  T transform(U u);
}
```

### Box

```java
public class Box<T> {
  private final T t;
  private static final Box<?> EMPTY_BOX = new Box<>(null);

  private Box(T t) {
    this.t = t;
  }

  public T get() {
    return this.t;
  }

  public static <T> Box<T> of(T t) {
    if (t == null) {
      return null;
    } else {
      return new Box<>(t);
    }
  }

  public static <T> Box<T> ofNullable(T t) {
    if (t == null) {
      return empty();
    } else {
      return new Box<>(t);
    }
  }

  public static <T> Box<T> empty() {
    @SuppressWarnings("unchecked")
    Box<T> b = (Box<T>) EMPTY_BOX;
    return b;
  }

  public boolean isPresent() {   
    return !(this.equals(EMPTY_BOX)); 
     
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o instanceof Box) {
      Box<?> b = (Box<?>) o;
      if (this.get() == null) {
        return this.get() == b.get();
      } else {
        return this.get().equals(b.get());
      }
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    if (this.get() == null) {
      return "[]";
    }
    return "[" + this.t + "]";
  }

  public Box<T> filter(BooleanCondition<? super T> bool) {
    if ((this.get() == null) || !(bool.test(this.get()))) {
      return empty();
    } else {
      return this;
    }
  }

  public <U> Box<U> map(Transformer<? super T, ? extends U> transformer) {
    if (this.get() == null) {
      return empty();
    } else {
      return new Box<>(transformer.transform(this.t));
    }
  }
}

public class LongerThan implements BooleanCondition<String> {
  int limit;

  public LongerThan(int limit) {
    this.limit = limit;
  }

  @Override
  public boolean test(String s) {
    return s.length() > this.limit;
  }
}

public class LastDigitsOfHashCode implements Transformer<Object, Integer> {
  int k;

  public LastDigitsOfHashCode(int k) {
    this.k = k;
  }

  @Override
  public Integer transform(Object t) {
    return Math.abs((int) (t.hashCode() % Math.pow(10, k)));
  }
}

public class DivisibleBy implements BooleanCondition<Integer> {
  Integer intVal;

  public DivisibleBy(Integer intVal) {
    this.intVal = intVal;
  }

  @Override 
  public boolean test(Integer integer) {
    return integer % this.intVal == 0;
  }
}

public class BoxIt<T> implements Transformer<T, Box<T>> {

  @Override
  public Box<T> transform(T t) {
    return Box.of(t);
  }
}
```

 

### Lazy List

```java
import cs2030s.fp.Lazy;
import cs2030s.fp.Transformer;
import java.util.ArrayList;
import java.util.List;

class LazyList<T> {
  private List<Lazy<T>> list;

  private LazyList(List<Lazy<T>> list) {
    this.list = list;
  }

  public static <T> LazyList<T> generate(int n, T seed, Transformer<T, T> f) {
    LazyList<T> lazyList = new LazyList<>(new ArrayList<>());
    Lazy<T> curr = Lazy.of(seed);

    for (int i = 0; i < n; i++) {
      lazyList.list.add(curr);
      curr = curr.map(f);
    }
    return lazyList;
  }

  public T get(int i) {
    return this.list.get(i).get();
  }

  public int indexOf(T v) {
	return this.list.indexOf(Lazy.of(v));
  }

  @Override
  public String toString() {
    return this.list.toString();
  }
}
```



### Eager List

```java
import cs2030s.fp.Transformer;
import java.util.ArrayList;
import java.util.List;

class EagerList<T> {
  private List<T> list;

  private EagerList(List<T> list) {
    this.list = list;
  }

  public static <T> EagerList<T> generate(int n, T seed, Transformer<T, T> f) {
    EagerList<T> eagerList = new EagerList<>(new ArrayList<>());
    T curr = seed;
    for (int i = 0; i < n; i++) {
      eagerList.list.add(curr);
      curr = f.transform(curr);
    }
    return eagerList;
  }

  public T get(int i) {
    return this.list.get(i);
  }

  public int indexOf(T v) {
    return this.list.indexOf(v);
  }

  @Override
  public String toString() {
    return this.list.toString();
  }
}
```



## Lecture Notes

### Immutability

<u>Circle and Point</u>

```java
final class Point {
    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private final static Point ORIGIN = new Point(0, 0); // returns the same instance ORIGIN every time when Point.of(0, 0) is called.
    // Such a design pattern is only safe when the class is immutable. Consider the mutable version of Point -- calling Point.of(0, 0).moveTo(1, 1) would change every reference to the origin to (1, 1)

    public static Point of(double x, double y) {
        if (x == 0 && y == 0) {
            return ORIGIN;
        }
        return new Point(x, y);
    }
      :
}

final class Circle { // disallow inheritance, final methods cannot be overriden
    final private Point c;
    final private double r;

    public Circle (Point c, double r) {
        this.c = c;
        this.r = r;
    }

     :

    public Circle moveTo(double x, double y) {
        return new Circle(c.moveTo(x, y), r);
    }
}

Point p = new Point(0, 0);
Circle c1 = new Circle(p, 1);
Circle c2 = new Circle(p, 4);
c1.moveTo(1, 1); // c1 remains unchanged
c1 = c1.moveTo(1, 1); // updates c1
```

<u>Immutable Array</u>

```java
class ImmutableArray<T> {
  private final int start;
  private final int end;
  private final T[] array;

  @SafeVarargs
  public static <T> ImmutableArray<T> of(T... items) { // ... notation is a Java syntax for a variable number of arguments (varargs) of the same type (T), i.e. items = T[]
    return new ImmutableArray<>(items, 0, items.length-1);
  }

  private ImmutableArray(T[] a, int start, int end) {
    this.start = start;
    this.end = end;
    this.array = a;
  }

  public T get(int index) {
    if (index < 0 || this.start + index > this.end) {
      throw new IllegalArgumentException("Index out of bound");
    }
    return this.array[this.start + index];
  }

  public ImmutableArray<T> subarray(int start, int end) {
     return new ImmutableArray<>(this.array, this.start + start, this.start + end);
  }
/*
an naive method to implement subarray would be to allocate a new T[] and copy the elements over. This can be expensive. Since our class is immutable and the internal field array is guaranteed not to mutate, we can safely let b and c refer to the same array from a, and only store the starting and ending index.
*/
}

ImmutableArray<Integer> a;
a = ImmutableArray.of(1, 2, 3);
a = ImmutableArray.of(1, 2, 3, 4, 5);
ImmutableArray<Integer> a = ImmutableArray.of(10, 20, 30, 40, 50, 60);
ImmutableArray<Integer> b = a.subarray(2, 4); // b is [30, 40, 50]
b.get(0) // returns 30
ImmutableArray<Integer> c = b.subarray(1, 2); // c is [40, 50]
c.get(1) // returns 50
```

### Nested Classes

<u>**Non-static nested inner class** (a.k.a. inner classes)</u>

- involves the nesting of a class inside another class. 
- The inner class can access the all the fields and methods, even private variables, of the outer class.
- can modify access to the inner class by using access modifier keywords(e.g. private, protected, and default)
- Similarly, access specifiers can help nest interfaces inside one another.
- NOTE: even though we can have nested static classes, we **cannot have methods that are static inside the nested classes**. Because all the methods of the nested class are implicitly connected to the object of its outer enclosing class. Hence they cannot declare any static methods for themselves.

<u>**Static nested classes**</u>

- If you declare the inner class to be static, then you can access the class **without having to create an object of the outer class.** 
- However, the **static class will not have access to members of the outer class**. 
- can only access **static fields and static methods** of the containing class
- We can access the elements of the outer class from the inner class.

```java
class A {
  private int x;
  static int y;

  class B { // non-static nested inner class
    void foo() {
      x = 1; // accessing x from A is OK
      y = 1; // accessing y from A is OK
    }
  }

  static class C { // static nested inner class
    void bar() {
      x = 1; // accessing x from A is not OK since C is static
      y = 1; // accessing y is OK
    }
  }
}
----------------------------
class A {
 private int x;

 class B {
   void foo() {
     this.x = 1; // error: this refers to B but B does not have field x
     A.this.x = 1; // ok
   }
 }
}
```

(**Method) local classes** 

- the outer class method contains the inner class.
- a local class has access to the **variables of the enclosing class** through the qualified `this` reference and the **local variables of the enclosing method**.
- even though a local class can access the local variables in the enclosing method, the local class makes *a copy of local variables* inside itself, i.e. a local class *captures* the local variables.
  - Java only allows a local class to access variables that are explicitly declared final or implicitly final (or effectively final).

```java
void sortNames(List<String> names) {

  class NameComparator implements Comparator<String> {
    public int compare(String s1, String s2) {
      return s1.length() - s2.length();
    }
  }

  names.sort(new NameComparator());
}

class A {
  int x = 1;

  void f() {
    int y = 1;

    class B {
      void g() {
        x = y; // accessing x and y is OK.
      }
    }

    new B().g();
  }
}
```

**Anonymous inner classes**

- inner classes without a name.
- need to be declared and instantiated at the same time
- The definition of the classes are written outside the scope of the outer class. 
- These classes are useful when we have to design an interface or overload a method. 
- It saves the effort of us having to nest the class.
- 2 types of anon inner classes: subclass of the specified type & the implementer of the specified interface

<u>Subclass of the specified type</u>

- the anonymous class is put inside a subclass of the outer class.

```java
class OuterClass {
  void print() {
    System.out.println("I am in the print method of superclass");
  }
}

class AnonymousClass {
  // An anonymous class with OuterClass as base class 
  // start of the anonymous class.
  static OuterClass out = new OuterClass() {
    void print() {
      super.print();
      System.out.println("I am in Anonymous class");
    }
  };
    
  public static void main(String[] args) {
    out.print();
  }
}
/*
I am in the print method of the superclass
I am in Anonymous class
*/
```

<u>Implementer of specified interface</u>

- The anonymous class can extend a class or implement an interface at a time. 

```java
Comparator<String> cmp = new Comparator<String>() {
  public int compare(String s1, String s2) {
    return s1.length() - s2.length();
  }
};
names.sort(cmp);
```

## Functions

```java
Point origin = new Point(0, 0);
Transformer<Point, Double> dist = new Transformer<>() {
    @Override
    public Double transform(Point p) {
        return origin.distanceTo(p);
    }
}

Point origin = new Point(0, 0);
Transformer<Point, Double> dist = p -> origin.distanceTo(p);

Point origin = new Point(0, 0);
Transformer<Point, Double> dist = origin::distanceTo; // :: method ref
// origin captured by the lambda expression dist -> (effectively) final
Box::of            // x -> Box.of(x)
Box::new           // x -> new Box(x)
x::compareTo       // y -> x.compareTo(y)
A::foo             // (x, y) -> x.foo(y) or (x, y) -> A.foo(x,y)
```

<u>Curried Functions</u>

```java
int add(int x, int y) {
  return x + y;
}
Transformer<Integer, Transformer<Integer, Integer>> add = x -> y -> (x + y); // add is a higher-order function that takes in a single argument and returns another function.
Transformer<Integer,Integer> incr = add.transform(1);

```

### Eager List

```java
class EagerList<T> {
  private final T head;
  private final EagerList<T> tail;
  private static EagerList<?> EMPTY = new EmptyList(); 

  public EagerList(T head, EagerList<T> tail) {
    this.head = head;
    this.tail = tail;
  }

  public T head() {
    return this.head;
  }

  public EagerList<T> tail() {
    return this.tail;
  }
    
  public static <T> EagerList<T> generate(T t, int size) {
    if (size == 0) {
      return empty();
    }
    return new EagerList<>(t, generate(t, size - 1));
  }

  public static <T> EagerList<T> iterate(T init, BooleanCondition<? super T> cond, Transformer<? super T, ? extends T> op) {
    if (!cond.test(init)) {
      return empty();
    }
    return new EagerList<>(init, iterate(op.transform(init), cond, op));
  }

  public static <T> EagerList<T> empty() {
    @SuppressWarnings("unchecked")
    EagerList<T> temp = (EagerList<T>) EMPTY;
    return temp;
  }
    
  public <R> EagerList<R> map(Transformer<? super T, ? extends R> mapper) {
    return new EagerList<>(mapper.transform(this.head()), this.tail().map(mapper));
  }
    
  public EagerList<T> filter(BooleanCondition<? super T> cond) {
    if (cond.test(this.head())) {
      return new EagerList<>(this.head(), this.tail().filter(cond));
    }
    return this.tail().filter(cond);
  }
    
  

  private static class EmptyList extends EagerList<Object> {
    EmptyList() {
      super(null, null);
    }

    @Override
    public Object head() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public EagerList<Object> tail() {
      throw new java.util.NoSuchElementException();
    }
      
    @Override
    public <R> EagerList<R> map(Transformer<? super Object, ? extends R> mapper) {
      return empty();
    }

    @Override
    public EagerList<Object> filter(BooleanCondition<? super Object> cond) {
      return empty()      
    }
  }
}

EagerList<Integer> l = EagerList.iterate(1, i -> i < 10, i -> i + 1) // [1, ... 9]
    .filter(i -> i % 3 == 0)  // [3, 6, 9]
    .map(i -> i * 2);  // [6, 12, 18]
l.head();        // 6
l.tail().head(); // 12
l.tail().tail().head(); // 18
```

### Infinite List

```java
class InfiniteList<T> {
  private Producer<T> head;
  private Producer<InfiniteList<T>> tail;

  public static <T> InfiniteList<T> generate(Producer<T> producer) {
    return new InfiniteList<T>(producer,
        () -> InfiniteList.generate(producer));
  }

  public static <T> InfiniteList<T> iterate(T init, Transformer<T, T> next) {
      return new InfiniteList<T>(() -> init,
      () -> InfiniteList.iterate(next.transform(init), next));
  }

  public InfiniteList(Producer<T> head, Producer<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  public T head() {
    T h = this.head.produce();
    return h == null ? this.tail.produce().head() : h;  
  }

  public InfiniteList<T> tail() {
    T h = this.head.produce();
    return h == null ? this.tail.produce().tail() : this.tail.produce();  
  }

  public <R> InfiniteList<R> map(Transformer<? super T, ? extends R> mapper) {
    return new InfiniteList<>(
        () -> mapper.transform(this.head()),
        () -> this.tail().map(mapper));
  }
    
  public InfiniteList<T> filter(BooleanCondition<? super T> cond) {
    Producer<T> newHead = () -> cond.test(this.head()) ? this.head() : null;
    return new InfiniteList<>(newHead, () -> this.tail().filter(cond));
  }
}
```

| CS2030S                       | java.util.function         |
| :---------------------------- | :------------------------- |
| `BooleanCondition<T>::test`   | `Predicate<T>::test`       |
| `Producer<T>::produce`        | `Supplier<T>::get`         |
| `Transformer<T,R>::transform` | `Function<T,R>::apply`     |
| `Transformer<T,T>::transform` | `UnaryOp<T>::apply`        |
| `Combiner<S,T,R>::combine`    | `BiFunction<S,T,R>::apply` |

| CS2030S           | Java version                 |
| ----------------- | ---------------------------- |
| `Maybe<T>`        | `java.util.Optional<T>`      |
| `Lazy<T>`         | N/A                          |
| `InfiniteList<T>` | `java.util.stream.Stream<T>` |

### Streams

- a stream can only be operated on once. 
- cannot iterate through a stream multiple times ->  ` IllegalStateException`  
- have to recreate the stream if we want to operate on the stream more than once.

```java
Stream<Integer> s = Stream.of(1,2,3);
s.count(); // terminal
s.count(); // <- error
```

```java
boolean isPrime(int x) {
  for (int i = 2; i <= x-1; i++) {
    if (x % i == 0) {
      return false;
    }
  }
  return true;
}

boolean isPrime(int x) {
  return IntStream.range(2, Math.sqrt(x))
      .noneMatch(i -> x % i == 0);
}

void fiveHundredPrime() {
  int count = 0;
  int i = 2;
  while (count < 500) {
    if (isPrime(i)) {
      System.out.println(i);
      count++;
    }
    i++;
  }
}

IntStream.iterate(2, x -> x+1)
    .filter(x -> isPrime(x))
    .limit(500) // intermediate
    .forEach(System.out::println); // terminal
```


public class A {
    static int x;

    static int foo() {
        return 0;
    }

    int bar() {
        return 1;
    }

    static class B{

    }

    public static void main(String[] args) {
        x = 1;
        A a = new A();
        B b = new B();
        new A().foo();// not the best way but can still compile: equivalent to new A(); A.foo();
        new A().bar();

    }
}

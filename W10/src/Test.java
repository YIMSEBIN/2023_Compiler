public class Test extends Object {
    // Standard initializer
    public Test() {
        super();
    }

    public static int add(int a, int b) {
        int result;
        result = a + b;
        return result;
    }

    public static void main(String[] args) {
        int value = 33;
        System.out.println(add(1, value));
    }
}
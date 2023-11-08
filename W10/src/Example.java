public class Example {

    int number;

    public Example(int number) {
        this.number = number;
    }

    void printNumber() {
        System.out.println("print: " + this.number);
    }

    public static void main(String[] args) {
        Example example = new Example(12);
        example.printNumber();
    }
}
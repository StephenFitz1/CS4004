package code;

public class ConsoleOutput {
    public void println(String line) {
        System.out.println(line);
    }

    public void print(String line) {
        System.out.print(line);
    }

    public void printf(String pattern, Object... args) {
        System.out.printf(pattern, args);
    }
}

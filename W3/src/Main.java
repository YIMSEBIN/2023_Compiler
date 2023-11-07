import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        String result = parse(input);
        if (result == "FAIL") {
            System.out.println("FAIL");
        } else {
            System.out.println("OK");
        }

    }

    //TODO Non-terminal 심볼 S의 파싱
    // S는 'aA', 'bB' 중 하나로 파싱될 수 있음.
    // 이때 맨 앞자리 심볼을 확인 후 해당되는 방식의 각 심볼 parse 함수 호출
    public static String parse(String input) {
        char firstchar = input.charAt(0);
        // 'aA'에 해당되는지 확인하기 위해
        // parsea, parseA 호출
        if (firstchar == 'a') {
            String after = parsea(input);
            after = parseA(after);
            return after;
        }
        // 'bB'에 해당되는지 확인하기 위해
        // parseb, parseB 호출
        else if (firstchar == 'b') {
            String after = parseb(input);
            after = parseB(after);
            return after;
        } else {
            return "FAIL";
        }
    }

    //TODO terminal 심볼 a의 파싱
    // nextSymbol (입력받은 string에서 맨 앞 글자) 이 a가 확실하다면 입력string에서 nextSymbol을 제외한 부분을 리턴.
    public static String parsea(String nextString) {
        char nextchar = nextString.charAt(0);
        if (nextchar == 'a') {
            return nextString.substring(1);
        } else {
            return "FAIL";
        }
    }

    //TODO terminal 심볼 b의 파싱
    // nextSymbol (입력받은 string에서 맨 앞 글자) 이 b가 확실하다면 입력string에서 nextSymbol을 제외한 부분을 리턴.
    public static String parseb(String nextString) {
        char nextchar = nextString.charAt(0);
        if (nextchar == 'b') {
            return nextString.substring(1);
        } else {
            return "FAIL";
        }
    }

    //TODO terminal 심볼 c의 파싱
    // nextSymbol (입력받은 string에서 맨 앞 글자) 이 c가 확실하다면 입력string에서 nextSymbol을 제외한 부분을 리턴.
    public static String parsec(String nextString) {
        char nextchar = nextString.charAt(0);
        if (nextchar == 'c') {
            return nextString.substring(1);
        } else {
            return "FAIL";
        }
    }

    //TODO terminal 심볼 d의 파싱
    // nextSymbol (입력받은 string에서 맨 앞 글자) 이 d가 확실하다면 입력string에서 nextSymbol을 제외한 부분을 리턴.
    public static String parsed(String nextString) {
        char nextchar = nextString.charAt(0);
        if (nextchar == 'd') {
            return nextString.substring(1);
        } else {
            return "FAIL";
        }
    }

    //TODO terminal 심볼 e의 파싱
    // nextSymbol (입력받은 string에서 맨 앞 글자) 이 e가 확실하다면 입력string에서 nextSymbol을 제외한 부분을 리턴.
    public static String parsee(String nextString) {
        char nextchar = nextString.charAt(0);
        if (nextchar == 'e') {
            return nextString.substring(1);
        } else {
            return "FAIL";
        }
    }

    //TODO terminal 심볼 f의 파싱
    // nextSymbol (입력받은 string에서 맨 앞 글자) 이 f가 확실하다면 입력string에서 nextSymbol을 제외한 부분을 리턴.
    public static String parsef(String nextString) {
        char nextchar = nextString.charAt(0);
        if (nextchar == 'f') {
            return nextString.substring(1);
        } else {
            return "FAIL";
        }
    }

    //TODO Non-terminal 심볼 A의 파싱
    // A는 'aBb', 'bBb', 'cBb' 중 하나로 파싱될 수 있음.
    // 이때 맨 앞자리 심볼을 확인 후 해당되는 방식의 각 심볼 parse 함수 호출
    public static String parseA(String nextString) {
        char nextchar = nextString.charAt(0);
        if (nextchar == 'a') {
            String after = parsea(nextString);
            after = parseB(after);
            after = parseb(after);
            return after;
        } else if (nextchar == 'b') {
            String after = parseb(nextString);
            after = parseB(after);
            after = parseb(after);
            return after;
        } else if (nextchar == 'c') {
            String after = parsec(nextString);
            after = parseB(after);
            after = parseb(after);
            return after;
        } else {
            return "FAIL";
        }
    }

    //TODO Non-terminal 심볼 B의 파싱
    // S는 'd', 'e', 'f' 중 하나로 파싱될 수 있음.
    // 이때 맨 앞자리 심볼을 확인 후 해당되는 방식의 각 심볼 parse 함수 호출
    public static String parseB(String nextString) {
        char nextchar = nextString.charAt(0);
        if (nextchar == 'd') {
            String after = parsed(nextString);
            return after;
        } else if (nextchar == 'e') {
            String after = parsee(nextString);
            return after;
        } else if (nextchar == 'f') {
            String after = parsef(nextString);
            return after;
        } else {
            return "FAIL";
        }
    }
}
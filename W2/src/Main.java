import java.io.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        // txt파일 입력받기
        BufferedReader reader = new BufferedReader(
                new FileReader("./input.txt"), 16 * 1024
        );

        // str : 입력받은 파일의 한줄
        // total : 내보낼 c파일의 내용
        String str;
        String total = "";

        //TODO
        // phonenum, email, phonetype, file : 각 문자열 종류에 해당하는 정규표현식 패턴
        Pattern phonenum = Pattern.compile("^\\d{3}-\\d{4}-\\d{4}$");
        Pattern email = Pattern.compile("^[a-zA-Z_]+@[a-zA-Z_]+(\\.[a-zA-Z_]+)+$");
        Pattern phonetype = Pattern.compile("^(iPhone|Galaxy) \\d{1,3}$");
        Pattern file = Pattern.compile("^[a-z|A-Z|_]+.(c|java|py|ml)$");

        // str에 한 줄씩 받아와서 확인
        while ((str = reader.readLine()) != null) {
            if (phonenum.matcher(str).find()) {
                total += "Match : The input string is "+str+" Matched Type is [Phone Number]\n";
            } else if (email.matcher(str).find()) {
                total += "Match : The input string is "+str+" Matched Type is [e-mail]\n";
            } else if (phonetype.matcher(str).find()) {
                total += "Match : The input string is "+str+" Matched Type is [Phone Type]\n";
            } else if (file.matcher(str).find()) {
                total += "Match : The input string is "+str+" Matched Type is [Source File]\n";
            } else {
                total += "Error : The input does not belong to the given string type "+str+"\n";
            }
        }

        System.out.println(total);

    }
}
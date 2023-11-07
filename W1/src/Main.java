import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) throws IOException {

        // umm파일 입력받기
        BufferedReader reader = new BufferedReader(
                new FileReader("./test.umm"), 16 * 1024
        );

        // str : 입력받은 파일의 한줄
        // total : 내보낼 c파일의 내용
        String str;
        String total = "";


        while ((str = reader.readLine()) != null) {

            if (str.equals("어떻게")) {
                total += "#include <stdio.h>\nint main() {\nint a=0; int b=0; int c=0;\n";
            } else if (str.equals("이 사람이름이냐ㅋㅋ")) {
                total += "return 0;\n}";
                break;
            } else {
                String[] strArray = str.split("");

                // tmpchar : '어'를 누적시키기위한 임시 변수
                // parttotal : '어', '.', ',', ' ' 가 섞여 쓰일때, scanf 및 printf와 대입의 위치가 다름을 고려하기 위해 변수와 수의 덧셈곱셈을 미리 저장해 둔 후, 한번에 작성시키기 위한 임시 변수
                // tmpcount : 연속되는'.'와 ','를 즉시 계산하기 위한 임시 변수
                String tmpchar = "";
                String parttotal = "";
                int tmpcount = 0;

                // '어', '.' 및 ',' (이하 '.')의 배열 경우의수는 '어.', '.', '어' 세가지.
                // onlychar : 위 경우 중 '어'만 반복되었음을 확인하는 flag
                // intafterchar : 위 경우 중 '어.'와 같이 '어'의 반복 이후 '.'이 반복됨을 확인하는 flag
                boolean onlychar = false;
                boolean intafterchar = false;

                for(int i=0; i< strArray.length; i++) {

                    String nowchar = strArray[i];

                    //TODO 토큰 "엄" 처리
                    if (nowchar.equals("엄")) {

                        tmpchar += "엄";

                        // '식?' (입력) 앞에 붙는 '엄'을 구별하기 위해, 다음 글자가 '식'인지 확인
                        // 다음 글자가 '식'인 경우 scanf를 먼저 작성 후 해당되는 변수 작성
                        // '식'이 아닌 경우 대입을 위한 '=' 까지 작성
                        // 인덱스오류를 방지하기 위해 현재 글자가 마지막 글자인지를 먼저 확인
                        if (i < strArray.length-1) {
                            if (strArray[i+1].equals("식")) {
                                total += "scanf(\"%d\", &";
                                if (tmpchar.equals("엄")) {
                                    total += "a";
                                } else if (tmpchar.equals("어엄")) {
                                    total += "b";
                                } else if (tmpchar.equals("어어엄")) {
                                    total += "c";
                                }
                                total += ");\n";
                            } else {
                                if (tmpchar.equals("엄")) {
                                    total += "a = ";
                                } else if (tmpchar.equals("어엄")) {
                                    total += "b = ";
                                } else if (tmpchar.equals("어어엄")) {
                                    total += "c = ";
                                }
                            }
                        }
                        // 현재 글자가 마지막 글자인 경우, 해당되는 변수에 0을 대입
                        else {
                            if (tmpchar.equals("엄")) {
                                total += "a = 0;";
                            } else if (tmpchar.equals("어엄")) {
                                total += "b = 0;";
                            } else if (tmpchar.equals("어어엄")) {
                                total += "c = 0;";
                            }
                        }

                        // '어'의 개수를 파악하였고, '엄'에 해당하는 대입 코드를 작성하였으므로 초기화
                        tmpchar = "";
                        onlychar = false;
                    }
                    //TODO 토큰 "어" 처리
                    else if (nowchar.equals("어")) {
                        tmpchar += "어";
                        // 현재 '어'가 입력되었으므로 경우의 수는 '어', '어.'
                        // '.'의 입력이 인식될 경우에는 '어.'의 경우이므로 onlychar flag가 false로 변경될 예정
                        // '어-엄'의 경우는 토큰 '엄'과정에서 모두 처리 후 초기화하므로 고려하지 않음.
                        onlychar = true;

                        // '어' 가 줄의 끝에 오는 경우는 a = a와 같은 특수한 경우 뿐. 이를 위한 코드임.
                        if (i == strArray.length-1) {
                            if (tmpchar.equals("어")) {
                                parttotal += "a;\n";
                            } else if (tmpchar.equals("어어")) {
                                parttotal += "b;\n";
                            } else if (tmpchar.equals("어어어")) {
                                parttotal += "c;\n";
                            }
                            total += parttotal;
                            parttotal = "";
                        }
                    }
                    //TODO 토큰 "식" 처리
                    else if (nowchar.equals("식")) {
                        // '식?'의 경우 토큰 '엄'에서 처리
                        // '식!'의 경우 토큰 '!'에서 처리
                        // '식ㅋ'의 경우 토큰 'ㅋ'에서 처리
                    }
                    //TODO 토큰 "!" 처리
                    else if (nowchar.equals("!")) {

                        // '식'과 '!' 사이 값을 출력해야함.
                        // onlychar : '어'가 입력되었을 때 true, '엄'이나 '.'이 입력되었을때 false
                        // onlychar가 true인 경우 tmpchar를 보고 해당하는 변수를 작성
                        // intafterchar : '.'이 입력되었을때 true
                        // intafterchar가 true인 경우 parttotal에는 직전 '어-'에 해당하는 변수가 들어있으며, 이는 '.'이 입력되었을때 처리되었음.
                        // 그러므로 tmpcount 값을 parttotal에 추가로 작성 후 닫는 괄호 작성.
                        // 두 경우 모두 아닌 경우 '.-' 의 경우이므로, 닫는괄호 없이 tmpcount만 parttotal에 작성.
                        total += "printf(\"%d\", ";
                        if (onlychar) {
                            if (tmpchar.equals("어")) {
                                total += "a";
                            } else if (tmpchar.equals("어어")) {
                                total += "b";
                            } else if (tmpchar.equals("어어어")) {
                                total += "c";
                            }
                            onlychar = false;
                            tmpchar = "";
                        } else if (intafterchar){
                            parttotal += tmpcount+")";
                            intafterchar = false;
                        } else {
                            parttotal += tmpcount;
                        }

                        total += parttotal + ");";
                        parttotal = "";

                    }
                    //TODO 토큰 "?" 처리
                    else if (nowchar.equals("?")) {
                        // '식?'의 경우 토큰 '엄'에서 처리
                    }
                    //TODO 토큰 "ㅋ" 처리
                    else if (nowchar.equals("ㅋ")) {

                        // '식'과 'ㅋ' 사이 값을 문자로 출력해야함.
                        // 토큰 '!'를 처리하는 과정과 동일.
                        total += "printf(\"%c\", ";
                        if (onlychar) {
                            if (tmpchar.equals("어")) {
                                total += "a";
                            } else if (tmpchar.equals("어어")) {
                                total += "b";
                            } else if (tmpchar.equals("어어어")) {
                                total += "c";
                            }
                            onlychar = false;
                            tmpchar = "";
                        } else if (intafterchar){
                            parttotal += tmpcount+")";
                            intafterchar = false;
                        } else {
                            parttotal += tmpcount;
                        }

                        total += parttotal + ");";
                        parttotal = "";

                    }
                    //TODO 토큰 "." 처리
                    else if (nowchar.equals(".")) {

                        // TODO [Task 01] Additional implementation
                        // 변수에 +1, -1 외에도 가능한 모든 숫자들이 증감 가능.
                        // '.' 및 ','이 나올때마다 작성하지 않고, tmpcount 변수에 '.'이 나오면 +1, ','이 나오면 -1을 연산 후
                        // 최종적으로 계산된 숫자를 작성하도록 함.
                        tmpcount++;


                        // TODO [Task 02] Additional implementation
                        // 변수와 정수의 연산
                        // 변수 이후 '.' 및 ','이 나오는 경우, 이를 소괄호로 묶어 덧셈을 계산하도록 함.
                        // 이때 '어-'는 단순히 변수를 불러오는 것으로만 취급하여, '.어'와 같은 문법은 '(+)1a'와 같으므로 불가능하다고 가정하였음.


                        // onlychar가 true인 경우, 경우의 수는 '어.', 즉 (변수 + 정수)
                        // 먼저 여는괄호와, 해당하는 변수, '+'를 parttotal에 작성 후 onlychar를 비활성화하고 intafterchar를 활성화하여 경우의 수 알림.
                        if (onlychar) {
                            if (tmpchar.equals("어")) {
                                parttotal += "(a + ";
                                intafterchar = true;
                            } else if (tmpchar.equals("어어")) {
                                parttotal += "(b + ";
                                intafterchar = true;
                            } else if (tmpchar.equals("어어어")) {
                                parttotal += "(c + ";
                                intafterchar = true;
                            }
                            onlychar = false;
                            tmpchar = "";
                        }

                        // 만약 현재 글자가 마지막 글자인 경우, 경우의수는 '어.' 혹은 '.'
                        // intafterchar인 경우 '어.'이므로 parttotal에 현재 tmpcount와 닫는괄호를 작성
                        // 아닌 경우 parttotal에 tmpcount만 작성
                        // 이후 total에 parttotal 작성
                        if (i == strArray.length-1) {
                            if (intafterchar) {
                                parttotal += tmpcount+");\n";
                            } else {
                                parttotal += tmpcount+";\n";
                            }
                            total += parttotal;
                            parttotal = "";
                        }
                    }
                    //TODO 토큰 "," 처리
                    else if (nowchar.equals(",")) {
                        tmpcount--;

                        // 토큰 '.'를 처리하는 과정과 동일.
                        if (tmpchar.equals("어")) {
                            parttotal += "(a + ";
                            intafterchar = true;
                        } else if (tmpchar.equals("어어")) {
                            parttotal += "(b + ";
                            intafterchar = true;
                        } else if (tmpchar.equals("어어어")) {
                            parttotal += "(c + ";
                            intafterchar = true;
                        }
                        onlychar = false;
                        tmpchar = "";

                        if (i == strArray.length-1) {
                            if (intafterchar) {
                                parttotal += tmpcount+");\n";
                            } else {
                                parttotal += tmpcount+";\n";
                            }
                            total += parttotal;
                        }
                    }
                    //TODO 토큰 " " 처리
                    else if (nowchar.equals(" ")) {

                        // 곱하기를 나타내므로 현재까지 임시변수에 저장된 변수 및 정수를 고려 후 작성해야함.
                        // onlychar인 경우, 괄호 없이 parttotal에 해당하는 변수 작성
                        // intafterchar인 경우, parttotal에 tmpcount와 닫는괄호 작성
                        // 둘 모두 아닌 경우, 닫는괄호 없이 tmpcount만 작성
                        // 곱하기 기호 '*'를 parttotal에 작성하고, tmpchar와 tmpcount 모두 초기화하여 다시 변수 및 정수 및 둘의 덧셈을 입력받기 시작.
                        if (onlychar) {
                            if (tmpchar.equals("어")) {
                                parttotal += "a";
                            } else if (tmpchar.equals("어어")) {
                                parttotal += "b";
                            } else if (tmpchar.equals("어어어")) {
                                parttotal += "c";
                            }
                            onlychar = false;
                        } else if (intafterchar){
                            parttotal += tmpcount+")";
                            intafterchar = false;
                        } else {
                            parttotal += tmpcount;
                        }
                        parttotal += " * ";
                        tmpchar = "";
                        tmpcount = 0;
                    }
                }
            }
        }

        // c파일 내보내기
        try {
            OutputStream output = new FileOutputStream("./test.c");
            byte[] by=total.getBytes();
            output.write(by);
        } catch (Exception e) {
            e.getStackTrace();
        }

        reader.close();

    }
}
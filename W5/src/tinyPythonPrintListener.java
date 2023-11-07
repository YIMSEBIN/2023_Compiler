import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class tinyPythonPrintListener extends tinyPythonBaseListener {

    // 결과를 저장하는 ParseTree
    ParseTreeProperty<String> newTexts = new ParseTreeProperty<>();
    // suite를 몇번 접근하였는지, 즉 들여쓰기를 몇번 해야하는지 카운트하기 위한 전역변수
    int suiteCount = 0;

    @Override
    public void exitFile_input(tinyPythonParser.File_inputContext ctx) {
        String nowTexts = "";
        for(int i=0; i<ctx.getChildCount()-1; i++) {
            if (newTexts.get(ctx.getChild(i)) == null) {
                nowTexts += ctx.getChild(i).getText();
            } else {
                nowTexts += newTexts.get(ctx.getChild(i));
            }
        }
        // 최종 결과 출력
        System.out.println(nowTexts);
    }

    @Override
    public void exitStmt(tinyPythonParser.StmtContext ctx) {
        newTexts.put(ctx, newTexts.get(ctx.getChild(0)));
    }

    @Override
    public void exitSimple_stmt(tinyPythonParser.Simple_stmtContext ctx) {
        String smallStmt = newTexts.get(ctx.getChild(0));
        String nowTexts = smallStmt+"\n";
        newTexts.put(ctx, nowTexts);
    }

    @Override
    public void exitSmall_stmt(tinyPythonParser.Small_stmtContext ctx) {
        newTexts.put(ctx, newTexts.get(ctx.getChild(0)));
    }

    @Override
    public void exitAssignment_stmt(tinyPythonParser.Assignment_stmtContext ctx) {
        String name = ctx.getChild(0).getText();
        String expr = newTexts.get(ctx.getChild(2));
        String nowTexts = name + " = " + expr;
        newTexts.put(ctx, nowTexts);
    }

    @Override
    public void exitFlow_stmt(tinyPythonParser.Flow_stmtContext ctx) {
        newTexts.put(ctx, newTexts.get(ctx.getChild(0)));
    }

    @Override
    public void exitBreak_stmt(tinyPythonParser.Break_stmtContext ctx) {
        newTexts.put(ctx, "break");
    }

    @Override
    public void exitContinue_stmt(tinyPythonParser.Continue_stmtContext ctx) {
        newTexts.put(ctx, "continue");
    }

    @Override
    public void exitCompound_stmt(tinyPythonParser.Compound_stmtContext ctx) {
        newTexts.put(ctx, newTexts.get(ctx.getChild(0)));
    }

    @Override
    public void enterIf_stmt(tinyPythonParser.If_stmtContext ctx) {
        suiteCount++;
    }

    @Override
    public void exitIf_stmt(tinyPythonParser.If_stmtContext ctx) {
        String nowTexts = "";
        suiteCount--;
        String whiteSpace = "    ".repeat(suiteCount);
        nowTexts = "if "+newTexts.get(ctx.getChild(1))+" : "+newTexts.get(ctx.getChild(3));
        for(int i=4; i<ctx.getChildCount(); i+=4) {
            if ((ctx.getChild(i).getText()).equals("elif")) {
                nowTexts += whiteSpace+"elif "+newTexts.get(ctx.getChild(i+1))+" : "+newTexts.get(ctx.getChild(i+3));
            } else if ((ctx.getChild(i).getText()).equals("else")) {
                nowTexts += whiteSpace+"else : "+newTexts.get(ctx.getChild(i+2));
                break;
            }
        }
        newTexts.put(ctx, nowTexts);
    }

    @Override
    public void enterWhile_stmt(tinyPythonParser.While_stmtContext ctx) {
        suiteCount++;
    }

    @Override
    public void exitWhile_stmt(tinyPythonParser.While_stmtContext ctx) {
        String nowTexts = "while "+newTexts.get(ctx.getChild(1))+" : "+newTexts.get(ctx.getChild(3));
        newTexts.put(ctx, nowTexts);
        suiteCount--;
    }

    @Override
    public void enterDef_stmt(tinyPythonParser.Def_stmtContext ctx) {
        suiteCount++;
    }

    @Override
    public void exitDef_stmt(tinyPythonParser.Def_stmtContext ctx) {
        String nowTexts = "def "+ctx.getChild(1).getText()+ctx.getChild(2).getText()+newTexts.get(ctx.getChild(3))+ctx.getChild(4).getText()+" : "+newTexts.get(ctx.getChild(6));
        newTexts.put(ctx, nowTexts);
        suiteCount--;
    }

    @Override
    public void exitSuite(tinyPythonParser.SuiteContext ctx) {
        if (ctx.getChildCount() != 1) {
            String nowTexts = "\n";
            String whiteSpace = "    ".repeat(suiteCount);
            for (int i=1; i<ctx.getChildCount(); i++) {
                nowTexts += whiteSpace+newTexts.get(ctx.getChild(i));
            }
            newTexts.put(ctx, nowTexts);
        } else {
            String whiteSpace = "    ".repeat(suiteCount);
            newTexts.put(ctx, "\n"+whiteSpace+newTexts.get(ctx.getChild(0)));
        }
    }

    @Override
    public void exitArgs(tinyPythonParser.ArgsContext ctx) {
        if (ctx.getChildCount() == 0) {
            newTexts.put(ctx, "");
        } else {
            String nowTexts = ctx.getChild(0).getText();
            for (int i=1; i<ctx.getChildCount(); i+=2) {
                nowTexts += ", "+ctx.getChild(i+1).getText();
            }
            newTexts.put(ctx, nowTexts);
        }
    }

    @Override
    public void exitReturn_stmt(tinyPythonParser.Return_stmtContext ctx) {
        String nowTexts = "return";
        for (int i=1; i<ctx.getChildCount(); i++) {
            nowTexts += " " + newTexts.get(ctx.getChild(i));
        }
        newTexts.put(ctx, nowTexts);
    }

    @Override
    public void exitTest(tinyPythonParser.TestContext ctx) {
        String nowTexts = newTexts.get(ctx.getChild(0));
        for(int i=1; i<ctx.getChildCount(); i+=2) {
            nowTexts += " "+newTexts.get(ctx.getChild(i))+" "+newTexts.get((ctx.getChild(i+1)));
        }
        newTexts.put(ctx, nowTexts);
    }

    @Override
    public void exitPrint_stmt(tinyPythonParser.Print_stmtContext ctx) {
        String printArg = newTexts.get(ctx.getChild(1));
        String nowTexts = "print " + printArg;
        newTexts.put(ctx, nowTexts);
    }

    @Override
    public void exitPrint_arg(tinyPythonParser.Print_argContext ctx) {
        if (ctx.getChild(0) == ctx.STRING()) {
            newTexts.put(ctx, ctx.getChild(0).getText());
        } else {
            newTexts.put(ctx, newTexts.get(ctx.getChild(0)));
        }
    }

    @Override
    public void exitComp_op(tinyPythonParser.Comp_opContext ctx) {
        newTexts.put(ctx, ctx.getChild(0).getText());
    }

    @Override
    public void exitExpr(tinyPythonParser.ExprContext ctx) {
        if (ctx.getChild(0) == ctx.NAME()) {
            String name = ctx.getChild(0).getText();
            String optParan = newTexts.get(ctx.getChild(1));
            newTexts.put(ctx, name + optParan);
        } else if (ctx.getChild(0) == ctx.NUMBER()) {
            newTexts.put(ctx, ctx.getChild(0).getText());
        } else if (ctx.getChild(0) == ctx.OPEN_PAREN()) {
            newTexts.put(ctx, "("+newTexts.get(ctx.getChild(1))+")");
        } else {
            String nowTexts = newTexts.get(ctx.getChild(0));
            for(int i=1; i<ctx.getChildCount(); i += 2) {
                String op = ctx.getChild(i).getText();
                String expr = newTexts.get(ctx.getChild(i+1));
                nowTexts += " "+op+" "+expr;
            }
            newTexts.put(ctx, nowTexts);
        }
    }

    @Override
    public void exitOpt_paren(tinyPythonParser.Opt_parenContext ctx) {
        if (ctx.getChildCount() == 0) {
            newTexts.put(ctx, "");
        } else {
            if (ctx.getChild(1) == ctx.CLOSE_PAREN()) {
                newTexts.put(ctx, "()");
            } else {
                String nowTexts = "(" + newTexts.get(ctx.getChild(1));
                for (int i=3; i<ctx.getChildCount(); i+=2) {
                    nowTexts += ", " + newTexts.get(ctx.getChild(i));
                }
                nowTexts += ")";
                newTexts.put(ctx, nowTexts);
            }
        }
    }

}

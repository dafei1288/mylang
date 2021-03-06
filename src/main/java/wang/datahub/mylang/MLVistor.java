package wang.datahub.mylang;

import wang.datahub.mylang.parser.MyLangBaseVisitor;
import wang.datahub.mylang.parser.MyLangParser;

import java.util.Hashtable;

public class MLVistor extends MyLangBaseVisitor {
//    Hashtable<String, MyLangParser.FunctionDeclContext> sympoltable = new Hashtable<>();
    Hashtable<String,Symbol> _sympoltable = new Hashtable<>();

    @Override
    public Object visitFunctionDecl(MyLangParser.FunctionDeclContext ctx) {
        String functionName = ctx.identifier().getText();
        if(_sympoltable.get(functionName) == null){
            System.out.println("define function ==> "+functionName);
//            sympoltable.put(ctx.identifier().getText(),ctx);
            Symbol symbol = new Symbol();
            symbol.setName(functionName);
            symbol.setParseTree(ctx);
            symbol.setType(Symbol.SymbolType.FUNCTION);
            _sympoltable.put(functionName,symbol);
            return null;
        }
        //return null;
        return super.visitFunctionDecl(ctx);
    }

    @Override
    public Object visitFunctionCall(MyLangParser.FunctionCallContext ctx) {
        String functionName = ctx.identifier().getText();
        if("print".equals(functionName)){
            String parStr = ctx.parameterList().getText();
            if(parStr.startsWith("\"")){
                parStr = parStr.substring(1,parStr.length()-1);
            }
            System.out.println(parStr);
            return null;
        }else{
            System.out.println("run function ==>"+functionName);
            MyLangParser.FunctionDeclContext fdc = (MyLangParser.FunctionDeclContext) _sympoltable.get(functionName).getParseTree();
                    //sympoltable.get(functionName);
            if(fdc==null){
                throw new RuntimeException("undefine function ...." + functionName);
            }
            super.visitFunctionDecl(fdc);
        }
        return super.visitFunctionCall(ctx);
    }
}

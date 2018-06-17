package MathEngine.model;

import MathEngine.handlers.MathHandler;
import MathEngine.handlers.impl.*;

public enum Operation {
    SUM(0, "+", 2, new SumHandler(), "+"), MULTIPLY(1, "*", 2, new MultiplyHandler(), "*"), OPEN_BRACKET(4, "(", -1, null, "("), CLOSE_BRACKET(4, ")", -1, null, ")"),
    COS(3, "O", 1, new CosHandler(), "cos()"), FACT(3, "!", 1, new FactHandler(), "!"),
    SIN(3, "S", 1, new SinHandler(), "sin()"), CTG(3, "C", 1, new CtgHandler(), "ctg()"), TG(3, "T", 1, new TgHandler(), "tg()"), LG(3, "L", 1, new LgHandler(), "lg()"),
    SQRT(3, "Q", 1, new SqrtHandler(), "sqrt()"), DIVISION(0, "-", 2, new DivisionHandler(), "-"), SUBTRACTION(1, "/", 2, new SubstructionHandler(), "/"),
    POW(2, "^", 2, new PowHandler(), "^"),
    PERCENT(3, "%", 1, new PercentHandler(), "%"), NONE(-1, "", -1, null, ""), LN(3, "N", 1, new LnHandler(), "ln()");

    private final int priority;
    private final String name;
    private final String defineName;
    private final int arity;
    private final MathHandler mathHandler;

    Operation(int priority, String name, int arity, MathHandler mathHandler, String defineName) {
        this.priority = priority;
        this.name = name;
        this.arity = arity;
        this.mathHandler = mathHandler;
        this.defineName = defineName;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getArity() {
        return arity;
    }

    public MathHandler getHandler() {
        return mathHandler;
    }

    public static Operation getOperationByName(String name) {
        for (Operation operation : Operation.values())
            if (operation.name.equals(name)) return operation;
        return null;
    }

    public String getDefineName() {
        return defineName;
    }
}

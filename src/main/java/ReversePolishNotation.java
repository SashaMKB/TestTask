import java.util.Stack;

public class ReversePolishNotation {
    private static boolean isNum(char c) {
        return c - '0' >= 0 && c - '0' <= 9;
    }

    private static boolean isOperator(char c) {
        return c == '>' || c == '<' || c == '=' || c == '!' || c == '(' || c == ')' || c == '&' || c == '|';
    }

    public static String[] convertToPolish(String exp) {
        String res = "";
        int len = exp.length();
        Stack<Character> operator = new Stack<>();
        Stack<String> reversePolish = new Stack<>();

        operator.push('#');
        for (int i = 0; i < len; ) {
            boolean cond = isOperator(exp.charAt(i));
            if (!isOperator(exp.charAt(i))) {
                String num = "";
                while (i < len && !isOperator(exp.charAt(i)))
                    num += exp.charAt(i++);
                reversePolish.push(num);
            } else {
                char op = exp.charAt(i);
                if (op == '!' || op == '|')
                    i++;
                switch (op) {
                    case '(' -> operator.push(op);
                    case ')' -> {
                        while (operator.peek() != '(')
                            reversePolish.push(Character.toString(operator.pop()));
                        operator.pop();
                    }
                    case '|' -> {
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                    }
                    case '&' -> {
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '|' &&
                                    operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                    }
                    case '<', '>', '!', '=' -> {
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '|' &&
                                    operator.peek() != '&' && operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                    }
                }
                i++;
            }
        }
        while (operator.peek() != '#')
            reversePolish.push(Character.toString(operator.pop()));
        while (!reversePolish.isEmpty())
            res = res.length() == 0 ? reversePolish.pop() + res : reversePolish.pop() + " " + res;
        return res.split(" ");
    }

    public static Boolean evaluatePolishNotation(String[] tokens) {
        Stack<String> stringStack = new Stack<>();
        Stack<Boolean> boolStack = new Stack<>();
        String operators = "<>!=&|";
        for (String token : tokens) {
            if (!operators.contains(token)) {
                stringStack.push(token);
                continue;
            }
            String a = "", b = "";
            boolean c = false, d = false;
            if (!stringStack.isEmpty()) {
                a = stringStack.pop();
                b = stringStack.pop();
            } else if (!boolStack.isEmpty()) {
                c = boolStack.pop();
                d = boolStack.pop();
            } else {
                return true;
            }

            switch (token) {
                case "<" -> {
                    if (isNumber(a, b)) {
                        boolStack.push(Integer.parseInt(b) < Integer.parseInt(a));
                    } else if (isFloat(a, b)) {
                        boolStack.push(Float.parseFloat(b) < Float.parseFloat(a));
                    }
                }
                case ">" -> {
                    if (isNumber(a, b)) {
                        boolStack.push(Integer.parseInt(b) > Integer.parseInt(a));
                    } else if (isFloat(a, b)) {
                        boolStack.push(Float.parseFloat(b) > Float.parseFloat(a));
                    }
                }
                case "!" -> {
                    if (isNumber(a, b)) {
                        boolStack.push(Integer.parseInt(b) != Integer.parseInt(a));
                    } else if (isFloat(a, b)) {
                        int result = Float.compare(Float.parseFloat(a), Float.parseFloat(b));
                        boolStack.push(result != 0);
                    } else {
                        boolStack.push(!a.equals(b));
                    }
                }
                case "=" -> {
                    if (isNumber(a, b)) {
                        boolStack.push(Integer.parseInt(b) == Integer.parseInt(a));
                    } else if (isFloat(a, b)) {
                        int result = Float.compare(Float.parseFloat(a), Float.parseFloat(b));
                        boolStack.push(result == 0);
                    } else {
                        boolStack.push(a.equals(b));
                    }
                }
                case "&" -> boolStack.push(c && d);
                case "|" -> boolStack.push(c || d);
            }
        }
        return boolStack.pop();
    }

    private static Boolean isNumber(String a, String b) {
        int newA = 0;
        int newB = 0;
        try {
            newA = Integer.parseInt(a);
            newB = Integer.parseInt(b);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Boolean isFloat(String a, String b) {
        float newA = 0.0F;
        float newB = 0.0F;
        try {
            newA = Float.parseFloat(a);
            newB = Float.parseFloat(b);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

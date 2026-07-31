
import java.util.*;

public class ExpressionTree {

    private static class Node {
        String val;
        Node left, right;
        Node(String v) { val = v; }
        boolean isOperator() {
            return val.length() == 1 && "+-*/^".indexOf(val.charAt(0)) >= 0;
        }
    }

    // Build from postfix tokens (space separated tokens expected)
    public static Node buildFromPostfix(String postfix) {
        if (postfix == null || postfix.trim().isEmpty()) return null;
        Deque<Node> stack = new ArrayDeque<>();
        String[] tokens = tokenize(postfix);
        for (String t : tokens) {
            if (t.isEmpty()) continue;
            if (isOperatorToken(t)) {
                if (stack.size() < 2) throw new IllegalArgumentException("Invalid postfix expression");
                Node right = stack.pop();
                Node left = stack.pop();
                Node op = new Node(t);
                op.left = left;
                op.right = right;
                stack.push(op);
            } else {
                stack.push(new Node(t));
            }
        }
        if (stack.size() != 1) throw new IllegalArgumentException("Invalid postfix expression");
        return stack.pop();
    }

    // Build from infix expression (like "3 + 4 * (2 - 1)")
    public static Node buildFromInfix(String infix) {
        String postfix = infixToPostfix(infix);
        return buildFromPostfix(postfix);
    }

    // Evaluate tree (integer arithmetic)
    public static long evaluate(Node root) {
        if (root == null) throw new IllegalArgumentException("Empty tree");
        if (!root.isOperator()) {
            return Long.parseLong(root.val);
        }
        long l = evaluate(root.left);
        long r = evaluate(root.right);
        switch (root.val.charAt(0)) {
            case '+': return l + r;
            case '-': return l - r;
            case '*': return l * r;
            case '/':
                if (r == 0) throw new ArithmeticException("Division by zero");
                return l / r;
            case '^': return (long) Math.pow(l, r);
            default: throw new UnsupportedOperationException("Unknown operator " + root.val);
        }
    }

    // Traversals
    public static String toPostfix(Node root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        post(root, sb);
        return sb.toString().trim();
    }
    private static void post(Node n, StringBuilder sb) {
        if (n == null) return;
        post(n.left, sb);
        post(n.right, sb);
        sb.append(n.val).append(' ');
    }

    public static String toPrefix(Node root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        pre(root, sb);
        return sb.toString().trim();
    }
    private static void pre(Node n, StringBuilder sb) {
        if (n == null) return;
        sb.append(n.val).append(' ');
        pre(n.left, sb);
        pre(n.right, sb);
    }

    public static String toInfix(Node root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        in(root, sb);
        return sb.toString().trim();
    }
    private static void in(Node n, StringBuilder sb) {
        if (n == null) return;
        if (n.isOperator()) sb.append('(');
        in(n.left, sb);
        sb.append(n.val);
        in(n.right, sb);
        if (n.isOperator()) sb.append(')');
    }

    // Helpers: tokenize supports multi-digit and negative numbers when separated by spaces or from infix conversion
    private static String[] tokenize(String s) {
        return s.trim().split("\\s+");
    }

    private static boolean isOperatorToken(String t) {
        return t.length() == 1 && "+-*/^".indexOf(t.charAt(0)) >= 0;
    }

    // Infix to postfix using shunting-yard (handles parentheses, operator precedence, left/right associativity)
    private static String infixToPostfix(String infix) {
        if (infix == null) return "";
        List<String> output = new ArrayList<>();
        Deque<String> ops = new ArrayDeque<>();
        // tokenize infix: numbers, operators, parens
        List<String> tokens = splitInfixTokens(infix);
        for (String tok : tokens) {
            if (tok.isEmpty()) continue;
            if (isNumber(tok)) {
                output.add(tok);
            } else if (isOperatorToken(tok)) {
                while (!ops.isEmpty() && isOperatorToken(ops.peek())) {
                    String top = ops.peek();
                    if ((isLeftAssociative(tok) && prec(tok) <= prec(top)) ||
                            (!isLeftAssociative(tok) && prec(tok) < prec(top))) {
                        output.add(ops.pop());
                    } else break;
                }
                ops.push(tok);
            } else if (tok.equals("(")) {
                ops.push(tok);
            } else if (tok.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) output.add(ops.pop());
                if (ops.isEmpty() || !ops.peek().equals("(")) throw new IllegalArgumentException("Mismatched parentheses");
                ops.pop();
            } else {
                throw new IllegalArgumentException("Unknown token: " + tok);
            }
        }
        while (!ops.isEmpty()) {
            String t = ops.pop();
            if (t.equals("(") || t.equals(")")) throw new IllegalArgumentException("Mismatched parentheses");
            output.add(t);
        }
        return String.join(" ", output);
    }

    private static List<String> splitInfixTokens(String s) {
        List<String> tokens = new ArrayList<>();
        int i = 0, n = s.length();
        while (i < n) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            if (c == '(' || c == ')' || "+-*/^".indexOf(c) >= 0) {
                // handle unary minus: if '-' and it's start or previous token is operator or '(' then it's unary part of number
                if (c == '-') {
                    boolean unary = (tokens.isEmpty() ||
                            tokens.get(tokens.size()-1).equals("(") ||
                            isOperatorToken(tokens.get(tokens.size()-1)));
                    if (unary) {
                        // parse number with leading minus
                        int j = i + 1;
                        StringBuilder num = new StringBuilder("-");
                        // read digits
                        while (j < n && (Character.isDigit(s.charAt(j)))) {
                            num.append(s.charAt(j));
                            j++;
                        }
                        if (num.length() == 1) { // only '-', treat as minus operator
                            tokens.add("-");
                            i++;
                        } else {
                            tokens.add(num.toString());
                            i = j;
                        }
                        continue;
                    }
                }
                tokens.add(String.valueOf(c));
                i++;
            } else if (Character.isDigit(c)) {
                int j = i;
                StringBuilder num = new StringBuilder();
                while (j < n && Character.isDigit(s.charAt(j))) {
                    num.append(s.charAt(j));
                    j++;
                }
                tokens.add(num.toString());
                i = j;
            } else {
                throw new IllegalArgumentException("Invalid character in expression: " + c);
            }
        }
        return tokens;
    }

    private static boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        int i = (s.charAt(0) == '-') ? 1 : 0;
        if (i == 1 && s.length() == 1) return false;
        for (; i < s.length(); i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    private static int prec(String op) {
        char c = op.charAt(0);
        switch (c) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
            default: return -1;
        }
    }
    private static boolean isLeftAssociative(String op) {
        return op.charAt(0) != '^';
    }

    // Demo
    public static void main(String[] args) {
        // Example 1: build from postfix (tokens separated by spaces)
        String postfix = "3 4 2 * 1 5 - 2 3 ^ ^ / +";
        System.out.println("Postfix input: " + postfix);
        Node root1 = buildFromPostfix(postfix);
        System.out.println("Reconstructed infix: " + toInfix(root1));
        System.out.println("Prefix: " + toPrefix(root1));
        System.out.println("Postfix: " + toPostfix(root1));
        try { System.out.println("Evaluated value: " + evaluate(root1)); } catch (Exception e) { System.out.println("Eval error: " + e.getMessage()); }
        System.out.println();

        // Example 2: build from infix
        String infix = "3 + 4 * (2 - 1)";
        System.out.println("Infix input: " + infix);
        Node root2 = buildFromInfix(infix);
        System.out.println("Converted postfix: " + infixToPostfix(infix));
        System.out.println("Reconstructed infix: " + toInfix(root2));
        System.out.println("Prefix: " + toPrefix(root2));
        System.out.println("Postfix: " + toPostfix(root2));
        System.out.println("Evaluated value: " + evaluate(root2));
    }
}

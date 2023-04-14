public class ExpressionBuilder {
    public static String createExpression(String filter, String[] values) {
        String copy = filter;

        int i = copy.indexOf("column");
        while (i != -1) {
            int j = i + 6; // [
            while (copy.charAt(j) != ']') j++;
            int index = Integer.parseInt(copy.substring(i + 7, j)) - 1;
            String value = values[index];
            copy = copy.replace(copy.substring(i, j + 1), String.valueOf(value));
            i = copy.indexOf("column");
        }

        return copy;
    }
}

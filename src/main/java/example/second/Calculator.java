package example.second;

public class Calculator {

    public static String performAnOperation(String a, String action, String b) {
        if (!a.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            return "Некорректное значение";
        }
        if (!b.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            return "Некорректное значение";
        }
        if (" ".equals(action)){
            action = "+";
        } else if (action.matches("[^-/*]")) {
            return "Некорректный символ";
        }

        double result = 0d;
        switch(action) {
            case "-" : result = Double.parseDouble(a) - Double.parseDouble(b);
                break;
            case "+" : result = Double.parseDouble(a) + Double.parseDouble(b);
                break;
            case "/" : result = Double.parseDouble(a) / Double.parseDouble(b);
                break;
            case "*" : result = Double.parseDouble(a) * Double.parseDouble(b);
                break;
        }

        return String.valueOf(result);
    }
}

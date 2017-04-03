package unit;

public class DataUnit {

    public static String intToStr(int num, int len) {
        String tmpStr = String.valueOf(num);
        String resultStr = null;
        if (isNull(tmpStr)) {
            resultStr = getSpaces(len);
        }
        else {
            resultStr = getSpaces(len - tmpStr.length()) + tmpStr;
        }
        return resultStr;
    }

    public static String intToStr(int num) {
        return intToStr(num, 0);
    }

    private static String getSpaces(int len) {
        String spaces = "";
        for (int i = 0; i < len; i++ ) {
            spaces += " ";
        }
        return spaces;
    }

    private static boolean isNull(String tmpStr) {
        if (tmpStr == null || tmpStr.length() == 0) {
            return true;
        }
        return false;
    }
}

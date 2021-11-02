import java.util.Arrays;

class Sorter {

    public static void sortStrings(String[] arr) {
        // TODO: implement your sorting function here
        for (int i = 1; i < arr.length; i++) {
            String key = arr[i];
            int j = i - 1;
            while (j >= 0 && isGreaterThan(arr[j], key)) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }

    }

    public static boolean isGreaterThan(String str1, String str2) {
        if (str1.charAt(0) > str2.charAt(0)) {
            return true;
        } else if (str1.charAt(0) == str2.charAt(0)) {
            return str1.charAt(1) > str2.charAt(1);
        } else {
            return false;
        }
    }

}

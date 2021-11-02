

public class Main {
    public static void main(String[] args) {
        System.out.println((int) '0');
        String str = "abc";
        System.out.println(str.substring(3));
        for (int x = 0; x < 6; x++){
            if (x == 3) {
                continue;
            }
            System.out.println( x +" here");
        }


    }
}

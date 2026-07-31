import java.util.Scanner;

public class Frequencyofstr {
    public static void main(String[] args) {
        {
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            System.out.println("String :" + str);
            int hash[] = new int[127];
            for (int i = 0; i < 127; i++) {
                hash[i] = 0;
            }
            for (int i = 0; i < str.length(); i++) {
                int ind = (int) str.charAt(i);
                hash[ind]++;
            }
            for (int i = 97; i < 122; i++) {
                if (hash[i] > 0) {
                    System.out.println((char) i + "->" + hash[i]);
                }
            }
            for (int i = 65; i < 90; i++) {
                if (hash[i] > 0) {
                    System.out.println((char) i + "->" + hash[i]);
                }
            }
        }
    }

}
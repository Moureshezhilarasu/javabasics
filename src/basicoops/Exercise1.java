package basicoops;
import java.util.Scanner;
/*
@param : im getting the name in string

@return : a string of employee details / void

*/

public class Exercise1 {
    static String name="Akash";

public static  void display(){
    System.out.println("Welcome"+name);
}




    public static void main(String[] args) {
        display();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter N value");
        int n=in.nextInt();
        int arr[]=new int[n];
        for(int i=0;i<arr.length;i++){
            System.out.println("Enter Arr["+i+"]:");
            arr[i]=in.nextInt();
        }
        System.out.println("Array Elements: ");
        for(int j=0;j<arr.length;j++){
            System.out.print(arr[j]+" ");
        }
    }

}


 class HexawareQuestion1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read the input string
        if (!scanner.hasNextLine()) return;
        String cards = scanner.nextLine().trim();
        
        // Arrays to map and track data for suits: S->0, C->1, H->2, D->3
        boolean[][] seen = new boolean[4][14]; // 14 to accommodate 1-indexed card numbers (1 to 13)
        boolean[] duplicate = new boolean[4];
        int[] uniqueCount = new int[4];
        
        // Iterate through the string, parsing every 3 characters
        for (int i = 0; i < cards.length(); i += 3) {
            char suit = cards.charAt(i);
            int num = Integer.parseInt(cards.substring(i + 1, i + 3));
            
            int suitIndex = -1;
            switch (suit) {
                case 'S': suitIndex = 0; break;
                case 'C': suitIndex = 1; break;
                case 'H': suitIndex = 2; break;
                case 'D': suitIndex = 3; break;
            }
            
            if (suitIndex != -1) {
                // If the card is already marked as seen, flag a duplicate
                if (seen[suitIndex][num]) {
                    duplicate[suitIndex] = true;
                } else {
                    // Otherwise, mark it seen and increment the suit's unique card count
                    seen[suitIndex][num] = true;
                    uniqueCount[suitIndex]++;
                }
            }
        }
        
        // Construct standard output ensuring no arbitrary strings
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (duplicate[i]) {
                output.append("DUPLICATE");
            } else {
                output.append(13 - uniqueCount[i]);
            }
            
            // Add a space between outputs, but not at the very end
            if (i < 3) {
                output.append(" ");
            }
        }
        
        System.out.println(output.toString());
        scanner.close();
    }
}


class HexawareQuestion2 {
    // Helper class to store mat dimensions and precalculated area
    static class Mat {
        int l, b, area;
        Mat(int l, int b) {
            this.l = l;
            this.b = b;
            this.area = l * b;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        if (!scanner.hasNextInt()) {
            return;
        }
        
        int n = scanner.nextInt();
        Mat[] mats = new Mat[n];
        
        // Read input and populate the array of mats
        for (int i = 0; i < n; i++) {
            int l = scanner.nextInt();
            int b = scanner.nextInt();
            mats[i] = new Mat(l, b);
        }
        
        int minDiff = Integer.MAX_VALUE;
        Mat mat1 = null;
        Mat mat2 = null;
        
        // Compare every pair of mats to find the minimum area difference
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int diff = Math.abs(mats[i].area - mats[j].area);
                
                // If a new minimum difference is found, update the selected mats
                if (diff < minDiff) {
                    minDiff = diff;
                    mat1 = mats[i];
                    mat2 = mats[j];
                }
            }
        }
        
        // Print the result exactly as expected (no extra strings)
        if (mat1 != null && mat2 != null) {
            System.out.println(mat1.l + " " + mat1.b);
            System.out.println(mat2.l + " " + mat2.b);
        }
        
        scanner.close();
    }
}



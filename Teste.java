public class Teste{
    public static void main(String[] args) {
        String line = "label: 1000110101";
        String line2 = "1000110101";

        String[] campos = line2.split(":");
        System.out.println(campos[0]);
    } 
}
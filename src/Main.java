import controller.Connector;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
//        int[] sizes = {100};
//        int[] numPerson = {10, 50, 100, 400, 700, 1000, 2000, 3000, 4000, 5000, 6000, 7000,8000,9000,10000};
//        int[] numPerson = {6000};
//
//        for (int i = 0; i < sizes.length; i++) {
//            for (int j = 0; j < numPerson.length; j++) {
//                long moyenne=0;
//                for (int k = 0; k < 10; k++) {
//                    moyenne += new Connector(sizes[i], numPerson[j]).executeSimulation();
//                }
//                System.out.println(sizes[i] + "," + numPerson[j] + "," + moyenne/10);
//            }
//        }
        System.out.println(new Connector(100, 10000).executeSimulation());
    }
}

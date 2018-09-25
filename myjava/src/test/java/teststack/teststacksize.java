package teststack;

import org.junit.Test;

public class teststacksize {
    @Test
    public static void main(String[] args) throws Exception {

//    byte[] bytes = new byte[6];
//    teststacksizemethord(1);
        // testGC();
        testProcess();
    }


    public static void teststacksizemethord(int n) {

        System.out.println(n);
        n++;
        teststacksizemethord(n);

    }

    public static void testJVM() throws InterruptedException {

        Thread.sleep(1000);

    }

    public static void testGC() {
        int n = 1024 * 1024 * 6;

        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {

            byte[] bytes = new byte[n];

        }

        System.out.println(System.nanoTime() - start);


    }

    @Test
    public static void testProcess() throws Exception {
        //5种gc算法
        String[] gcs = {
                "UseSerialGC",
                "UseParallelGC",
                "UseParallelOldGC",
                "UseConcMarkSweepGC",
                "UseG1C"
        };
        Runtime r = Runtime.getRuntime();

        for (String gc : gcs) {
            System.out.print(gc + "\t: ");
            for (int i = 0; i < 3; i++) {
                String javapc = String.format("java -Xms500m -Xmx500m -XX:NewSize=7m -XX:MaxNewSize=7m -XX:SurvivorRatio=5 -XX:+%s -cp d:/ceshi/jvmGC/ java Hello 10000 6000000", gc);
                long start = System.nanoTime();
                Process p = r.exec(javapc);
                p.waitFor();
                System.out.print((System.nanoTime() - start) + "\t");
            }
            System.out.println();
        }

    }
}

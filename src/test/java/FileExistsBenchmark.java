import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Time taken by new File().exists(): 7865 ms
 * Time taken by Files.exists(): 18125 ms
 */
public class FileExistsBenchmark
{

    @Test
    public void testFileExists() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            new File("testFile.txt").exists();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken by new File().exists(): " + (end - start) + " ms");
    }

    @Test
    public void testFilesExists() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Files.exists(Paths.get("testFile.txt"));
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken by Files.exists(): " + (end - start) + " ms");
    }
}
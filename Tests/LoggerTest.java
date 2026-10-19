import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerTest
{
    private static final Path folderpath = Path.of("Output_Files/Test_Files");

    @BeforeAll
    public static void createDirectory() throws IOException
    {
        Logger.enabled(true);
        Files.createDirectories(folderpath);
    }

    @AfterAll
    public static void removeDirectory() throws IOException
    {
        if (Files.exists(folderpath))
        {
            var child = Files.walk(folderpath).toList();
            child = child.reversed();
            for(var path : child)
            {
                Files.delete(path);
            }
        }
    }


    @Test
    public void DoesCreateFile()
    {
        Logger.FileCreator(() -> "Test_Files/Test_Write_File.txt");

        var txtpath = folderpath.resolve("Test_Write_File.txt");
        assertTrue(Files.exists(txtpath));
    }
}

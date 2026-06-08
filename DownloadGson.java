import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URL;

public class DownloadGson {
    public static void main(String[] args) throws Exception {
        String url = "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar";
        String outDir = "e:/LTjava/Duanjava/lib";
        Files.createDirectories(Paths.get(outDir));
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(outDir + "/gson-2.10.1.jar"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Gson downloaded successfully!");
    }
}

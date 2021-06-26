package html2csv;

import java.nio.file.Path;

public class HTML2CSV {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Too few parameters!");
        } else {
            HTML2CSV html2CSV = new HTML2CSV();
            String source = html2CSV.getSoutrce(args);
            String target = html2CSV.getTarget(args);

            FileMethods fileMethods = new FileMethods();
            fileMethods.readFromFile(Path.of(source));
            fileMethods.writeToFile(Path.of(target));
        }
    }

    private String getSoutrce(String[] args) {
        return args[0];
    }

    private String getTarget(String[] args) {
        return args[1];
    }
}
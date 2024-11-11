package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;


import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Parameters(index = "0", description = "path to first file")
    private Path filepath1;

    @Parameters(index = "1", description = "path to second file")
    private Path filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format;

    @Override
    public Integer call() throws Exception { // business logic goes here...
        String diff =
                filepath1 != null && filepath2 != null
                        ? Differ.generate(filepath1, filepath2) : "Path null";

        var outputFormat = format == null ? "stylish" : format;
        switch (outputFormat) {
            case "plain" -> System.out.println("Soon i do a plain format for diff method");
            case "stylish" -> System.out.println(diff);
            default -> System.out.println(
                    "Wrong output format, "
                    + "type \"stylish\" or \"plain\". "
                    + "If the format is not written, "
                    + "then the standard output format \"stylish\" will be used");
        }
        return 0;
    }
}

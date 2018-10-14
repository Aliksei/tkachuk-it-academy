package by.itacademy.utils;


import by.itacademy.exceptions.ScriptNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import static by.itacademy.utils.DatabaseProperties.DATABASE_SCRIPTS_PATH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlQueryManager {

    public static synchronized String getSqlQuery(final String scriptName){
        Path scriptPath = resolvePath(scriptName);
        if (scriptPath != null){
            try {
                byte[] encoded = Files.readAllBytes(scriptPath);
                return new String(encoded);

            }catch (IOException e){
                throw new ScriptNotFoundException(String.format("Error during reading file in directory find script file by name [%s]",scriptName), e.getCause());
            }
        }
        throw new ScriptNotFoundException(String.format("There is no script file in directory , file name [%s]", scriptName));
    }

    private static Path resolvePath(final String scriptName){
        final Path scriptPath;
        try(Stream<Path> paths = Files.walk(Paths.get(DATABASE_SCRIPTS_PATH),Integer.MAX_VALUE)) {
            scriptPath = paths.filter(path -> path.getFileName().toString().equals(scriptName) && Files.isRegularFile(path))
                    .findFirst()
                    .orElse(null);

        }catch (IOException e){
            throw new ScriptNotFoundException(String.format("Error during resolving directory for script file - [%s]", scriptName), e.getCause());
        }
        return scriptPath;
    }

}

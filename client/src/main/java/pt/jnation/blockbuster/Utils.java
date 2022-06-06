package pt.jnation.blockbuster;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public class Utils {

    public static void print(Object o) {
        ObjectWriter mapper = new ObjectMapper().writerWithDefaultPrettyPrinter();
        try {
            mapper.writeValue(System.out, o);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

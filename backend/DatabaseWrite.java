package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseWrite {
    private static DatabaseWrite instance;
    private DatabaseWrite()
    {

    }
    public static DatabaseWrite getInstance()
    {
        if(instance==null)
        {
            instance=new DatabaseWrite();
        }
        return instance ;
    }
    public <T>   void write(String filename, ArrayList<T> data) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File(filename);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}

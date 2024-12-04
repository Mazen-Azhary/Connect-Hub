package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseRead {
    private static DatabaseRead instance;
    private DatabaseRead()
    {

    }
    public static DatabaseRead getInstance()
    {
        if(instance==null)
        {
            instance=new DatabaseRead();
        }
        return instance ;
    }
    public<T> ArrayList<T> read(String filename,Class<T> c)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ArrayList<T> data = new ArrayList<>();
        try
        {
            data = mapper.readValue(new File(filename),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, c));
        }
        catch (IOException e)
        {
        }
        return data;

    }
}

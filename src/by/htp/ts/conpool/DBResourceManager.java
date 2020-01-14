package by.htp.ts.conpool;

import java.util.ResourceBundle;

public class DBResourceManager {
    private ResourceBundle rb;

    public DBResourceManager(){
        rb=ResourceBundle.getBundle("by.htp.ts.resources.db");
    }

    public String getValue(String key){
        return rb.getString(key);
    }
}

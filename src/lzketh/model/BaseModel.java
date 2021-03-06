package lzketh.model;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseModel {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = this.getClass().getFields();
        sb.append("(");
        for (Field f: fields) {
            try {
                Object v = f.get(this);
                String s = String.format("%s: %s, ", f.getName(), v);
                sb.append(s);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sb.append(")");
        return sb.toString();
    }
}


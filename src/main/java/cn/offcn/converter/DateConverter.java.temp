package cn.offcn.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {

        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(s);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}

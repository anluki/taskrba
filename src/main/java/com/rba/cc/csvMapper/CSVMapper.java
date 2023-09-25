package com.rba.cc.csvMapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class CSVMapper {

	
	public static String convertToCSV(Object object, boolean skipId) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);

        StringBuilder sb = new StringBuilder();

        fieldList.forEach(field -> {
            field.setAccessible(true);
            try {
            	if (skipId) {
            		if(field.getName() != "id" ) {
                		sb.append(field.get(object)).append("|");
                	}	
            	} else {
            		sb.append(field.get(object)).append("|");
            	}
            	
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
	
}

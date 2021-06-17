package org.fleet.modules.deve.autocode.generate.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SimpleFormat {
    public static String underlineToHump(String para) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] arrayOfString = para.split("_");
        for (String str : arrayOfString) {
            if (!para.contains("_")) {
                stringBuilder.append(str);
            } else if (stringBuilder.length() == 0) {
                stringBuilder.append(str.toLowerCase());
            } else {
                stringBuilder.append(str.substring(0, 1).toUpperCase());
                stringBuilder.append(str.substring(1).toLowerCase());
            }
        }
        return stringBuilder.toString();
    }

    public static String humpToUnderline(String para) {
        StringBuilder stringBuilder = new StringBuilder(para);
        byte b = 0;
        if (!para.contains("_"))
            for (byte b1 = 0; b1 < para.length(); b1++) {
                if (Character.isUpperCase(para.charAt(b1))) {
                    stringBuilder.insert(b1 + b, "_");
                    b++;
                }
            }
        if (stringBuilder.toString().toLowerCase().startsWith("_"))
            return stringBuilder.toString().toLowerCase().substring(1);
        return stringBuilder.toString().toLowerCase();
    }

    public static String humpToShortbar(String para) {
        StringBuilder stringBuilder = new StringBuilder(para);
        byte b = 0;
        if (!para.contains("-"))
            for (byte b1 = 0; b1 < para.length(); b1++) {
                if (Character.isUpperCase(para.charAt(b1))) {
                    stringBuilder.insert(b1 + b, "-");
                    b++;
                }
            }
        if (stringBuilder.toString().toLowerCase().startsWith("-"))
            return stringBuilder.toString().toLowerCase().substring(1);
        return stringBuilder.toString().toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(humpToShortbar("fleetDemo"));
    }

    public String number(Object obj) {
        obj = (obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj;
        if (obj.toString().equalsIgnoreCase("NaN"))
            return "NaN";
        return (new DecimalFormat("0.00")).format(Double.parseDouble(obj.toString()));
    }

    public String number(Object obj, String pattern) {
        obj = (obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj;
        if (obj.toString().equalsIgnoreCase("NaN"))
            return "NaN";
        return (new DecimalFormat(pattern)).format(Double.parseDouble(obj.toString()));
    }

    public String round(Object obj) {
        obj = (obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj;
        if (obj.toString().equalsIgnoreCase("NaN"))
            return "NaN";
        return (new DecimalFormat("0")).format(Double.parseDouble(obj.toString()));
    }

    public String currency(Object obj) {
        obj = (obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj;
        return NumberFormat.getCurrencyInstance(Locale.CHINA).format(obj);
    }

    public String timestampToString(Object obj, String pattern) {
        if (obj == null)
            return "";
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat1.parse(obj.toString());
        } catch (ParseException parseException) {
            parseException.printStackTrace();
            return "error";
        }
        return simpleDateFormat2.format(date);
    }

    public String percent(Object obj) {
        obj = (obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj;
        if (obj.toString().equalsIgnoreCase("NaN"))
            return "";
        return NumberFormat.getPercentInstance(Locale.CHINA).format(obj);
    }

    public String date(Object obj, String pattern) {
        if (obj == null)
            return "";
        return (new SimpleDateFormat(pattern)).format(obj);
    }

    public String date(Object obj) {
        if (obj == null)
            return "";
        return DateFormat.getDateInstance(1, Locale.CHINA).format(obj);
    }

    public String time(Object obj) {
        if (obj == null)
            return "";
        return DateFormat.getTimeInstance(3, Locale.CHINA).format(obj);
    }

    public String datetime(Object obj) {
        if (obj == null)
            return "";
        return DateFormat.getDateTimeInstance(1, 3, Locale.CHINA).format(obj);
    }

    public String getInStrs(List<String> params) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str1 : params)
            stringBuffer.append("'" + str1 + "',");
        String str = stringBuffer.toString();
        if (!"".equals(str) || str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
            return str;
        }
        return null;
    }
}

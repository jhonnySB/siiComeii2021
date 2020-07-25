package com.tiamex.siicomeii.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author cerimice **/
public class Utils{
    
    public static final String UNIVERSAL = "Etc/Universal";
    
    public static String getGoogleMapsApiKey(){
        return "AIzaSyBBIyd1mW5Yh0G2Z6kJSNN9XWtdKjAHsMY";
    }
    
    public static Level nivelLoggin(){
        return Level.INFO;
    }
    
    /* Obtiene la fecha en una zona horaria Universal | Retorna LocalDateTime */
    public static LocalDateTime getLocalDateTimeUniversal(){
        return ZonedDateTime.now(ZoneId.of(UNIVERSAL)).toLocalDateTime();
    }
    
    /* Obtiene la fecha en una zona horaria Universal | Retorna LocalDate */
    public static LocalDate getLocalDateUniversal(){
        return getLocalDateTimeUniversal().toLocalDate();
    }
    
    /* Obtiene la fecha en una zona horaria especifica basada en el servidor | Retorna LocalDateTime */
    public static LocalDateTime getLocalDateTimeZoneTime(String zoneId){
        return ZonedDateTime.now(ZoneId.of(zoneId)).toLocalDateTime();
    }
    /* Obtiene la fecha en una zona horaria especifica basada en el servidor | Retorna LocalDate */
    public static LocalDate getLocalDateZoneTime(String zoneId){
        return getLocalDateTimeZoneTime(zoneId).toLocalDate();
    }
    
    public static Date getDateUniversal(){
        try{
            return toDate(ZonedDateTime.now(ZoneId.of(UNIVERSAL)).toLocalDateTime());
        }catch (ParseException ex){
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    public static LocalDateTime getLocalDateTimeUniversalZone(LocalDateTime date,String timeZoneOrigin,String timeZoneDestination){
        ZonedDateTime dateZoned = ZonedDateTime.of(date,ZoneId.of(timeZoneOrigin));
        dateZoned = dateZoned.withZoneSameInstant(ZoneId.of(timeZoneDestination));
        return dateZoned.toLocalDateTime();
    }
    
    private static DateFormat formatId;
    public static DateFormat getFormatId(){
        if(formatId==null){formatId=new SimpleDateFormat("yyDDDkkmmssSSSSSS");}
        return formatId;
    }
    
    public static long getFormatIdLong(){
        return new Long(getFormatId().format(new Date()));
    }
    
    private static DateFormat formatShortDate;
    public static DateFormat getFormatShortDate(){
        if(formatShortDate==null){
            formatShortDate=new SimpleDateFormat("dd/MM/yyyy");
        }
        return formatShortDate;
    }
    
    private static DateFormat formatDateTime;
    public static DateFormat getFormatDateTime(){
        if(formatDateTime==null){
            formatDateTime=new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        }
        return formatDateTime;
    }
    
    public static LocalDateTime getUniversalTimeZone(){
        return ZonedDateTime.now(ZoneId.of("Etc/Universal")).toLocalDateTime();
    }
    
    public static LocalDateTime getEspecificTimeZone(String timeZone){
        return ZonedDateTime.now(ZoneId.of(timeZone)).toLocalDateTime();
    }
    
    public static LocalDate toLocalDate(Date date){
        return LocalDate.parse(Utils.getFormatShortDate().format(date),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    public static LocalDateTime toLocalDateTime(Date date){
        return LocalDateTime.parse(Utils.getFormatDateTime().format(date),DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm:ss"));
    }
    
    public static Date toDate(LocalDate date) throws ParseException{
        return getFormatShortDate().parse(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    
    public static Date toDate(LocalDateTime date) throws ParseException{
        return getFormatDateTime().parse(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm:ss")));
    }
    
    public static String replaceComaFromNumber(String number){
        number = number.replace(",", "");
        return number;
    }
    
    private static String tempFolder;
    public static String getTempFolder(){
        if(tempFolder == null){
            tempFolder = System.getProperty("java.io.tmpdir")+System.getProperty("file.separator");
        }
        return tempFolder;
    }
    
    public static String encryptMD5(String data) throws Exception{
        try{
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(data.getBytes(),0,data.length());
            
            //Converts message digest value in base 16 (hex)
            return new BigInteger(1, digest.digest()).toString(16);
        }catch(NoSuchAlgorithmException ex){
            throw ex;
        }
    }
    
    public static boolean validateFormatPassword(String string){
        return !string.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$");
    }
}

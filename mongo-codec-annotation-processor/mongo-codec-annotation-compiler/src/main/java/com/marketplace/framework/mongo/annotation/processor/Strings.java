package com.marketplace.framework.mongo.annotation.processor;

public class Strings {
  private static String toTitleCase(String str) {

    if(str == null || str.isEmpty())
      return "";

    if(str.length() == 1)
      return str.toUpperCase();

    //split the string by space
    String[] parts = str.split(" ");

    StringBuilder sb = new StringBuilder( str.length() );

    for(String part : parts){

      if(part.length() > 1 )
        sb.append( part.substring(0, 1).toUpperCase() )
            .append( part.substring(1).toLowerCase() );
      else
        sb.append(part.toUpperCase());

      sb.append(" ");
    }

    return sb.toString().trim();
  }


}

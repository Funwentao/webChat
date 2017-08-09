package fuc;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormat {

	
	
	public static String creSinFormAndByts(String str) throws UnsupportedEncodingException{
		if(null == str || "".equals(str.trim()))
			return "";
		return new String(str.getBytes("8859_1"),"UTF-8").trim().replace("'", "\\'");
	}

}

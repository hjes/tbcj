package cn.tb.util;

public class StringUtils {

	public static boolean isBlank(String str){
		if(str==null){
			return true;
		}else{
			if(str.equals(""))
			{
				return true;
			}else{
				return false;
			}
		}
	}
	
	public static boolean isNotBlank(String str){
		if(str!=null){
			if(!str.equals(""))
			{
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
}

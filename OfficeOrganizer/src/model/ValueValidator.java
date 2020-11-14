package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface ValueValidator {
	default boolean isTimeCorrect(int firstHour, int secondHour) {
		if (firstHour >= 0 && secondHour <= 23) {
			if (secondHour > firstHour) {
				return true;
			} else {return false;}
		} else {return false;}
	}
	
	default boolean isTextCorrect(String textToCheck) {
		Pattern regexp = Pattern.compile("[a-zA-Z¿Ÿæñó³ê¹œ¯Æ¥ŒÊ£ÓÑ]+");
		Matcher matcher = regexp.matcher(textToCheck);
		if (textToCheck == null) {
			System.out.println("1");
			return false;
		}
		else if (matcher.matches()) {
			return true;
		
//		int len = textToCheck.length();
//		for (int i = 0; i < len; i++) {
//			if ((textToCheck.charAt(i) == ' ')) {
//				System.out.println("2");
//				return false;
//			}
		}
		else return false;
	}
	
	default boolean isRoomCorrect(String officeBox) {
		if (officeBox.equals("001") || officeBox.equals("002") || officeBox.equals("003") || officeBox.equals("004") || officeBox.equals("005")) {
			System.out.println("3");
			return true;
		}
		return false;
	}


}

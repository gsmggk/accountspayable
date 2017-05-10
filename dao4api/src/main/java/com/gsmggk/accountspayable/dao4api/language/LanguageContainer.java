package com.gsmggk.accountspayable.dao4api.language;


public class LanguageContainer {

public static final String LNG_PREFIX = "LNG";
private static String language;

public static String getLanguage() {
	return language;
}

public static void setLanguage(String language) {
	LanguageContainer.language = language;
}

@Override
public String toString() {
	return "LanguageContainer [language=" + language + "]";
}

}

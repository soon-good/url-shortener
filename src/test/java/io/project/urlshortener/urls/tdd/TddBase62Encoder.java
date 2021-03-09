package io.project.urlshortener.urls.tdd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TddBase62Encoder {
	private final String BASE_62_LETTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	@Test
	@DisplayName("ASCII코드_출력해보기")
	void ASCII코드_출력해보기(){
		String mango = "Mango";
		for(int i=0; i<mango.length(); i++){
			System.out.println("Mango.charAt(" + i + ") :: " + (int)mango.charAt(i));
		}
	}

	@Test
	@DisplayName("ASCII 코드를 비트로 출력")
	void ASCII코드를_비트로_출력(){
		String mango = "Mango";
		StringBuffer bitStrBuffer = new StringBuffer();

		int asciiElement = (int)mango.charAt(0);
		int modular2_result = asciiElement % 2;
		int division2_result = asciiElement / 2;
		System.out.println("모듈러 2 :: " + modular2_result);
		System.out.println("몫 2 :: " + division2_result);
//		System.out.println("Mango.charAt(" + 0 + ") :: " + (int)mango.charAt(0));
	}

	@Test
	@DisplayName("문자열의 각 문자의 ASCII 코드를 비트로 출력")
	void 문자열의_각_문자의_ASCII_코드를_비트로_출력(){

	}
	
}

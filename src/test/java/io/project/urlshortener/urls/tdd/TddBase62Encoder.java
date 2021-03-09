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


	@Test
	@DisplayName("base62 encoding test #1")
	void testBase62Encoding1(){
		System.out.println(encode1(99999999));
	}

	private String encode1(int value){
		final StringBuffer sb = new StringBuffer();
		do{
			int i = value % 62;
			sb.append(BASE_62_LETTERS.charAt(i));
			value /= 62;
		}while(value>0);
		System.out.println(sb.toString());
		return sb.toString();
	}

	@Test
	@DisplayName("base62 encoding test #2")
	void testBase62Encoding2(){
		System.out.println(encode2(99999999));
	}

	private String encode2(long param){
		final StringBuffer sb = new StringBuffer();
		while (param > 0){
			sb.append(BASE_62_LETTERS.charAt((int) (param % 62)));
			param /= 62;
		}
		return sb.toString();
	}
}

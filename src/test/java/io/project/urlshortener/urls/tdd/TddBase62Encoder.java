package io.project.urlshortener.urls.tdd;

import java.util.ArrayList;
import java.util.List;
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
		String money = "Mango";
		int asciiElement = (int)money.charAt(0);
		String binaryString = Integer.toBinaryString(asciiElement);
		System.out.println("binaryString = " + binaryString);
	}

	@Test
	@DisplayName("문자열을 바이너리 디지트로 변환")
	void 문자열을_바이트로_변환(){
		String money = "Mango";
		char[] chars = money.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for(char c : chars){
			buffer.append(
				String.format("%8s", Integer.toBinaryString(c))
					.replaceAll(" ", "0")
			);
		}
		System.out.println(buffer.toString());
		System.out.println(buffer.toString().length());
	}

	@Test
	@DisplayName("문자열 여섯자리단위로 나누기 테스트")
	void 문자열_여섯자리_단위로_나누기_테스트(){
		String ex = "0123456789abcd";
		int max = ex.length()/6;
		for(int i=0; i<=max; i++){
			String tempSub = "";
			if(ex.length() < i*6+5){
				tempSub = ex.substring(i*6);
			}
			else{
				tempSub = ex.substring(i*6, i*6+6);
			}
			System.out.println(tempSub);
		}
	}

	@Test
	@DisplayName("6비트 단위 버퍼로 분할")
	void 버퍼로_나누어_담기(){
		String mango = "Mango";
		char[] chars = mango.toCharArray();
		StringBuffer buffer = new StringBuffer();

		for(char c : chars){
			buffer.append(
				String.format("%8s", Integer.toBinaryString(c))
					.replaceAll(" ", "0")
			);
		}

		List<String> binaryStrList = new ArrayList<>();

		String ex = buffer.toString();
		int max = ex.length()/6;
		for(int i=0; i<=max; i++){
			if(ex.length() < i*6+5){
				binaryStrList.add(ex.substring(i*6));
			}
			else{
				binaryStrList.add(ex.substring(i*6, i*6+6));
			}
		}
		System.out.println(binaryStrList);
	}

	@Test
	@DisplayName("문자열의 각 문자의 ASCII 코드를 비트로 출력")
	void 문자열의_각_문자의_ASCII_코드를_비트로_출력(){

	}

}

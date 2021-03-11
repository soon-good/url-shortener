package io.project.urlshortener.functions;

import java.util.function.Function;

/**
 * Base32 인코딩을 위한 공통 유틸 Function
 */
public class Base32EncoderFunctions {

	// BASE62 에서 원하는 문자만을 선택하여 5bit 기반으로 변경
	private static final String BASE_32_LETTERS = "0123456789ABCGHKMQTbceklmnopqxyz";

	/**
	 * 5bit문자열을 정수형 코드로 변환
	 */
	public static final Function<String, Integer> string5BitToIntegerConverter = (binaryString) -> {
		int sum = 0;
		for(int strIdx=0; strIdx<binaryString.length(); strIdx++){
			int t = Integer.parseInt(binaryString.substring(strIdx, strIdx + 1));
			sum = sum + (int)(t*Math.pow(2,4-strIdx));
		}
		return sum;
	};

	/**
	 * 5bit 를 BASE32 기반 문자열로 반환 (인코딩시 사용)
	 */
	public static final Function<String, String> base32Encoding = (binaryString) -> {
		int max = binaryString.length()/5;
		StringBuffer encodedBuffer = new StringBuffer();

		for(int i=0; i<max; i++){
			String each5BitsString = "";
			if(binaryString.length() < i*5+4){
				each5BitsString = binaryString.substring(i*5);
//				binaryStrBy5BitsList.add(binaryString.substring(i*5));
			}
			else{
				each5BitsString = binaryString.substring(i*5, i*5+5);
//				binaryStrBy5BitsList.add(binaryString.substring(i*5, i*5+5));
			}
//			binaryStrBy5BitsList.add(each5BitsString);

//			int sum = convertStrBinaryToInteger(each5BitsString);
			Integer sum = string5BitToIntegerConverter.apply(each5BitsString);
//			encodedBitsInteger.add(sum);
			encodedBuffer.append(BASE_32_LETTERS.charAt(sum));
		}

		return encodedBuffer.toString();
	};

	/**
	 * 8bit 문자열을 int 값으로 변환
	 */
	public static final Function<String, Integer> string8BitToIntegerConverter = (binaryString) -> {
		int sum = 0;
		for(int strIdx=0; strIdx<binaryString.length(); strIdx++){
			int t = Integer.parseInt(binaryString.substring(strIdx, strIdx + 1));
			sum = sum + (int)(t*Math.pow(2,7-strIdx));
		}
		return sum;
	};

	/**
	 * 8bit 문자열을 원본문자열로 변환
	 */
	public static final Function<String, String> base32Decoding = (inputString) -> {
		StringBuffer expectedStringBuffer = new StringBuffer();
		int max = inputString.length()/8;
		for (int i=0; i<max; i++){
			String eachExpectedString = "";

			if (i*8+7 > eachExpectedString.length()){
				eachExpectedString = inputString.substring(i*8);
			}
			else {
				eachExpectedString = inputString.substring(i*8, i*8+8);
			}
//			binaryStrBy8bitsList.add(eachExpectedString);
			Integer data = string8BitToIntegerConverter.apply(eachExpectedString);
			char c = (char) data.intValue();
//			System.out.println("data = " + Character.toString(c));
			expectedStringBuffer.append(c);
		}
		return expectedStringBuffer.toString();
	};
}

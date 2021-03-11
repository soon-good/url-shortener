package io.project.urlshortener.urls.tdd.encoding;

import com.google.common.hash.Hashing;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.apache.commons.codec.digest.MurmurHash3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TddBase32Encoder {
	private final String BASE_64_LETTERS 			= "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/";
	private final String BASE_62_LETTERS 			= "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private final String BASE_32_LETTERS 			= "0123456789ABCGHKMQTbceklmnopqxyz";	// BASE62 에서 원하는 문자만을 선택하여 5bit 기반으로 변경

	// 1) 문자열의 각 문자 하나를 8자리 문자열 기반 이진수로 변환
	// : (8bit, 즉 1바이트(1~255까지의 문자열을 표현가능한 나열)로 변환)
	Function<String,String> stringToBinaryStringConverter = (inputString) -> {
		char[] chars = inputString.toCharArray();
		StringBuffer buffer = new StringBuffer();

		for(char c : chars){
			buffer.append(
				String.format("%8s", Integer.toBinaryString(c))
					.replaceAll(" ", "0")
			);
		}

		System.out.println("buffer = " + buffer.toString() + ", length = " + buffer.toString().length());
		return buffer.toString();
	};

	// 2) 5bit 문자열을 int 값으로 변환
	Function<String, Integer> string5BitToIntegerConverter = (binaryString) -> {
		int sum = 0;
		for(int strIdx=0; strIdx<binaryString.length(); strIdx++){
			int t = Integer.parseInt(binaryString.substring(strIdx, strIdx + 1));
			sum = sum + (int)(t*Math.pow(2,4-strIdx));
		}
		return sum;
	};

	// 3) 8bit 문자열을 int 값으로 변환
	Function<String, Integer> string8BitToIntegerConverter = (binaryString) -> {
		int sum = 0;
		for(int strIdx=0; strIdx<binaryString.length(); strIdx++){
			int t = Integer.parseInt(binaryString.substring(strIdx, strIdx + 1));
			sum = sum + (int)(t*Math.pow(2,7-strIdx));
		}
		return sum;
	};

	// 4) 5bit 를 BASE32 기반 문자열로 변환 (인코딩시 사용)
	Function<String, String> base32Encoding = (binaryString) -> {
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

	// 5) 8bit 문자열을 원본문자열로 변환 (디코딩시 사용)
	Function<String, String> fullBinary8bitToStringConverter = (inputString) -> {
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

	/**
	 * 문자열 기반 이진수를 10진수로 변환
	 * @param binaryString
	 * @return
	 */
	private int convertStrBinaryToInteger(String binaryString){
		int sum = 0;
		for(int j=0; j<binaryString.length(); j++){
			int t = Integer.parseInt(binaryString.substring(j, j + 1));
			sum = sum + (int)(t*Math.pow(2,4-j));
		}
		return sum;
	}

	@Test
	@DisplayName("5비트_이진법_문자열을_숫자로_변환하여_BASE32문자값_또는_아스키문자_리턴")
	void 비트_이진법_문자열을_숫자로_변환하여_BASE32문자값_또는_아스키문자_리턴(){
		final String str = "10101";

		int integerM = convertStrBinaryToInteger(str);
		Assertions.assertThat(integerM).isEqualTo(21);
		System.out.println(integerM);

		String base32EncodedStr = String.valueOf(BASE_32_LETTERS.charAt(integerM));
		Assertions.assertThat(base32EncodedStr).isEqualTo("e");
		System.out.println(base32EncodedStr);
	}

	@Test
	@DisplayName("5비트_단위버퍼로_나눈후_BASE32_인코딩")
	void 단위버퍼로_나눈후_BASE32_인코딩(){

		// 1) 문자열의 각 문자 하나를 8자리 문자열 기반 이진수로 변환
		// : (8bit, 즉 1바이트(1~255까지의 문자열을 표현가능한 나열)로 변환)
		String moral = "Moral";
		String binaryString = stringToBinaryStringConverter.apply(moral);

		// 2) 8bit 단위로 표현된 비트 열을 5bit 단위 비트 단위 문자열로 변환하여 별도의 리스트에 저장
		// : BASE32 인코딩을 위한 5bit 단위 분할
		// : MSB 부터 5비트 단위로 bit 분할
		List<String> binaryStrBy5BitsList = new ArrayList<>();
		List<Integer> encodedBitsInteger = new ArrayList<>();

		String encodedResult = base32Encoding.apply(binaryString);
		Assertions.assertThat(encodedResult.length()).isEqualTo(8);
		System.out.println("encodedResult = " + encodedResult);

//		int max = binaryString.length()/5;
//		StringBuffer encodedBuffer = new StringBuffer();
//
//		for(int i=0; i<max; i++){
//			String each5BitsString = "";
//			if(binaryString.length() < i*5+4){
//				each5BitsString = binaryString.substring(i*5);
////				binaryStrBy5BitsList.add(binaryString.substring(i*5));
//			}
//			else{
//				each5BitsString = binaryString.substring(i*5, i*5+5);
////				binaryStrBy5BitsList.add(binaryString.substring(i*5, i*5+5));
//			}
//			binaryStrBy5BitsList.add(each5BitsString);
//
////			int sum = convertStrBinaryToInteger(each5BitsString);
//			Integer sum = string5BitToIntegerConverter.apply(each5BitsString);
//			encodedBitsInteger.add(sum);
//			encodedBuffer.append(BASE_32_LETTERS.charAt(sum));
//		}
//		System.out.println("encodedBuffer \t\t:: " + encodedBuffer.toString());
//		System.out.println("encodedBits List \t:: " + encodedBitsInteger);
//		System.out.println("binaryStrList \t\t:: " + binaryStrBy5BitsList);
//		System.out.println();
//
//		Assertions
//			.assertThat(encodedBuffer.toString().length())
//			.isEqualTo(8);
	}

	@Test
	@DisplayName("인덱스 맵 만들기")
	void 인덱스맵_만들기(){
		Map<String, Integer> indexMap = new HashMap<>();
		for(int i=0; i<BASE_32_LETTERS.length(); i++){
			String strCharAt = String.valueOf(BASE_32_LETTERS.charAt(i));
			indexMap.put(strCharAt, i);
		}
		System.out.println("indexMap = " + indexMap);
	}

	private Map<String, Integer> generateIndexMap(String targetStr){
		Map<String, Integer> indexMap = new HashMap<>();
		for(int i=0; i<targetStr.length(); i++){
			String strCharAt = String.valueOf(targetStr.charAt(i));
			indexMap.put(strCharAt, i);
		}
		System.out.println("indexMap = " + indexMap);
		return indexMap;
	}

	@Test
	@DisplayName("디코딩_테스트")
	void 디코딩_테스트(){
		String shortUrl = "9ell4mBC";
		String expected = "Moral";

		Map<String, Integer> indexMap = generateIndexMap(BASE_32_LETTERS);
		StringBuffer expectedStringBinaryBuffer = new StringBuffer();
		for(int i=0; i<shortUrl.length(); i++){
			String eachChar = String.valueOf(shortUrl.charAt(i));
			int shortUrlIndex = indexMap.get(eachChar);

			String binaryString8s = String.format("%5s", Integer.toBinaryString(shortUrlIndex)).replaceAll(" ", "0");

			System.out.println(
				"eachChar = " + eachChar +
				",\t shortUrlIndex = " + shortUrlIndex +
				",\t indexOf = " + BASE_32_LETTERS.indexOf(eachChar) +
				",\t binaryString = " + Integer.toBinaryString(shortUrlIndex) +
				",\t binaryString8s = " + binaryString8s
			);

			expectedStringBinaryBuffer.append(binaryString8s);

//			String.format("%8s", Integer.toBinaryString(shortUrlIndex))
//				.replaceAll(" ", "0");
		}

		String expectedBinaryString = expectedStringBinaryBuffer.toString();
		System.out.println("expectedBinaryString = " + expectedBinaryString);

		String decodedString = fullBinary8bitToStringConverter.apply(expectedBinaryString);
		System.out.println("decodedString = " + decodedString);
		Assertions.assertThat(decodedString).isEqualTo(expected);

		// 아래 로직은 람다로 공통화한 코드이기는 한데, 아직은 고민중이다. 원시적인 코드가 더 좋을때가 있다는 생각 때문.
//		StringBuffer expectedStringBuffer = new StringBuffer();
//		int max = expectedBinaryString.length()/8;
//		for (int i=0; i<max; i++){
//			String eachExpectedString = "";
//
//			if (i*8+7 > eachExpectedString.length()){
//				eachExpectedString = expectedBinaryString.substring(i*8);
//			}
//			else {
//				eachExpectedString = expectedBinaryString.substring(i*8, i*8+8);
//			}
////			binaryStrBy8bitsList.add(eachExpectedString);
//			Integer data = string8BitToIntegerConverter.apply(eachExpectedString);
//			char c = (char) data.intValue();
////			System.out.println("data = " + Character.toString(c));
//			expectedStringBuffer.append(c);
//		}

//		String result = expectedStringBuffer.toString();

//		System.out.println("result = " + result);
//		Assertions.assertThat(result).isEqualTo(expected);
	}


	@Test
	@DisplayName("Murmur3_32 테스트")
	void Murmur3_32_테스트(){
		String key = Hashing.murmur3_32(6).hashString("http://www.naver.com", Charset.defaultCharset())
			.toString();
		System.out.println("key = "+ key);
		String s = Hashing.murmur3_32(6).hashString(key, Charset.defaultCharset())
			.toString();
		System.out.println("s = " + s);
	}

	@Test
	@DisplayName("MurmurHash3 :: apache")
	void MurmurHash3(){
		String ex = "0123456789abcd";
		byte[] bytes = ex.getBytes();
		System.out.println(bytes);

		long[] longs = MurmurHash3.hash128x64(bytes, 0, bytes.length, 7);
		String s = Long.toBinaryString(longs[0]);
		System.out.println(s);
	}

}

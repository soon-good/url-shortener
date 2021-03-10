package io.project.urlshortener.urls.tdd.encoding;

import com.google.common.hash.Hashing;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
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

	// 2) 8bit 단위로 표현된 비트 열을 5bit 단위 비트 단위 문자열로 변환하여 별도의 리스트에 저장
	// : BASE32 인코딩을 위한 5bit 단위 분할
	// : MSB 부터 5비트 단위로 bit 분할
	Function<String, Integer> stringBitToIntegerConverter = (binaryString) -> {
		int sum = 0;
		for(int strIdx=0; strIdx<binaryString.length(); strIdx++){
			int t = Integer.parseInt(binaryString.substring(strIdx, strIdx + 1));
			sum = sum + (int)(t*Math.pow(2,4-strIdx));
		}
		return sum;
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
		StringBuffer encodedBuffer = new StringBuffer();

		int max = binaryString.length()/5;

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
			binaryStrBy5BitsList.add(each5BitsString);

//			int sum = convertStrBinaryToInteger(each5BitsString);
			Integer sum = stringBitToIntegerConverter.apply(each5BitsString);
			encodedBitsInteger.add(sum);
			encodedBuffer.append(BASE_32_LETTERS.charAt(sum));
		}
		System.out.println("encodedBuffer \t\t:: " + encodedBuffer.toString());
		System.out.println("encodedBits List \t:: " + encodedBitsInteger);
		System.out.println("binaryStrList \t\t:: " + binaryStrBy5BitsList);
		System.out.println();
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

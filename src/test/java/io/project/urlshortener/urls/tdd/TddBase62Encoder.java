package io.project.urlshortener.urls.tdd;

import com.google.common.hash.Hashing;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.MurmurHash3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TddBase62Encoder {
	private final String BASE_64_LETTERS 			= "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/";
	private final String BASE_62_LETTERS 			= "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private final String BASE_32_LETTERS 			= "0123456789ABCGHKMQTbceklmnopqxyz";	// BASE62 에서 원하는 문자만을 선택하여 5bit 기반으로 변경

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
	@DisplayName("5비트 단위 버퍼로 분할")
	void 버퍼로_나누어_담기(){
		String mango = "Moral";
		char[] chars = mango.toCharArray();
		StringBuffer buffer = new StringBuffer();

		for(char c : chars){
			buffer.append(
				String.format("%8s", Integer.toBinaryString(c))
					.replaceAll(" ", "0")
			);
		}

		System.out.println("buffer = " + buffer.toString() + ", length = " + buffer.toString().length());
		List<String> binaryStrList = new ArrayList<>();
		String ex = buffer.toString();

		// MSB 부터 5비트 단위로 bit 분할
		int max = ex.length()/5;
		for(int i=0; i<=max; i++){
			if(ex.length() < i*5+4){
				binaryStrList.add(ex.substring(i*5));
			}
			else{
				binaryStrList.add(ex.substring(i*5, i*5+5));
			}
		}

		System.out.println("binaryStrList =======");
		binaryStrList.stream().forEach(System.out::println);

		System.out.println("encodeBase32Str =======");

		List<Integer> encodedBits = new ArrayList<>();

		// 2진수 변환
		binaryStrList.stream().forEach(strBits->{
			int sum = 0;
			for(int i=0; i<strBits.length(); i++){
				int t = Integer.parseInt(strBits.substring(i, i + 1));
				sum = sum + (int)(t*Math.pow(2,4-i));
			}
			encodedBits.add(sum);
		});

		for(Integer i : encodedBits){
			System.out.println(i);
		}
	}

	@Test
	@DisplayName("문자열의 각 문자의 ASCII 코드를 비트로 출력")
	void 문자열의_각_문자의_ASCII_코드를_비트로_출력(){

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

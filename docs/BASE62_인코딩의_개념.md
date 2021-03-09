# 참고자료
- [Base 62](https://en.wikipedia.org/wiki/Base62)
- [Base64 인코딩](https://ko.wikipedia.org/wiki/%EB%B2%A0%EC%9D%B4%EC%8A%A464)


# BASE62의 개념
BASE62 에 대한 자료는 [여기](https://en.wikipedia.org/wiki/Base62) 에 있다. 그런데 이게 뭐지? 하면서 잠깐당황했었다.  
  
차분하게 웹 검색을 해본 결과 BASE64 와 BASE62의 차이점을 설명하는 글들이 있었고, BASE62의 경우 BASE64 인코딩에서 `/`, `+` 을 빼고서 인코딩하는 개념으로 방향이 잡혔다.  
  
BASE62 인코딩 방식을 파악하기 위해서는 Base64 인코딩 방식을 파악해서 테스트 코드를 작성해보는게 먼저이겠구나... 하는 생각이 들게 되었다.  
  
이런 이유로 BASE64 인코딩을 정리해보면 아래와 같다.

# BASE64 인코딩 
> 참고자료 : [Base64 인코딩](https://ko.wikipedia.org/wiki/%EB%B2%A0%EC%9D%B4%EC%8A%A464)

**직접 그려본 그림**   
![이미지](./img/BASE64_2021_0308.png)

# 자바 바이너리 비트(이진수) 생성 및 표현
![이미지](./img/BIT_PRESENTATION_1.png)
출근을 해야 해서... 나머지는 점심에 잠깐 짬을 내거나, 퇴근후에... ㅠㅜ

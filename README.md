# 카카오페이의 사전과제

## 개발 프레임워크
 - IDE : STS-3.9.4.RELEASE
 - Springboot
 - mysql mysql-connector-java-8.0.20
 - Java-1.8
 
## 문제해결 전략
 - 요구사항에 부합하기 위해 API 명세 설계
 - xUserId, 대화방 token을 활욜하여 뿌리기, 받기 REST API구현
  * 뿌리기 API
 	- 뿌리기 API는 뿌릴금액, 대화방 인원을 인자로 넘겨줌
 	- CreateTokenUtil을 활용하여 3자리 유일한 token을 생성함.
 	- 생성한 token을 대화방에 카카오페이머니 뿌리기수행과 함께 대화방 구성원들에게 전달됨.
	- 뿌린사람(main)에 의하여 설정된 뿌릴금액을 뿌릴인원으로 나누어 반올림한 금액을 대화방 모든 구성원들이 미리 가지고있을 수 있도록 구현.
	- 뿌리기와 동시에 뿌린시간을 별도로 저장함.

  * 받기 API
    - 받는 대상자는 해당대화방의 뿌리기 token을 보유하고잇는지 체크하여 다음단계진행
	- token발급후 10분이내에만 받을 수 있도록  체크함
	- API수행시 뿌린사람의 뿌린잔액을 지속적으로 동기화하여 받는사람들이 금액을 모두 나눠가졌는지 체크함.
	- API수행시 SpreadMoneyRecieveYn(받기수행완료여부)를 'Y'로 update하여 사용자당 최초 한번만 받을 수 있도록함.
	
  * 조회 API
    - 대화방 token, xUserId를 인자로 넘겨주고 xUserId가 돈을 뿌린사람인지 체크
	- 뿌린사람의 뿌린시각, 뿌린금액, 현재잔액을 보여주고 해당정보는 1주일간만 조회할 수 있도록 Date를 사용하여 구현.
	- 받은사람들의 리스트를 받기API 수행당시 update하였던 SpreadMoneyRecieveYn(받기수행완료여부) 항목으로 판별하여 리스트에 add함.
	- 뿌린사람정보, 받은사람리스트를 최종 결과값으로 전달
	
 
 
## 빌드 및 실행 방법
 - 빌드 방법
	- STS 활용
		- STS에서 프로젝트를 Import
		- Project build
 - 실행 방법
	- STS 활용
		- Run As Spring Boot App으로 실행
			
## API 명세			

		
	GET /kakopay/spread/10000/3
		- 기능 : 뿌리기기능
		
	GET /kakopay/recieve/GER/3
		- 기능 : 받기기능
		
	GET /kakopay/allUsers/GER/3
		- 기능 : 조회기능
		
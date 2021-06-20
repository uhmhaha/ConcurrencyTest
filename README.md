----

## 프로젝트 

### 프로젝트 개요 

* 프로젝트 명 :  KAKAO Pay Investment
* 개발자 : 양승권

## 요구사항

* 과제내용
	* 사용자는 원하는 부동산/신용 투자 상품을 투자할 수 있습니다 
	* 투자상품이 오픈될 때, 다수의 고객이 동시에 투자를 합니다. 
	* 투자 후 투자상품의 누적 투자모집 금액, 투자자 수가 증가됩니다. 
	* 총 투자모집금액 달성 시 투자는 마감되고 상품은 Sold out 됩니다.
	* 이번 과제에서는 UI 를 제외한 간소화된 REST API 를 구현하는 것이 목표입니다.
    * 위 목표와 아래의 요구사항을 만족하는 API 서비스를 자유롭게 정의하여 구현해주시면 됩니다


## 핵심 문제해결 전략 및 분석한 내용
* 분석내용 
    * Database 선정 : 
    	* 다수의 서버에서 다수의 인스턴스(MSA)로 동작하더라도 기능에 문제가 없도 록 설계되어야하고,
    	* 순식간에 폭발적인 Traffic 이 예상
    	* 또한 합계금액을 확인하여 금액이 넘을 시 sold-out을 구현해야함
    	* Perfomance의 극대화 및 Concurrency 제어를 위해서 REDIS database를 선정 
	* DB Lock 및 Transaction 부분 : ( DB Lock : oracle등의 Select for update와 유사 )
	```
	redisOperations.watch(hashKey); //key를 watch하다 값이 바뀌면 exception
	redisOperations.multi();        //transaction start
	redisOperations.discard();      // rollbak
	redisOperations.exec();         // transaction commit
	```
    * Redis key관리 : 일부키를 조회하여 관련 Data를 조회하도록 key 규칙( invest :{userid}:{assetId} )
	```
  	invest:kakaopay002:1
  	invest:kakaopay001:1
    ```

## Scenarios - REST APIs

* ***`GET /investAssets/`***
	* Desc. :
		* 전체 투자상품목록을 조회한다. 
	* Input : 
    	* 없음 
    * Output : 
    	````json
    	[
		    {
		        "assetId": 1,
		        "assetTitle": "kakao-fund-002",
		        "assetTotalAmount": 200000000,
		        "assetCurrentAmount": 6000000,
		        "investorNumbers": 2000,
		        "assetStatus": "INPROGRESS",
		        "assetValidFromDate": "2018-12-15T10:00:00",
		        "assetValidToDate": "2018-12-17T10:00:00"
		    },
		    {
		        "assetId": 2,
		        "assetTitle": "kakao-fund-002",
		        "assetTotalAmount": 200000000,
		        "assetCurrentAmount": 6000000,
		        "investorNumbers": 2000,
		        "assetStatus": "INPROGRESS",
		        "assetValidFromDate": "2018-12-15T10:00:00",
		        "assetValidToDate": "2018-12-17T10:00:00"
		    },
		    {
		        "assetId": 3,
		        "assetTitle": "kakao-fund-002",
		        "assetTotalAmount": 106000000,
		        "assetCurrentAmount": 106000000,
		        "investorNumbers": 2000,
		        "assetStatus": "INPROGRESS",
		        "assetValidFromDate": "2018-12-15T10:00:00",
		        "assetValidToDate": "2018-12-17T10:00:00"
		    },
		    {
		        "assetId": 5,
		        "assetTitle": "kakao-fund-002",
		        "assetTotalAmount": 200000000,
		        "assetCurrentAmount": 6000000,
		        "investorNumbers": 2000,
		        "assetStatus": "INPROGRESS",
		        "assetValidFromDate": "2018-12-15T10:00:00",
		        "assetValidToDate": "2018-12-17T10:00:00"
		    }
		]
		````
	
* ***`POST /invest/`***
	* Desc. :
		* 특정상품에 투자한다.
	* Input : 
		* Header : "X-USER-ID" : kakaopay001
		* Body :
    	````json
    	{
		    "assetId" : 1,
		    "investAmount": 1000,
		    "investDate": "2018-12-15T10:00:00"
		}
		````
		
    * Output : 상품 id를 return
    	````json
    	1
		````
		
* ***`GET /invest/record/`***
	* Desc. :
		* 내가 투자한 모든상품을 조회한다.
	* Input : 
		* Header : "X-USER-ID" : kakaopay001

		
    * Output : 상품 id를 return
    	````json
    	[
		    {
		        "assetId": 1,
		        "assetTitle": "kakao-fund-002",
		        "assetTotalAmount": 6002000,
		        "investAmount": 1000,
		        "investDate": "2021-06-20T23:12:58.335679"
		    }
		]
		````

* ***`GET /`***
	* Desc. : for livenessProbe
		
## Test Scenarios

* 첨부파일 : [kakaopay.postman_collection.json.zip](https://github.com/uhmhaha/kakaopayServerTest/files/6682556/kakaopay.postman_collection.json.zip)

* 1.  투자상품을 단건 등록한다.( 파일명 : sub1. Create Invest Assets )
	* ***`POST /investAssets/`***

* 2.  등록된 상품을 확인한다.( 파일명 : sub2. Get a Invest Asset )
	* ***`GET /investAssets/{id}`***

* 3. 전체 투자 상품을 조회한다.( 파일명 : 1. GET All Invest Assets )
 	* ***`GET /investAssets/`***

* 4. 상품에 투자한다.( 파일명 : 2. Request My Invest )
        * ***`POST /invest/`***
        
* 5. 내가 투자한 모든 상품을 조회한다.( 파일명 : 3. Get My Invest Record )
	* ***`GET /invest/record/`*** 

		




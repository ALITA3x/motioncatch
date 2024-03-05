1 Explanation  
==
This code provides 2 functions. One is to generate a signature based on timestamp, secret and parameters; the other is the TCP channel establishment code 

2	Preparation  
==
2.1	Install JAVA JDK  
--
>>https://www.oracle.com/java/technologies/downloads/  

2.2	Download HTTP test tool
--
>>[Postman](https://www.postman.com/downloads/)
>>
3	Codd ccatalog description  
==
src  
├── main  
│   ├── java  
│   │   └── com  
│   │       └── alita  
│   │           └── example  
│   │               └── cloud_point
│   │                   ├── controller
│   │                   │   ├── SignUtil_bindDevice.java-----------------------Account bound device (https)
│   │                   │   ├── SignUtil_deviceProp.java-----------------------Get device properties (https)	
│   │                   │   ├── SignUtil_getDeviceInfo.java--------------------Get all device information (https)
│   │                   │   ├── SignUtil_getsleepreport.java-------------------Get sleep quality report (https)
│   │                   │   ├── SignUtil_httpscallback.java---------------------The 3rd receiving event data interface (HTTPS-interface callback)
│   │                   │   ├── SignUtil_online.java-----------------------------Get device online status (https)
│   │                   │   ├── SignUtil_setboundaries.java--------------------Set radar boundaries (https)
│   │                   │   ├── SignUtil_setcoords.java-------------------------Set area (https)
│   │                   │   ├── SignUtil_setheightscene.java-------------------Set installation method: height and scene(https)
│   │                   │   ├── SignUtil_setthreshold.java----------------------Set respiratory heart rate threshold (https）
│   │                   │   ├── SignUtil_subAffair.java--------------------------Subscribe event data API (HTTPS)
│   │                   │   ├── SignUtil_syncPupillus.java----------------------Set device archives (https)
│   │                   │   ├── SignUtil_token.java-----------------------------Get token interface (https)
│   │                   │   ├── SignUtil_unSubAffair.java----------------------Unsubscribe event data API (HTTPS)
│   │                   │   └── SignUtil_unbindDevice.java--------------------Account unbound device (https)
│   │                   └── tcp----------------------------------------------------The 3rd receiving real-time data interface (TCP)	10
│   │                       ├── MessageCodecSharableTcpTest.java
│   │                       ├── SubDeviceData.java
│   │                       ├── TcpClientHandler.java
│   │                       └── TcpTestClient.java--------------------------------Run test

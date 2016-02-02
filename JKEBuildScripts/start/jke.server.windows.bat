@rem ***************************************************************************
@rem Licensed Materials - Property of IBM
@rem (c) Copyright IBM Corporation 2011. All Rights Reserved.
@rem
@rem Note to U.S. Government Users Restricted Rights:  
@rem Use, duplication or disclosure restricted by GSA ADP Schedule 
@rem Contract with IBM Corp. 
@rem ***************************************************************************
if "%HTTP_PROXY_HOST%"=="" (
java -cp jke.jar;./;libs/mysql-connector-java-5.0.8-bin.jar;libs/derby.jar;libs/javax.servlet_2.5.0.v200910301333.jar;libs/org.mortbay.jetty.server_6.1.23.v201004211559.jar;libs/org.mortbay.jetty.util_6.1.23.v201004211559.jar;libs/com.ibm.team.json_1.0.0.I200908182153.jar com.jke.server.JKEServer
) else (
java -cp jke.jar;./;libs/mysql-connector-java-5.0.8-bin.jar;libs/derby.jar;libs/javax.servlet_2.5.0.v200910301333.jar;libs/org.mortbay.jetty.server_6.1.23.v201004211559.jar;libs/org.mortbay.jetty.util_6.1.23.v201004211559.jar;libs/com.ibm.team.json_1.0.0.I200908182153.jar -Dhttp.proxyHost=%HTTP_PROXY_HOST% -Dhttp.proxyPort=%HTTP_PROXY_PORT% com.jke.server.JKEServer
)
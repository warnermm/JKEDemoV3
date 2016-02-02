#!/bin/sh
#*******************************************************************************
# Licensed Materials - Property of IBM
# (c) Copyright IBM Corporation 2011. All Rights Reserved.
#
# Note to U.S. Government Users Restricted Rights:  
# Use, duplication or disclosure restricted by GSA ADP Schedule 
# Contract with IBM Corp. 
#*******************************************************************************
PRGPATH="`dirname "$0"`"
JAVA_EXE=java

NAME=JKE
PIDFILE=/var/run/$NAME.pid

case "$1" in
start)
	printf "%-50s" "Starting $NAME..."
    PID=`$JAVA_EXE -cp jke.jar:./:libs/mysql-connector-java-5.1.18-bin.jar:libs/derby.jar:libs/javax.servlet_2.5.0.v200910301333.jar:libs/org.mortbay.jetty.server_6.1.23.v201004211559.jar:libs/org.mortbay.jetty.util_6.1.23.v201004211559.jar:libs/com.ibm.team.json_1.0.0.I200908182153.jar @HTTP_PROXY_JVM_ARG@ com.jke.server.JKEServer > /dev/null 2>$1 & echo $!`
    echo "Saving PID " $PID " to " $PIDFILE
    	if [ -z $PID ]; then
    		printf "%s\n" "Fail"
    	else
    		printf $PID > $PIDFILE
    		printf "%s\n" "Ok"
    	fi
;;
status)
	printf "%-50s" "Checking $NAME..."
	if [ -f $PIDFILE ]; then
		PID=`cat $PIDFILE`
		if [ -z "`ps axf | grep ${PID} | grep -v grep`" ]; then
			printf "%s\n" "Process dead but pidfile exists"
		else
			echo "Running"
		fi
	else
		printf "%s\n" "$NAME not running"
	fi
;;
stop)
	printf "%-50s" "Stopping $NAME"
		PID=`cat $PIDFILE`
	if [ -f $PIDFILE ]; then
		kill -HUP $PID
		printf "%s\n" "Ok"
		rm -f $PIDFILE
	else
		printf "%s\n" "pidfile not found"
	fi
;;
restart)
	$0 stop
	$0 start
;;

*)
	echo "Usage: $0 {status|start|stop|restart}"
	exit 1
esac
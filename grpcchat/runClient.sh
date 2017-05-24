#! /bin/bash

if [ -n "$1" ]
then
java -jar ./target/*-jar-with-dependencies.jar localhost 50051 "$1"
else
java -jar ./target/*-jar-with-dependencies.jar localhost 50051 Client
fi

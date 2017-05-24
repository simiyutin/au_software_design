#! /bin/bash

if [ -n "$1" ]
then
java -jar ./target/*-jar-with-dependencies.jar "$1"
else
java -jar ./target/*-jar-with-dependencies.jar Server
fi

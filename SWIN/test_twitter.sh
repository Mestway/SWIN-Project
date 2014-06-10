#!/bin/bash

if [ -e output ]
then
	rm -r output
fi

mkdir output
FileSuffix=".update"

if [ $# -gt 1 ]
then
	echo "Compiling File "$@
	Compile=""
	flag=0
	filename=$1
	cp $filename "DONOTUSETHISNAME.DONOTUSETHISNAME"
	if [ $# -gt 2 ]
	then
		classPath=$2
		touch "APIFILE.APIFILE"
	fi
	for i in $@;do
		if [ $flag -gt 1 ]
		then 
			Compile=$Compile" "$i
		else
			let "flag=$flag+1"
		fi
	done
	echo $Compile
	bin/jlc -classpath $classPath -d output/ -ext update $Compile
	rm "DONOTUSETHISNAME.DONOTUSETHISNAME"
	rm "APIFILE.APIFILE"
else
	echo "An error occured. Seems arguments are not right"
fi

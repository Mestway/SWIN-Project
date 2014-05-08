#!/bin/bash

dir="debugDoc"
if [ -e $dir ]
then 
	rm -r $dir
fi

mkdir $dir

./test MatchInfo/MatchInfo.in update_test/2.update > $dir/2
./test MatchInfo/MatchInfo.in update_test/3.update > $dir/3
./test MatchInfo/MatchInfo1.in update_test/4.update > $dir/4
./test MatchInfo/testPara.in update_test/testPara.update > $dir/testPara


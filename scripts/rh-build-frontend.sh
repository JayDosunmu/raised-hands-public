#! /usr/bin/env sh

CURDIR=$(pwd)
BASENAME=`basename $(pwd)`

if [ "$BASENAME" != "raised-hands" ]
    then
    echo "Error: You must run this script from the raised-hands root project directory"
    echo "\ttry changing directory (cd) into it, then run:"
    echo "\t./scripts/`basename $0`"
    exit 1
fi

cd ./raised-hands-web-client
npm i
npm run build

cd $CURDIR

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

SKIP_TESTS=

for arg in "$@"
do
    case $arg in
        -x|--skip-tests)
        SKIP_TESTS='-x test'
        shift
        ;;
        *)
        shift
        ;;
    esac
done

cd ./raised-hands-server
./gradlew build $SKIP_TESTS

cd $CURDIR

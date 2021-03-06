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
VERSION=

for arg in "$@"
do
    case $arg in
        -x|--skip-tests)
        SKIP_TESTS=$1
        shift
        ;;
        -v|--version)
        shift
        VERSION=$1
        shift
        ;;
        *)
        shift
        ;;
    esac
done

echo Building and registering backend version: $VERSION

rm -f raised-hands-server/build/libs/*

./scripts/rh-build-backend.sh $SKIP_TESTS
if [ $? -ne 0 ]; then { echo "Failed to create build, aborting." ; exit 42; } fi

docker build -t gcr.io/raised-hands-274417/raised-hands-backend:$VERSION ./raised-hands-server/
if [ $? -ne 0 ]; then { echo "Failed to containerize backend, aborting." ; exit 42; } fi

docker push gcr.io/raised-hands-274417/raised-hands-backend:$VERSION

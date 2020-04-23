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

if [ ! -f .env ]
    then
    echo "Error: missing .env file in root project directory"
    echo "\tyou can copy (cp) .env.template"
    echo "\tthen just fill in the appropriate values"
    exit 2
fi

if test -n "$( find ./raised-hands-server/build -name '*.jar' -quit)"
    then
    echo "Backend build not found.\n\tCreating one now..."
    ./scripts/rh-build-backend -x
fi

BUILD=

for arg in "$@"
do
    case $arg in
        -b|--build)
        BUILD='--build'
        shift
        ;;
        *)
        shift
        ;;
    esac
done

docker-compose up $BUILD -d

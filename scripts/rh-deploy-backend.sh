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
DRY_RUN=
VERSION=`cat ./raised-hands-server/version`
if [ -z $VERSION ]; then { echo "Failed to determine version from backend project. Is there a file called version?" ; exit 2; } fi

function skipping_tests() {
  case $1 in
    -x|--skip-tests) echo true ;;
    *) echo false;;
   esac
}

for arg in "$@"
do
    case $arg in
        -x|--skip-tests)
        SKIP_TESTS=$1
        shift
        ;;
        -n|--dry-run)
        DRY_RUN=1
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

BRANCH=$(git symbolic-ref --short HEAD)
if [ $? -ne 0 ]; then { echo "Failed to determine the current git branch. Is git installed?" ; exit 3; } fi

if [ "$BRANCH" != "master" ]
then
    VERSION=$VERSION-SNAPSHOT
fi

echo "Build artifact ($BRANCH): $VERSION -- Skipping tests: $(skipping_tests $SKIP_TESTS)"

./scripts/rh-build-push-docker-backend.sh $SKIP_TESTS -v $VERSION
if [ $? -ne 0 ]; then { echo "Failed to containerize the backend and register the image." ; exit 4; } fi

if [ -z $DRY_RUN ]
then
    kubectl set image deployment/raised-hands-backend raised-hands-backend=gcr.io/raised-hands-274417/raised-hands-backend:$VERSION --record
    if [ $? -ne 0 ]; then { echo "Failed to deploy the backend. Contact the tech lead." ; exit 5; } fi
else
    echo "DRY RUN: would have executed:"
    echo "\tkubectl set image deployment/raised-hands-backend raised-hands-backend=gcr.io/raised-hands-274417/raised-hands-backend:$VERSION --record"
fi

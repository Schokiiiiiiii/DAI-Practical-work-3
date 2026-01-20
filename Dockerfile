FROM ubuntu:latest
LABEL authors="schoki"

ENTRYPOINT ["top", "-b"]
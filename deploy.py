#!/usr/bin/python
import configparser
import subprocess

properties = configparser.RawConfigParser()
properties.read('local.ini')
registry = properties.get("DEFAULT", "registry")

def build(type):
    if type != 'server' and type != 'client':
        raise Exception("type must be server or client")

    tag = f"{registry}/windsekirun/kohakunas-dashbard/{type}"
    buildCommand = ["docker", "buildx", "build", "--platform",
                    "linux/amd64,linux/arm64", "-t", tag, "--push", f"./{type}"]

    print(" ".join(buildCommand))

    buildResult = subprocess.run(buildCommand)
    buildResult.check_returncode

if __name__ == '__main__':
    build("server")
    build("client")

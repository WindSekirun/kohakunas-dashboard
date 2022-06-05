#!/usr/bin/python
import configparser
import subprocess
import os
import wget
from pathlib import Path

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
    pwd = os.getcwd()
    distributionPath = f"{pwd}/server/gradle/wrapper/gradle-7.2-bin.zip"
    if Path(distributionPath).is_file() == False:
        print("Download gradle-7.2-bin.zip")
        wget.download("https://services.gradle.org/distributions/gradle-7.2-bin.zip", distributionPath)
    
    build("server")
    build("client")

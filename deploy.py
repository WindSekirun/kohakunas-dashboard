#!/usr/bin/python
from asyncio.subprocess import PIPE
import configparser
import subprocess
import os
import wget
from pathlib import Path
import platform

properties = configparser.RawConfigParser()
properties.read('local.ini')
registry = properties.get("DEFAULT", "registry")

def appendSudo(command):
    if platform.system() == "Linux":
        command.insert(0, "sudo")
    return command

def build(type):
    if type != 'server' and type != 'client':
        raise Exception("type must be server or client")

    tag = f"{registry}/windsekirun/kohakunas-dashboard/{type}"

    findPlatformCommand = appendSudo(["docker", "buildx", "inspect"])
    findPlatformResult = subprocess.run(findPlatformCommand, stdout=PIPE, stderr=PIPE, universal_newlines=True).stdout.splitlines()[-1]
    
    if "arm64" in findPlatformResult:
        supportPlatform = "linux/amd64,linux/arm64"
    else:
        supportPlatform = "linux/amd64"

    buildCommand = appendSudo(["docker", "buildx", "build", "--platform", supportPlatform, "-t", tag, "--push", f"./{type}"])

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

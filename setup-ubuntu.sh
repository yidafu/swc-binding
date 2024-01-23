#!/bin/bash

sudo apt update
sudo apt upgrade
sudo apt install build-essential
sudo apt install llvm
cargo install cargo-zigbuild
cargo install cargo-xwin

cd swc-jni


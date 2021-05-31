#!/usr/bin/bash
pass="qwerty"

for i in {1..7} ; do
    sshpass -p $pass ssh sd110@l040101-ws0$i.ua.pt 'killall java'
done
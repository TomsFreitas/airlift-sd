#!/usr/bin/bash

host="l040101-ws04.ua.pt"
pass="qwerty"

echo "Transfering file"
sshpass -p $pass scp prod/deploy_pass*.tgz sd110@$host:/home/sd110/
echo "Extracting"
sshpass -p $pass ssh sd110@$host 'tar -xzf /home/sd110/*.tgz'
echo "Running"
sshpass -p $pass ssh sd110@$host 'cd depl*; java client.main.passengerMain'

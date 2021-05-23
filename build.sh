#!/usr/bin/bash
echo $(pwd)
echo "Getting .java files"
find -name "*.java" > sources.txt
echo "Compiling Java files"
javac -cp ".:src/genclass.jar" @sources.txt

echo "Creating Directories"
mkdir prod
for i in {passenger,hostess,pilot} ; do
    mkdir -p prod/deploy_$i/client/{entity,main,stubs}
    mkdir -p prod/deploy_$i/commInfra

done


for i in {departure,destination,plane,genrepo} ; do
    mkdir -p prod/deploy_$i/server/{interfaces,proxies,servers,sharedRegion,stubs}
    mkdir -p prod/deploy_$i/commInfra
done

echo "Copying relevant files"

echo "Creating Passenger Structure"
cp src/client/entity/Passenger.class prod/deploy_passenger/client/entity/
cp src/client/main/passengerMain.class prod/deploy_passenger/client/main/
cp -r src/client/stubs/* prod/deploy_passenger/client/stubs/
cp -r src/commInfra/* prod/deploy_passenger/commInfra/

echo "Creating Hostess Structure"
cp src/client/entity/Hostess.class prod/deploy_hostess/client/entity/
cp src/client/main/hostessMain.class prod/deploy_hostess/client/main/
cp -r src/client/stubs/* prod/deploy_hostess/client/stubs/
cp -r src/commInfra/* prod/deploy_hostess/commInfra/

echo "Creating Pilot Structure"
cp src/client/entity/Pilot.class prod/deploy_pilot/client/entity/
cp src/client/main/pilotMain.class prod/deploy_pilot/client/main/
cp -r src/client/stubs/* prod/deploy_pilot/client/stubs/
cp -r src/commInfra/* prod/deploy_pilot/commInfra/

echo "Creating Departure Structure"
cp src/server/interfaces/{Passenger.class,Hostess.class,Pilot.class,dep*.class} prod/deploy_departure/server/interfaces/
cp src/server/proxies/dep*.class prod/deploy_departure/server/proxies/
cp src/server/servers/dep*.class prod/deploy_departure/server/servers/
cp src/server/sharedRegion/dep*.class prod/deploy_departure/server/sharedRegion/
cp -r src/server/stubs/* prod/deploy_departure/server/stubs/
cp -r src/commInfra/* prod/deploy_departure/commInfra/

echo "Creating Destination Structure"
cp src/server/interfaces/{Passenger.class,Pilot.class,dest*.class} prod/deploy_destination/server/interfaces/
cp src/server/proxies/dest*.class prod/deploy_destination/server/proxies/
cp src/server/servers/dest*.class prod/deploy_destination/server/servers/
cp src/server/sharedRegion/dest*.class prod/deploy_destination/server/sharedRegion/
cp -r src/server/stubs/* prod/deploy_destination/server/stubs/
cp -r src/commInfra/* prod/deploy_destination/commInfra/

echo "Creating Plane Structure"
cp src/server/interfaces/{Passenger.class,Hostess.class,Pilot.class,plane*.class} prod/deploy_plane/server/interfaces/
cp src/server/proxies/plane*.class prod/deploy_plane/server/proxies/
cp src/server/servers/plane*.class prod/deploy_plane/server/servers/
cp src/server/sharedRegion/Plane*.class prod/deploy_plane/server/sharedRegion/
cp -r src/server/stubs/* prod/deploy_plane/server/stubs/
cp -r src/commInfra/* prod/deploy_plane/commInfra/

echo "Creating GenRepo Structure"
cp src/server/interfaces/gen*.class prod/deploy_genrepo/server/interfaces/
cp src/server/proxies/gen*.class prod/deploy_genrepo/server/proxies/
cp src/server/servers/gen*.class prod/deploy_genrepo/server/servers/
cp src/server/sharedRegion/gen*.class prod/deploy_genrepo/server/sharedRegion/
cp -r src/server/stubs/* prod/deploy_genrepo/server/stubs/
cp -r src/commInfra/* prod/deploy_genrepo/commInfra/

echo "Zipping all the files"

cd prod/
for i in *;do
  tar zcf ${i}.tgz $i;
done;














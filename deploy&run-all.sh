#!/usr/bin/bash

x-terminal-emulator -e ./deploy\&run_repo.sh&
read one
x-terminal-emulator -e ./deploy\&run_departure.sh&
x-terminal-emulator -e ./deploy\&run_destination.sh&
x-terminal-emulator -e ./deploy\&run_plane.sh&
read two
x-terminal-emulator -e ./deploy\&run_pilot.sh&
x-terminal-emulator -e ./deploy\&run_hostess.sh&
read three
x-terminal-emulator -e ./deploy\&run_passenger.sh









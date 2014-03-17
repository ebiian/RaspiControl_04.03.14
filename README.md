raspberry pi with external 2.2" display
java controlled

webserver: raspberrypi:8585/piWeb
  attention: 1st loading of the webPage needs very very very long !
  some kind of jsp issue ?!?

  
1) copy the content of "libpi" into the dirctory "/home/pi"

2) create i.e. "run.sh" with the following content:

#unset DISPLAY
LD_LIBRARY_PATH=. java -jar piControlLcd.jar


3) start it with: "sudo ./run.sh"

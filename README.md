raspberry pi automation controller

with additional external 2.2" LCD display

java controlled

webserver: raspberrypi:8585/piWeb

  attention: 1st loading of the webPage needs very very very long !
  some kind of jsp issue ?!?
  but after this it is fast enough !

  
1) copy the content of "libpi" into the dirctory "/home/pi"

2) create i.e. "run.sh" with the following content:
  LD_LIBRARY_PATH=. java -jar piControlLcd.jar

3) start it with: "sudo ./run.sh"

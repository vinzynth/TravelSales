@ECHO off
SET TITLE=TravelSales
CLS
TITLE Travel Sales
java -version
java -Xmx256m -Xverify:none -javaagent:target\dependency\chrl-callbacks-0.0.1-SNAPSHOT.jar -classpath target\travelsales-0.0.1-SNAPSHOT.jar;target\dependency\* at.tug.oad.travelsales.TravelSales



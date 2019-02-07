# PizarraFx
Pizarra distribuida utilizando raft

La pizarra ofrece la siguientes caracteristicas

    Implementada bajo el algoritmo robusto de raft
    Utiliza la implementacion de jgroups raft
    Usa multicast para descubrir los nodos en la red
    Usa el archivo de configuracion log4j2.xml para la parametrizacion de logs
    Usa el archivo de configuracion raft.xml para parametrizar los tiempos de eleccion de leader, multicast, etc.
    
Esta app fue desarrollada utilizando como editor *Netbeans*. Se debe construir el proyecto utilizando las dependencias, para ello utilizar la herramienta propia de netbeans.

![alt text](https://platform.netbeans.org/images/tutorials/maven-quickstart/72/try-1.png)

### Ejecucion

PizarraFx requires [Java8](https://www.java.com/es/download/) v8+ to run.

Install the dependencies and devDependencies and start the app.

```sh
$ cd PizarraFx
$ java -Dlog4j.configurationFile=log4j2.xml -jar target/PizarraFx-1.0-SNAPSHOT.jar
```

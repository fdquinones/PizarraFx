# PizarraFx
Pizarra distribuida utilizando raft

La pizarra ofrece la siguientes caracteristicas

    Implementada bajo el algoritmo robusto de raft
    Utiliza la implementacion de jgroups raft
    Usa multicast para descubrir los nodos en la red
    Usa el archivo de configuración log4j2.xml para la parametrizacion de logs
    Usa el archivo de configuración raft.xml para parametrizar los tiempos de elección de leader, multicast, etc.
    El numero de nodos a utilizar dentro del sistema viene dado por el archivo config.json y raft.xml. Por defecto el sistema trabaja con 4 nodos, por lo cual se debe ejecutar 4 instancias de la aplicación.
    
Esta app fue desarrollada utilizando como editor *Netbeans*. Se debe construir el proyecto utilizando las dependencias, para ello utilizar la herramienta propia de netbeans.

![alt text](https://platform.netbeans.org/images/tutorials/maven-quickstart/72/try-1.png)

### Ejecucion

PizarraFx requires [Java8](https://www.java.com/es/download/) v8+ to run.

Install the dependencies and devDependencies and start the app.

```sh
$ cd PizarraFx
$ java -Dlog4j.configurationFile=log4j2.xml -jar target/PizarraFx-1.0-SNAPSHOT.jar
```

Antes de empezar a graficar se tiene que tener un nodo como leader, para lo cual el sistema automaticamente seleccionara un candidato de la red.

### Capturas de la app en ejecución

![Pizarra en estado inicial](https://snag.gy/ueoJCV.jpg)
![Pizarra instancia leader](https://snag.gy/PZY9c4.jpg)
![Pizarra instancia leader](https://snag.gy/Oibzs4.jpg)
![Pizarra instancia leader](https://snag.gy/YvtAd1.jpg)




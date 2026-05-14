# 🍽️ Vacío Espiritual — Gestión de Restaurante

Aplicación Java para gestionar un restaurante. Permite crear cuentas, iniciar sesión, pedir platos, gestionar el stock y guardar todo en una base de datos MySQL.

---

## 📋 ¿Qué hace esta aplicación?

- Crear cuentas e iniciar sesión
- Realizar pedidos de platos
- Gestionar el stock de productos
- Almacenar información en una base de datos MySQL

---

## ☕ Requisitos para ejecutar la app

Necesitas tener instalado:

- **JDK 17** (o superior) → [Descargar aquí](https://adoptium.net)
- **MySQL** para la base de datos
- **Driver JDBC** (incluido en el proyecto)

### Instalar Java en Windows

1. Descarga el instalador de JDK 17 desde [Adoptium](https://adoptium.net)
2. Ejecútalo y sigue los pasos
3. Comprueba que funciona:

```bash
java -version
javac -version
```

### Instalar Java en Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version
```

### Variables de entorno (importante ⚠️)

Si Java no se reconoce como comando, hay que configurar:

| Variable    | Para qué sirve                                         |
|-------------|--------------------------------------------------------|
| `JAVA_HOME` | Apunta a la carpeta donde está instalado el JDK        |
| `PATH`      | Permite usar `java` y `javac` desde cualquier terminal |

**Errores típicos si no están bien configuradas:**
- `"java no se reconoce como un comando interno o externo"`
- Se usa una versión antigua de Java
- El IDE no encuentra el compilador

---

## 📦 Cómo instalar la aplicación

Para facilitar la instalación se puede empaquetar como instalador. Algunas herramientas útiles:

| Herramienta | Para qué sirve              | Sistema           |
|-------------|-----------------------------|-------------------|
| Inno Setup  | Crear instalador `.exe`     | Windows           |
| Launch4j    | Convertir `.jar` a `.exe`   | Windows           |
| jpackage    | Herramienta oficial del JDK | Win / Linux / Mac |

Un instalador puede incluir: los archivos de la app, el driver JDBC, accesos directos, configuración inicial y desinstalador.

### Diferencia entre máquina virtual y contenedor

| Máquina Virtual | Contenedor Docker       |
|-----------------|-------------------------|
| Varios GB       | Decenas de MB           |
| Arranque lento  | Arranque rápido         |
| SO completo     | Comparte el SO del host |


## ✅ Conclusión

Programar la app es solo una parte del trabajo. Este proyecto cubre también cómo:

1. Configurar el entorno Java correctamente
2. Distribuirla mediante instaladores
3. Comunicarla con otras apps mediante APIs
4. Evitar problemas de compatibilidad con Docker
5. Añadir una interfaz visual con Angular o React

---



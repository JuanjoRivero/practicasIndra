# PROYECTO INDRA

Este proyecto forma parte de las prácticas del primer curso de DAW por parte de Indra para el IES Castelar. El objetivo es crear un portal web para la gestión de eventos relacionados con la sostenibilidad (talleres, conferencias, etc.), aplicando conocimientos de programación, bases de datos, entornos de desarrollo y despliegue en entorno virtualizado.

---

## Índice

1. [Planteamiento general del proyecto](#1-planteamiento-general-del-proyecto)
2. [Lenguaje de marcas: HTML, CSS y JavaScript](#2-lenguaje-de-marcas-html-css-y-javascript)
3. [Base de datos: diseño ER y script SQL](#3-base-de-datos-diseño-er-y-script-sql)
4. [Programación orientada a objetos](#4-programación-orientada-a-objetos)
5. [Sistemas informáticos: instalación de Windows 10 y servidor web](#5-sistemas-informáticos-instalación-de-windows-10-y-servidor-web)
6. [Entorno de desarrollo y control de versiones (Git/GitHub)](#6-entorno-de-desarrollo-y-control-de-versiones-gitgithub)
7. [Capturas y evidencias](#7-capturas-y-evidencias)

---

## 1. Planteamiento general del proyecto

El proyecto se plantea como una simulación de un encargo real por parte de una empresa que necesita un portal web para la gestión de eventos sostenibles. A través de una entrevista con el responsable de la empresa, se identifican los requisitos principales del sistema:

  1. Creación de eventos sostenibles (talleres, conferencias, actividades ecológicas), con información como: nombre, fecha, duración, ubicación (presencial u online) y categoría.
  2. Sistema de registro y login de usuarios, que podrán inscribirse a eventos disponibles.
  3. Gestión de organizadores, que son los responsables de crear y editar eventos. Son diferentes de los usuarios normales.
  4. Gestión de inscripciones entre usuarios y eventos, ya que se trata de una relación de muchos a muchos.
  5. Clasificación de eventos por categorías (ej. talleres, conferencias) para facilitar la búsqueda.
  6. Distinción entre eventos presenciales y online, que deben comportarse de forma similar aunque tengan ubicaciones distintas.
  7. Despliegue local del portal en un servidor web montado dentro de una máquina virtual con Windows 10 PRO.
  8. Uso de buenas prácticas de programación orientada a objetos, diseño modular y control de versiones mediante Git y GitHub.

Además, el proyecto exige la generación de evidencias gráficas del proceso y el resultado final, incluyendo:

  1. Capturas de la instalación de la máquina virtual.
  2. Capturas del servidor web desplegado.
  3. Imagen final del portal funcionando (portal_eventos_sostenibles.jpg).
  4. Imagen del grafo de ramas usado en el desarrollo (grafo_ramas_git.jpg).

---

## 2. Lenguaje de marcas: HTML, CSS y JavaScript

El portal web tiene como objetivo facilitar la consulta y gestión de eventos relacionados con la sostenibilidad, permitiendo a los usuarios descubrir talleres, conferencias y actividades de interés, así como acceder a toda la información relevante de cada uno de ellos de forma clara y atractiva.

### Estructura y propósito de las páginas

- **Página principal (`index.html`)**  
  Es la carta de presentación del portal. Incluye una breve introducción sobre el objetivo de la plataforma y un carrusel visual con los eventos más destacados, pensado para captar la atención del usuario desde el primer momento y animarle a explorar el resto de secciones.

- **Listado de eventos (`eventos.html`)**  
  Muestra todos los eventos disponibles en formato de tarjetas, cada una con imagen, nombre, fecha, tipo y modalidad.  
  Incluye un sistema de filtrado dinámico por tipo de evento (taller, conferencia, actividad), implementado en JavaScript, que permite al usuario encontrar fácilmente los eventos que más le interesan sin recargar la página.

- **Detalle de evento (`evento1.html`, `evento2.html`, ..., `evento9.html`)**  
  Cada evento cuenta con su propia página de detalle, donde se muestra la imagen, nombre, fecha, tipo, modalidad, dirección, descripción, organizador y duración. Esto permite al usuario consultar toda la información relevante de un vistazo y decidir si desea inscribirse o participar.

### Funcionalidades destacadas

- **Carrusel de eventos destacados**  
  En la página principal, el carrusel permite navegar visualmente entre los eventos más importantes, mejorando la experiencia de usuario y la visibilidad de los eventos prioritarios.

- **Filtro de eventos**  
  En la página de listado, el filtro por tipo de evento está implementado en JavaScript puro, proporcionando una experiencia fluida y sin recargas. El usuario puede alternar entre talleres, conferencias y actividades con un solo clic.

- **Imágenes y recursos**  
  Todas las imágenes de los eventos están almacenadas en la carpeta `images` y se referencian correctamente en cada página, aportando un aspecto visual atractivo y profesional.

- **Estilos centralizados (`css/style.css`)**  
  El diseño visual se gestiona desde un único archivo CSS, donde se definen los estilos para la maquetación, las tarjetas de eventos, el detalle de cada evento y la adaptación responsive.

- **Interactividad (`js/script.js`)**  
  El filtrado de eventos y otras pequeñas interacciones se realizan con JavaScript, mejorando la experiencia de usuario sin recargar la página.


---

## 3. Base de datos: diseño ER y script SQL

Para garantizar la correcta gestión y persistencia de los datos, he diseñado una base de datos relacional que refleja fielmente las necesidades del portal de eventos sostenibles. El diseño parte de un análisis previo de los requisitos y se apoya en un **diagrama entidad-relación (ER)** que facilita la comprensión de las relaciones entre las distintas entidades del sistema.

### Diagrama entidad-relación

A continuación se muestra el diagrama ER diseñado con la herramienta web **Draw.io** `https://draw.io/` que representa la estructura lógica de la base de datos. En él se pueden observar las entidades principales (Usuarios, Organizadores, Eventos, Inscripciones, Categorías y Ubicaciones) y las relaciones entre ellas:

![Diagrama ER](sql/diagrama-er.png)

- **Usuarios** y **Organizadores**: Se gestionan como entidades separadas, cada una con sus propios atributos y restricciones. Los organizadores son los responsables de crear eventos, mientras que los usuarios pueden inscribirse en ellos.
- **Eventos**: Cada evento está asociado a una categoría, una ubicación y un organizador. Además, puede tener múltiples usuarios inscritos.
- **Inscripciones**: Representa la relación de muchos a muchos entre usuarios y eventos, almacenando la fecha de inscripción y asegurando que un usuario no pueda inscribirse dos veces al mismo evento.
- **Categorías**: Permite clasificar los eventos (taller, conferencia y actividad).
- **Ubicaciones**: Gestiona tanto eventos presenciales como online, diferenciando el tipo de ubicación y almacenando información relevante como dirección, ciudad y código postal.

### Características principales del modelo

- **Integridad referencial**: Todas las relaciones están implementadas mediante claves foráneas, asegurando la coherencia de los datos.
- **Restricciones de unicidad**: El correo electrónico y el teléfono son únicos tanto para usuarios como para organizadores, evitando duplicidades.
- **Persistencia de inscripciones**: La tabla intermedia `Inscripciones` garantiza que cada usuario solo pueda inscribirse una vez en cada evento.
- **Flexibilidad en ubicaciones**: La entidad `Ubicaciones` permite gestionar tanto eventos online como presenciales de forma unificada.

> **Nota:** El diseño y la implementación de la base de datos están redactados en el archivo [`sql/baseDeDatos.sql`](sql/baseDeDatos.sql) y el diagrama ER se encuentra en [`doc/diagrama-er.png`](doc/diagrama-er.png). Para más detalles sobre las entidades y sus atributos, puedes consultar el documento [`Entidades_Eventos_Sostenibles.pdf`](sql/Entidades_Eventos_Sostenibles.pdf).

---

## 4. Programación orientada a objetos

He desarrollado el programa mediante el lenguaje **Java puro (sin Frameworks)**, con módulos **Maven**. He importado diferentes dependencias (que se enumerarán a continuación). La aplicación en principio debía contener las clases **POJO** (Plain Old Java Object), pero no podria desarrollar los conocimientos del curso impartidos por mis profesores, de manera que, para dar el nivel dado por el profesor, realicé una aplicación que gestiona:
  1. La creación, modificación, eliminación y visualización de **USUARIOS, ORGANIZADORES y EVENTOS**.
  2. Que los **ORGANIZADORES** puedan crear y organizar los **EVENTOS** concretamente y SOLO podran modificar y eliminar los creados por unos mismos.
  3. Que los **USUARIOS** puedan apuntarse y desapuntarse a los **EVENTOS** públicos siempre y cuando estén disponibles **(ESTADO = EN CURSO)**
  4. Estén presentes las relaciones entre entidades.
  5. Que sea **PERSISTENTE**. Al ser un módulo independiente de *BASE DE DATOS*, he decidido mantener la persistencia mediante archivos JSON y serializando las entidades, teniendo en cuenta que hay atributos que no se pueden serializar por bucle infinito (los **USUARIOS** contienen **EVENTOS** que a su vez contienen los **USUARIOS** unidos).
  6. Todo gestionado con una interfaz visual por consola.

Las clases creadas son las siguientes:

- Main: Clase principal que inicia la aplicación, gestiona el flujo de inicio de sesión/registro y muestra los menús de usuario y organizador.
  
#### MÓDULO *Entities*

- **Usuario**: Representa a los usuarios del sistema, con atributos como nombre, correo, teléfono, contraseña y la lista de eventos a los que está inscrito.
- **Organizador**: Representa a los organizadores, con información personal y la lista de eventos que ha creado.
- **Inscripcion**: Relaciona a un usuario con un evento, almacenando la fecha de inscripción y los datos asociados.
- **Evento**: Define los eventos disponibles, incluyendo su estado (activo, finalizado, cancelado), categoría, nombre, ubicación, fecha, duración, organizador y usuarios inscritos.
- **Estado**: Enumera los posibles estados de un evento (ACTIVO, FINALIZADO, CANCELADO).
- **Categoria**: Enumera las categorías posibles de los eventos (por ejemplo, TALLER, CONFERENCIA, etc.).
- **Ubicacion**: Representa la ubicación física o virtual de un evento.
- **TipoUbicacion**: Enumera los tipos de ubicación posibles (por ejemplo, PRESENCIAL, ONLINE).

#### MÓDULO *Services*

- **Almacen**: Gestiona la persistencia de los datos en archivos JSON (eventos, usuarios, organizadores, inscripciones) y proporciona métodos CRUD para cada entidad.
- **GeneradorIds**: Se encarga de generar identificadores únicos para cada entidad (evento, usuario, organizador, inscripción).
- **MergeInscripciones**: Sincroniza las relaciones entre inscripciones, usuarios, eventos y organizadores para evitar inconsistencias y duplicidades.
- **CentralBBDD**: Orquesta el acceso a los datos, inicializa las listas de entidades y asegura la integridad de las relaciones entre ellas.
- **Dao**: Proporciona una interfaz de acceso a los datos, delegando las operaciones CRUD a CentralBBDD y gestionando la lógica de negocio.
- **Controlador**: Gestiona la lógica de la aplicación, coordinando la interacción entre la interfaz de usuario y los servicios, y validando las operaciones de registro, login, inscripción, etc.

También he agregado ciertas dependencias al **pom.xml**:

- GSON: dependencia de Google para la serialización de clases en Java que permite el parseo a JSON.
- JUNIT: dependencia para el testing. He desarrollado con el todos los test necesarios para comprobar que todo está en orden.
---

## 5. Sistemas informáticos: instalación de Windows 10 y servidor web

Para garantizar un entorno controlado y reproducible, he realizado la instalación de **Windows 10 PRO** en una máquina virtual, sobre la que posteriormente he desplegado el servidor web necesario para el funcionamiento del portal.

### Instalación de la máquina virtual con Windows 10

1. **Descarga de la ISO**: Se ha descargado la imagen oficial de Windows 10 PRO desde el sitio web de Microsoft.
2. **Creación de la máquina virtual**: Utilizando **VirtualBox** (aunque puede emplearse VMware u otro software de virtualización), se ha creado una nueva máquina virtual con los siguientes parámetros:
   - Asignación de 4 GB de RAM.
   - Disco duro virtual de 50 GB en formato VDI.
   - Configuración de red en modo NAT para permitir el acceso a Internet.
3. **Instalación del sistema operativo**: Se ha montado la ISO y seguido el asistente de instalación de Windows 10, configurando la cuenta de usuario y las opciones básicas del sistema.
4. **Configuración inicial**: Tras la instalación, se han aplicado las actualizaciones de Windows y configurado el idioma, zona horaria y otras preferencias.

### Instalación y configuración del servidor web

1. **Instalación de XAMPP**: Para facilitar el despliegue y pruebas del portal, se ha instalado el paquete **XAMPP** (que incluye Apache, MySQL y PHP) en la máquina virtual.
2. **Configuración de Apache**:
   - Se ha iniciado el servicio Apache desde el panel de control de XAMPP.
   - Se ha verificado el funcionamiento accediendo a `http://localhost` desde el navegador de la máquina virtual.
3. **Despliegue del portal web**:
   - Los archivos del portal se han copiado a la carpeta `htdocs` de XAMPP.
   - Se ha comprobado el acceso al portal desde el navegador, asegurando que todas las rutas y recursos funcionen correctamente.
4. **Acceso desde el equipo anfitrión** (opcional): Configurando la red en modo puente, es posible acceder al portal desde el equipo principal usando la IP de la máquina virtual.

> **Nota:** Se han realizado capturas de pantalla durante el proceso de instalación y despliegue, que se incluyen en el apartado de documentacion.

---

## 6. Entorno de desarrollo y control de versiones (Git/GitHub)

*Explica:*

* Creacion de repo
* Ramas
* organizacion del repo

---

## 7. Capturas y evidencias

* `portal_eventos_sostenibles.jpg`: Es la vista final del portal web.
* `grafo_ramas_git.jpg`: Estructura de ramas usada en Git.

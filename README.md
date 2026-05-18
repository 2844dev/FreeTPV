<!--
FULL CREDITS TO:
https://github.com/othneildrew/Best-README-Template
-->
<!--
[THIS CONTENT WAS WRITEN BY CLAUDE]
-->

> [!IMPORTANT]
> Este proyecto está actualmente en desarrollo, puede no funcionar correctamente.

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![project_license][license-shield]][license-url]

<br />
<div align="center">
  <a href="https://github.com/2844dev/FreeTPV">
    <img src="src/main/resources/com/mateo/freetpv/images/freetpv_logo.png" alt="Logo" width="400">
  </a>

  <h3 align="center">FreeTPV</h3>

  <p align="center">
    Un software TPV gratuito y de código abierto enfocado a la hostelería.
    <br />
    <a href="https://github.com/2844dev/FreeTPV/issues/new?labels=bug&template=reportar-bug.md">Reportar Bug</a>
    &middot;
    <a href="https://github.com/2844dev/FreeTPV/issues/new?labels=sugerencia&template=sugerencia-de-mejora.md">Solicitar función</a>
  </p>
</div>

---

<!-- TABLA DE CONTENIDOS -->
<details>
  <summary>Tabla de contenidos</summary>
  <ol>
    <li><a href="#sobre-el-proyecto">Sobre el proyecto</a></li>
    <li><a href="#características">Características</a></li>
    <li><a href="#herramientas-utilizadas">Herramientas utilizadas</a></li>
    <li>
      <a href="#instalación">Instalación</a>
      <ul>
        <li><a href="#windows">Windows</a></li>
        <li><a href="#compilar-desde-el-código-fuente">Compilar desde el código fuente</a></li>
      </ul>
    </li>
    <li><a href="#capturas-de-pantalla">Capturas de pantalla</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contribuir">Contribuir</a></li>
    <li><a href="#licencia">Licencia</a></li>
    <li><a href="#contacto">Contacto</a></li>
    <li><a href="#créditos">Créditos</a></li>
  </ol>
</details>

---

## Sobre el proyecto

FreeTPV es una aplicación de punto de venta (TPV) de escritorio, gratuita y de código abierto, pensada para negocios de hostelería como bares, restaurantes y cafeterías.

Funciona completamente en local, sin necesidad de conexión a internet ni suscripciones. Los datos se almacenan en una base de datos SQLite en el propio equipo.

---

## Características

- 🔐 **Sistema de usuarios** con contraseñas cifradas mediante bcrypt y roles (Admin / Camarero)
- 🛍️ **Gestión de productos y categorías** con imágenes, precios, IVA y estado
- 🧾 **Pantalla de ventas** con cobro en efectivo o tarjeta e impresión de tickets ESC/POS
- ⚙️ **Configuración completa** — datos de empresa, ticket, impresora y apariencia
- 🎨 **Múltiples temas** (Primer Light/Dark, Nord, Cupertino, Dracula)
- 💾 **Copias de seguridad** de la base de datos
- 📋 **Logs automáticos** con rotación diaria

---

## Herramientas utilizadas

[![Java][Java]][Java-url] [![JavaFX][JavaFX]][JavaFX-url] [![SQLite][SQLite]][SQLite-url]

| Tecnología | Uso |
|---|---|
| Java 25 | Lenguaje principal |
| JavaFX 26 | Interfaz gráfica |
| SQLite (JDBC) | Base de datos local |
| AtlantaFX | Tema y estilos de UI |
| Ikonli / FontAwesome 5 | Iconos |
| bcrypt | Cifrado de contraseñas |
| escpos-coffee | Impresión de tickets ESC/POS |
| SLF4J + Logback | Sistema de logs |
| Maven | Gestión de dependencias y build |

---

## Instalación

### Windows

1. Ve a la página de [Releases](https://github.com/2844dev/FreeTPV/releases) y descarga el instalador `.exe` de la última versión.
2. Ejecuta el instalador y sigue los pasos.
3. Abre FreeTPV desde el acceso directo del escritorio o el menú de inicio.
4. En el primer arranque, crea tu usuario administrador.

> No es necesario tener Java instalado. El instalador incluye todo lo necesario.

### Compilar desde el código fuente

**Requisitos previos:**
- Java 25 o superior
- Maven 3.9 o superior

```bash
# Clona el repositorio
git clone https://github.com/2844dev/FreeTPV.git
cd FreeTPV

# Compila y empaqueta
./mvnw clean package
```

Para ejecutarlo desde IntelliJ IDEA, añade estas opciones de VM en la configuración de ejecución:

```
--enable-native-access=org.xerial.sqlitejdbc
--enable-native-access=javafx.graphics
```

---

## Capturas de pantalla

> 📸 *Próximamente*

<!-- Pantalla de login -->
<!-- ![Login](images/screenshot_login.png) -->

<!-- Pantalla de ventas -->
<!-- ![Ventas](images/screenshot_ventas.png) -->

<!-- Gestión de productos -->
<!-- ![Productos](images/screenshot_productos.png) -->

<!-- Ajustes -->
<!-- ![Ajustes](images/screenshot_ajustes.png) -->

---

## Roadmap

- [x] Pantalla de login
  - [x] Contraseñas cifradas con bcrypt
  - [x] Pantalla de creación del primer usuario administrador
- [x] Base de datos SQLite local (sin internet)
- [x] Gestión de empleados
  - [x] Tabla de usuarios con búsqueda y filtros
  - [x] Creación y edición de usuarios
  - [ ] Roles personalizables con permisos
- [x] Gestión de productos
  - [x] Creación y edición de productos con imagen, precio e IVA
  - [x] Filtros por categoría, estado y favorito
- [x] Gestión de categorías
- [x] Pantalla de ventas
  - [x] Cobro en efectivo y tarjeta
  - [x] Impresión de tickets ESC/POS
  - [ ] Historial de ventas
- [x] Configuración completa (empresa, ticket, impresora, apariencia)
- [x] Múltiples temas visuales
- [x] Logs automáticos con rotación diaria
- [x] Copias de seguridad
- [x] Instalador `.exe` para Windows (GitHub Actions)
- [ ] Gestión de mesas y zonas
- [ ] Gestión de clientes
- [ ] Gestión de stock

Consulta los [issues abiertos](https://github.com/2844dev/FreeTPV/issues) para ver el estado de las funciones propuestas y los errores conocidos.

---

## Contribuir

Las contribuciones son bienvenidas y muy apreciadas. Si tienes una sugerencia, haz un fork del repositorio y abre una pull request. También puedes abrir un issue con la etiqueta correspondiente.

No olvides darle una estrella al proyecto si te ha resultado útil. ¡Gracias!

1. Haz un fork del proyecto
2. Crea tu rama de función (`git checkout -b feature/NuevaFuncion`)
3. Haz commit de tus cambios (`git commit -m 'Añade NuevaFuncion'`)
4. Sube la rama (`git push origin feature/NuevaFuncion`)
5. Abre una Pull Request

### Top de contribuidores

<a href="https://github.com/2844dev/FreeTPV/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=2844dev/FreeTPV" alt="contrib.rocks image" />
</a>

---

## Licencia

Distribuido bajo la licencia MIT. Consulta [LICENSE](https://github.com/2844dev/FreeTPV/blob/main/LICENSE) para más información.

---

## Contacto

- Repositorio: [https://github.com/2844dev/FreeTPV](https://github.com/2844dev/FreeTPV)
- Discord: @2844

---

## Créditos

- [Best-README-Template](https://github.com/othneildrew/Best-README-Template)
- [AtlantaFX](https://github.com/mkpaz/atlantafx)
- [escpos-coffee](https://github.com/anastaciocintra/escpos-coffee)
- [Ikonli](https://github.com/kordamp/ikonli)
- [contrib.rocks](https://contrib.rocks)

---

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/2844dev/FreeTPV.svg?style=for-the-badge
[contributors-url]: https://github.com/2844dev/FreeTPV/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/2844dev/FreeTPV.svg?style=for-the-badge
[forks-url]: https://github.com/2844dev/FreeTPV/network/members
[stars-shield]: https://img.shields.io/github/stars/2844dev/FreeTPV.svg?style=for-the-badge
[stars-url]: https://github.com/2844dev/FreeTPV/stargazers
[issues-shield]: https://img.shields.io/github/issues/2844dev/FreeTPV.svg?style=for-the-badge
[issues-url]: https://github.com/2844dev/FreeTPV/issues
[license-shield]: https://img.shields.io/github/license/2844dev/FreeTPV.svg?style=for-the-badge
[license-url]: https://github.com/2844dev/FreeTPV/blob/master/LICENSE.txt

[Java]: https://img.shields.io/badge/Java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/
[JavaFX]: https://img.shields.io/badge/JavaFX-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[JavaFX-url]: https://openjfx.io/
[SQLite]: https://img.shields.io/badge/SQLite-%2307405e?style=for-the-badge&logo=sqlite&logoColor=white
[SQLite-url]: https://sqlite.org/

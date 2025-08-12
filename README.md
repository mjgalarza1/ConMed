<p align="center">
  <img src="github-assets/conmed-logo.svg" alt="ConMed Logo">
</p>

✔️ Para **pacientes** y **médicos** que buscan una **gestión eficiente** de **turnos médicos**.<br>
✔️ Quienes necesitan **agendar**, **modificar** y **administrar turno**s de forma **rápida** y **sencilla**.<br>
✔️ El portal web **_ConMed_**, **es un sistema de gestión de turnos médicos en línea** que facilita la reserva de turnos, la gestión de recetas y la administración de agendas médicas.<br>
✔️ Diferente a los métodos tradicionales, **evita demoras** y **optimiza la organización**.<br>
✔️ _Nuestro proyecto ofrece una plataforma intuitiva que **mejora la experiencia del paciente** y **el profesional de la salud**._

## 📝 Detalles del proyecto
Para la realización de este proyecto, se puso en práctica la metodología ágil **SCRUM**, aprendidas en la materia de **Elementos de Ingeniería de Software** en la **Universidad Nacional de Quilmes**.

### 🤝 Este trabajo fue hecho en equipo, conformado por los siguientes estudiantes:
👤 [**Kevin Stanley**](https://github.com/KevinStanleyUNQV2)<br>
👤 [**Ariel Murua**](https://github.com/amurua)<br>
👤 [**Mauricio Velazquez**](https://github.com/Mauricio-Velazquez)<br>
👤 [**Matías Galarza**](https://github.com/mjgalarza1)<br>
👤 [**Martin Wolf**](https://github.com/martinlwolf)

Los roles de SCRUM **iban rotando por cada sprint**, por lo que todos los miembros del equipo pudieron poner en práctica los siguientes roles:
- **Scrum Master**
- **Product Owner**
- **Desarrollador**
- **Tester**

## 🛠️ Tecnologías utilizadas
- **Backend:** Java, Springboot, Spring Security, MySQL.
- **Frontend:** React + Vite, Axios, React Bootstrap, React Router, React Datepicker.

_(NOTA: Para el frontend, se decidió hacer el diseño completo de la página utilizando **React Bootstrap**, no solo para acelerar el desarrollo del front, sino también para facilitarle a aquellos quienes no tenían mucha experiencia en el front)._

## 🧰 Otras herramientas
- **Jira**: Para gestionar el desarrollo del producto, se utilizó la plataforma **Jira**, donde se realizaron las siguientes tareas:
  - **Sprint Planning**: El equipo elegía las historias de usuario a trabajar en el sprint actual y se definía un objetivo claro, para que el mismo pueda arrancar el sprint con foco y compromiso. Además, se **estimaban los puntos** de cada historia de usuario haciendo uso del **Planning Poker**, para luego calcular la **velocidad** del equipo.
  - **Refinamiento**: El Product Owner se encargaba de manipular el Product Backlog, creando, editando, o eliminando las historias de usuario necesarias, de cara a los futuros sprints, ordenándolos por prioridad siguiendo la Ley de Pareto.
  - **Reporte de Bugs**: Cuando los Testers encontraban algún bug, lo reportaban por este medio, y los desarrolladores se encargaban de solucionarlos.

- **Miro**: Para realizar el evento del **Sprint Retrospective**, se utilizó la plataforma **Miro**, donde el Scrum Master dejaba armadas las actividades para realizar en el evento para cada sprint, y así mejorar como equipo mediante las cosas positivas y negativas para no cometer los mismos errores.
- **Google Sheets**: Se utilizó para armar una planilla con el [test plan de cada sprint](https://docs.google.com/spreadsheets/d/1i60LmYcsdhR-yj7t0_Hy7Zn4ZnUoEwvwH1ZPHxQGDWA/edit?usp=sharing).
- **Google Docs**: Se utilizó para armar un **reporte de seguimiento** de cada sprint, para mantener un seguimiento de cada avance realizado.
- **Brevo**: Se utilizó para el **envío automático de emails** para los pacientes.

## 📥 ¿Cómo instalar el proyecto?
1. Clonar el repositorio
```
git clone https://github.com/mjgalarza1/conmed.git
```
2. Configurar la base de datos:
    - **MySQL**: Crear la base de datos y actualizar `backend/src/main/resources/application.properties` con las credenciales.
3. Configurar Brevo:
   - Crear cuenta y obtener API Key desde el panel de Brevo.
   - Guardar la clave en `backend/src/main/resources/application.properties`.
4. Instalar las dependencias necesarias del frontend abriendo una terminal, ubicarse en la carpeta "frontend", y ejecutar el siguiente comando
```
npm install
```
## 🚀 ¿Cómo ejecutar las aplicaciones?
### API
- Para ejecutar la API, ejecutar `ConmedSpringApp.java` que se encuentra dentro de `backend/src/main/java/ar.edu.unq.spring`.<br>
- La API se levantará de manera local en `http://localhost:8080`
### WEB
1. Para levantar la aplicación web, abrir una terminal, ubicarse en la carpeta `frontend`, y ejecutar el siguiente comando
```
npm run dev
```
- La aplicación web se levantará de manera local en `http://localhost:5173`

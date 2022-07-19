# language: es

@desktop @mobile
Característica: Web de MTP
  Validación de la Web de MTP.
  Vamos a usarlo a modo de ejemplo.

  Antecedentes:
    * Se accede a la web de MTP
    * Se aceptan la cookies

  Escenario:  Acceso a Aseguramiento de la calidad
    * Se navega a Servicios -> Aseguramiento de la calidad
    * Se carga la página Aseguramiento de la calidad

  Escenario:  Error forzado
    * Se carga la página Aseguramiento de la calidad

  Escenario:  El mensaje de aviso de las cookies no debe reaparecer
    * Se navega a Servicios -> Aseguramiento de la calidad
    * Se carga la página Aseguramiento de la calidad
    * El mensaje de aviso de las cookies no se muestra

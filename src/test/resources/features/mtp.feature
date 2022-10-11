# language: es

@desktop @mobile
#noinspection NonAsciiCharacters
Característica: Web de MTP
  Validación de la Web de MTP.
  Vamos a usarlo a modo de ejemplo.

  Antecedentes:
    * Se accede a la web de MTP
    * Se aceptan la cookies

  Escenario: Acceso a Aseguramiento de la calidad
    * Se navega a Servicios -> Aseguramiento de la calidad
    * Se carga la página Aseguramiento de la calidad

  Escenario: Error forzado
    * Se carga la página Aseguramiento de la calidad

  Escenario: El mensaje de aviso de las cookies no debe reaparecer
    * Se navega a Servicios -> Aseguramiento de la calidad
    * Se carga la página Aseguramiento de la calidad
    * El mensaje de aviso de las cookies no se muestra

  Esquema del escenario: Búsqueda en la web de MTP
    * Se busca el término '<search>'
    * El número de resultados para la búsqueda '<search>' es <results>
    * Se obtiene un resultado con título '<title>' y texto '<text>'
    Ejemplos:
      | search | results | title                                                                                                 | text                                                                                  |
      | Mexico | 8       | MTP, 25 años como empresa de referencia en aseguramiento de negocios digitales                        | MTP es hoy una empresa de referencia en Digital Business Assurance                    |
      | Brasil | 11      | MTP, 23 años en el sector de aseguramiento de negocios digitales con una clara vocación internacional | MTP, empresa especializada en Digital Business Assurance, cumple 23 años en este 2020 |

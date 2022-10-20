# language: es

#noinspection NonAsciiCharacters
Característica: Web de MTP
  Validación de la Web de MTP.
  Vamos a usarlo a modo de ejemplo.

  Antecedentes:
    * Se accede a la web de MTP
    * Se aceptan la cookies

  @desktop @mobile
  Escenario: Acceso a Aseguramiento de la calidad
    * Se navega a Servicios -> Aseguramiento de la calidad
    * Se carga la página Aseguramiento de la calidad

  @desktop @mobile
  Escenario: Error forzado
    * Se carga la página Aseguramiento de la calidad

  @desktop @mobile
  Escenario: El mensaje de aviso de las cookies no debe reaparecer
    * Se navega a Servicios -> Aseguramiento de la calidad
    * Se carga la página Aseguramiento de la calidad
    * El mensaje de aviso de las cookies no se muestra

  @desktop
  Esquema del escenario: Búsqueda en la web de MTP
    * Se busca el término '<search>'
    * El número de páginas de resultados para la búsqueda '<search>' es <resultsPages>
    * Se navega a la página <resultsPages> de resultados de la búsqueda
    * La página de resultados mostrada es la última
    * El número de resultados para la búsqueda mostrados es <lastPageResults>
    * Se muestra un resultado para la búsqueda con título '<lastPageResultTitle>' y texto '<lastPageResultText>'
    Ejemplos:
      | search  | resultsPages | lastPageResults | lastPageResultTitle                                                            | lastPageResultText                                                      |
      | Mexico  | 2            | 3               | MTP, 25 años como empresa de referencia en aseguramiento de negocios digitales | MTP es hoy una empresa de referencia en Digital Business Assurance      |
      | Viajero | 2            | 2               | Los valores MTP, claves para este 2020                                         | Este año 2020 ha sido un año particular y totalmente atípico para todos |

Feature: Automatizacion web PET-CLINIC

  @XRAY-21 @TEST_XRAY-6
  Scenario Outline: CP001 - <descripcion>

    Given   Se ingresa a la web de PET CLINIC <caso_prueba>, "<sheet>"
    When    Ingreso a la opcion find owner
    Then    Valido los owners

    Examples:
      | caso_prueba | sheet | descripcion                           |
      | 1           | Login | Validar los nombres de los 10 owners. |

  @XRAY-21 @TEST_XRAY-19
  Scenario Outline: CP002 - <descripcion>

    Given   Se ingresa a la web de PET CLINIC <caso_prueba>, "<sheet>"
    When    Ingreso a la opcion find owner
    And     Se realiza la edicion del primer Owner
    And     Se guardan los cambios validandolos
    Then    Se realiza el rollback con la información anterior

    Examples:
      | caso_prueba | sheet | descripcion                                                              |
      | 2           | Login | Editar del primer Owner de la lista, validar el cambio y hacer rollback. |

  @TEST_XRAY-48
  Scenario Outline: CP003 - <descripcion>

    Given   Se ingresa a la web de PET CLINIC <caso_prueba>, "<sheet>"
    Then    Verifico el ingreso a HOME
    And     Verifico el ingreso a FIND OWNERS
    And     Verifico el ingreso a VETERINARIANS
    And     Verifico el ingreso a ERROR

    Examples:
      | caso_prueba | sheet | descripcion                              |
      | 3           | Login | Validar el ingreso correcto a cada menu. |

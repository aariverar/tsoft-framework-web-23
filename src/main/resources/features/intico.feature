Feature: Automatizacion web PET-CLINIC

  @intico
  Scenario Outline: Validacion de owners

    Given   Se ingresa a la web de PET CLINIC <caso_prueba>, "<sheet>"
    When    Ingreso a la opcion find owner
    Then    Valido los owners

    Examples:
      | caso_prueba | sheet | descripcion   |
      | 1           | Login | Se valida el owner correcto.|
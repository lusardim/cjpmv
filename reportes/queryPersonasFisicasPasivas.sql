SELECT
     personafisica.`apellido` AS personafisica_apellido,
     personafisica.`numeroDocumento` AS personafisica_numeroDocumento,
     personafisica.`legajo` AS personafisica_legajo,
     persona.`nombre` AS persona_nombre,
     persona.`cui` AS persona_cui,
     tipodocumento.`descripcion` AS tipodocumento_descripcion,
     tipobeneficio.`descripcion` AS tipobeneficio_descripcion,
     beneficio.`fechaOtorgamiento` AS beneficio_fechaOtorgamiento,
     personafisica.`estado` AS personafisica_estado
FROM
     `persona` persona INNER JOIN `personafisica` personafisica ON persona.`id` = personafisica.`id`
     INNER JOIN `tipodocumento` tipodocumento ON personafisica.`tipoDocumento_id` = tipodocumento.`id`
     LEFT JOIN beneficio ON personafisica.`beneficio_id` = beneficio.`id`
     LEFT JOIN tipobeneficio ON beneficio.`tipo_id` = tipobeneficio.`id`
WHERE
     personafisica.`estado` = "PASIVO"
ORDER BY
     personafisica_legajo ASC,
     tipobeneficio_descripcion ASC
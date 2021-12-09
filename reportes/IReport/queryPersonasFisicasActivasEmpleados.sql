SELECT
     personafisica.`apellido` AS personafisica_apellido,
     personafisica.`numeroDocumento` AS personafisica_numeroDocumento,
     personafisica.`legajo` AS personafisica_legajo,
     persona.`nombre` AS persona_nombre,
     tipodocumento.`descripcion` AS tipodocumento_descripcion,
     personafisica.`estado` AS personafisica_estado,
     empleo.`situacionRevista` AS empleo_situacionRevista,
     persona.`cui` AS persona_cui,
    (SELECT nombre FROM `persona` p INNER JOIN `personajuridica` j ON p.`id`=j.`id` WHERE p.`id`=empleo.`empresa_id`)
FROM
     `persona` persona INNER JOIN `personafisica` personafisica ON persona.`id` = personafisica.`id`
     INNER JOIN `tipodocumento` tipodocumento ON personafisica.`tipoDocumento_id` = tipodocumento.`id`
     INNER JOIN `empleo` empleo ON personafisica.`id` = empleo.`empleado_id`
WHERE
     personafisica.`estado` = "ACTIVO"
ORDER BY
     empresa_id ASC,
     personafisica_legajo ASC
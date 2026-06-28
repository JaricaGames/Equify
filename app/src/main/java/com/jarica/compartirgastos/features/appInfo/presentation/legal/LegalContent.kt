package com.jarica.compartirgastos.features.appInfo.presentation.legal

import com.jarica.compartirgastos.core.utils.EMAIL_DIRECTION

/**
 * Contenido legal de Equify (BORRADOR).
 *
 * IMPORTANTE: este texto es una plantilla orientativa y NO constituye
 * asesoramiento jurídico. Revísalo y adáptalo antes de publicar en Google Play.
 *
 * Está en español (idioma principal). Las demás localizaciones harán fallback
 * a este texto hasta que se traduzca.
 */
object LegalContent {

    const val LAST_UPDATED = "Última actualización: 28 de junio de 2026"

    val privacyPolicy: List<LegalSection> = listOf(
        LegalSection(
            "1. Introducción",
            "Esta política de privacidad explica cómo Equify (\"la aplicación\") trata la " +
                "información cuando la utilizas. Equify es una aplicación para repartir gastos " +
                "entre personas de forma sencilla. Al usar la aplicación aceptas las prácticas " +
                "descritas en este documento."
        ),
        LegalSection(
            "2. Datos que tratamos",
            "Los datos que introduces en la aplicación (nombres de grupos y participantes, " +
                "gastos, pagos y cantidades) se almacenan únicamente en el almacenamiento local " +
                "de tu dispositivo. No recopilamos, transmitimos ni almacenamos esos datos en " +
                "servidores propios, y no tenemos acceso a ellos. Aparte de estos datos, la app " +
                "trata información de publicidad y de diagnóstico de errores, tal como se " +
                "describe en las secciones 3 y 5."
        ),
        LegalSection(
            "3. Publicidad (Google AdMob)",
            "Equify es gratuita y muestra anuncios a través de Google AdMob. Google y sus socios " +
                "pueden recopilar y utilizar identificadores del dispositivo y datos de uso para " +
                "mostrar publicidad personalizada o no personalizada, de acuerdo con sus propias " +
                "políticas. Puedes consultar cómo Google trata estos datos en " +
                "https://policies.google.com/technologies/partner-sites. Si adquieres la compra " +
                "«Quitar anuncios» (sección 4), los anuncios dejan de mostrarse."
        ),
        LegalSection(
            "4. Compras dentro de la aplicación",
            "Equify ofrece una compra opcional de pago único, «Quitar anuncios», que elimina la " +
                "publicidad de forma permanente. Es totalmente voluntaria. Las compras se " +
                "procesan a través de Google Play: el pago lo gestiona Google y nosotros no " +
                "recibimos ni almacenamos los datos de tu tarjeta o método de pago. Únicamente " +
                "recibimos la confirmación de la compra para activar la función. Al estar " +
                "vinculada a tu cuenta de Google, puedes restaurarla en otro dispositivo o tras " +
                "reinstalar la app, sin coste adicional."
        ),
        LegalSection(
            "5. Diagnóstico de errores (Firebase Crashlytics)",
            "Para mejorar la estabilidad de la aplicación, Equify utiliza Firebase Crashlytics, " +
                "un servicio de Google. Cuando se produce un fallo, recopila y nos envía " +
                "información técnica como el modelo del dispositivo, la versión del sistema " +
                "operativo, la versión de la app y el estado en el momento del error. Estos " +
                "informes no incluyen los datos que introduces ni permiten identificarte " +
                "personalmente, y se usan solo para corregir errores. La app también usa Firebase " +
                "Remote Config para ajustes de configuración (por ejemplo, avisar de " +
                "actualizaciones obligatorias)."
        ),
        LegalSection(
            "6. Permisos",
            "La aplicación solo solicita los permisos necesarios para su funcionamiento (por " +
                "ejemplo, acceso a Internet para cargar anuncios, para los servicios de Google " +
                "—AdMob y Firebase— y para procesar las compras). No accedemos a tu agenda de " +
                "contactos, ubicación ni archivos personales."
        ),
        LegalSection(
            "7. Menores",
            "La aplicación no está dirigida a menores de edad y no recopila conscientemente " +
                "datos personales de menores."
        ),
        LegalSection(
            "8. Tus derechos",
            "Como tus datos se guardan localmente en tu dispositivo, tienes control total sobre " +
                "ellos: puedes eliminarlos en cualquier momento borrando los grupos o " +
                "desinstalando la aplicación. Respecto a los datos tratados por Google con fines " +
                "publicitarios, puedes ejercer tus derechos a través de los ajustes de tu cuenta " +
                "de Google y de tu dispositivo."
        ),
        LegalSection(
            "9. Cambios en esta política",
            "Podemos actualizar esta política de privacidad ocasionalmente. Publicaremos " +
                "cualquier cambio en esta misma pantalla, indicando la fecha de la última " +
                "actualización."
        ),
        LegalSection(
            "10. Contacto",
            "Si tienes dudas sobre esta política de privacidad, puedes escribirnos a " +
                "$EMAIL_DIRECTION."
        ),
    )

    val terms: List<LegalSection> = listOf(
        LegalSection(
            "1. Aceptación de los términos",
            "Al descargar, instalar o usar Equify (\"la aplicación\") aceptas estos términos y " +
                "condiciones de uso. Si no estás de acuerdo con ellos, no utilices la aplicación."
        ),
        LegalSection(
            "2. Descripción del servicio",
            "Equify es una herramienta que te ayuda a registrar gastos y pagos compartidos y a " +
                "calcular cómo saldar las cuentas entre los participantes de un grupo. La " +
                "aplicación funciona principalmente sin conexión y los datos se guardan en tu " +
                "dispositivo."
        ),
        LegalSection(
            "3. Uso correcto",
            "Te comprometes a utilizar la aplicación de forma lícita y a no emplearla para fines " +
                "fraudulentos o que infrinjan derechos de terceros. Eres responsable de la " +
                "exactitud de los datos que introduces."
        ),
        LegalSection(
            "4. Cálculos orientativos",
            "Los balances y las cuentas que muestra la aplicación se ofrecen únicamente con " +
                "carácter informativo y orientativo. No nos hacemos responsables de las " +
                "decisiones económicas que tomes basándote en dichos cálculos; verifica siempre " +
                "los importes."
        ),
        LegalSection(
            "5. Propiedad intelectual",
            "La aplicación, su diseño, su nombre y sus contenidos están protegidos por derechos " +
                "de propiedad intelectual. No se permite copiar, modificar ni distribuir la " +
                "aplicación sin autorización."
        ),
        LegalSection(
            "6. Publicidad y compras",
            "Equify es gratuita y se financia mediante publicidad mostrada a través de Google " +
                "AdMob. De forma opcional puedes adquirir la compra de pago único «Quitar " +
                "anuncios», gestionada por Google Play, que elimina los anuncios de forma " +
                "permanente. El uso de la publicidad y de las compras se rige además por nuestra " +
                "política de privacidad."
        ),
        LegalSection(
            "7. Limitación de responsabilidad",
            "La aplicación se proporciona \"tal cual\", sin garantías de ningún tipo. En la " +
                "medida permitida por la ley, no seremos responsables de daños derivados del uso " +
                "o de la imposibilidad de uso de la aplicación, ni de la pérdida de datos " +
                "almacenados en tu dispositivo."
        ),
        LegalSection(
            "8. Modificaciones",
            "Podemos modificar estos términos en cualquier momento. Los cambios se publicarán en " +
                "esta misma pantalla con su fecha de actualización. El uso continuado de la " +
                "aplicación implica la aceptación de los términos vigentes."
        ),
        LegalSection(
            "9. Contacto",
            "Para cualquier consulta sobre estos términos puedes escribirnos a $EMAIL_DIRECTION."
        ),
    )
}

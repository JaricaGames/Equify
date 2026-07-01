package com.jarica.compartirgastos.core.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Migraciones reales de Room (conservan los datos del usuario al actualizar la app).
 *
 * REGLA: en cuanto la app esté publicada, CADA cambio de esquema debe subir la versión
 * en [AppDataBase] y añadir aquí su Migration(n, n+1), registrándola en RoomModule.
 * Nunca volver a recrear la BD de forma destructiva en una actualización con usuarios reales.
 *
 * Plantilla:
 *
 *   val MIGRATION_3_4 = object : Migration(3, 4) {
 *       override fun migrate(db: SupportSQLiteDatabase) {
 *           db.execSQL("ALTER TABLE ... ")
 *       }
 *   }
 *
 * Ejemplo previsto para la v2 (sincronización online) — añadir el código de invitación al grupo:
 *
 *   val MIGRATION_3_4 = object : Migration(3, 4) {
 *       override fun migrate(db: SupportSQLiteDatabase) {
 *           db.execSQL("ALTER TABLE groups_name ADD COLUMN inviteCode TEXT")
 *       }
 *   }
 *
 * (Ajusta el nombre real de la tabla según GROUPS_NAME_TABLE en core/utils/constants.kt.)
 */

// Lista de migraciones a registrar en RoomModule. De momento vacía: la app aún no se ha
// publicado, así que la versión 3 es la línea base. Añade aquí las migraciones futuras.
val ALL_MIGRATIONS: Array<Migration> = arrayOf(
    // MIGRATION_3_4,
)

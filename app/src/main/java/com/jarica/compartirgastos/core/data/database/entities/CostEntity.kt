package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.utils.COSTS_TABLE

@Entity(
    tableName = COSTS_TABLE,
    indices = [Index("idGroup")],
    foreignKeys = [
        ForeignKey(
            entity = GroupNameEntity::class,
            parentColumns = ["idGroup"],
            childColumns = ["idGroup"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CostEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idCost") val idCost: String,
    @ColumnInfo(name = "amount") val amount: Long,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "idGroup") val idGroup: String?,
    // Preparación para la sincronización remota (resolución de conflictos last-write-wins).
    @ColumnInfo(name = "createdAt", defaultValue = "0") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt", defaultValue = "0") val updatedAt: Long = System.currentTimeMillis(),
)
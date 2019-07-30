package com.codemobiles.cmscb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "company")
data class CompanyEntity (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @NotNull val name: String,
    @NotNull val address: String
)
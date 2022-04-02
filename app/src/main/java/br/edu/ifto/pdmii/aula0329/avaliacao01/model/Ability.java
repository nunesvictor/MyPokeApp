package br.edu.ifto.pdmii.aula0329.avaliacao01.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ability {
    @PrimaryKey(autoGenerate = true)
    public long abilityId;
    @ColumnInfo(name = "is_hidden")
    public boolean isHidden;
    public String ability;
}

package me.hieu.core.procedure;

import lombok.Getter;
import lombok.Setter;
import me.hieu.core.grant.Grant;
import me.hieu.core.punishment.Punishment;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 13/10/2024
 */

@Getter @Setter
public class Procedure <T> {

    private UUID target;
    private UUID uniqueId;
    private ProcedureType procedureType;
    private T object;
    private int step;

    public Procedure(UUID target, UUID uniqueId, ProcedureType procedureType, T object){
        this.target = target;
        this.uniqueId = uniqueId;
        this.procedureType = procedureType;
        this.object = object;
        step = 1;
    }

}

package me.hieu.core.procedure;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 13/10/2024
 */

@Getter
public class ProcedureHandler {

    private HashMap<UUID, Procedure> procedureMap;

    public ProcedureHandler(){
        procedureMap = new HashMap<>();
    }

    public void addProcedure(UUID uniqueId, Procedure procedure){
        procedureMap.put(uniqueId, procedure);
    }

    public Procedure getProcedureByUniqueId(UUID uniqueId){
        for (UUID uuid : procedureMap.keySet()){
            if (uuid.equals(uniqueId)) return procedureMap.get(uuid);
        }
        return null;
    }

}

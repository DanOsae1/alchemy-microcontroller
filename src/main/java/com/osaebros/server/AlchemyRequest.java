package com.osaebros.server;

import java.util.List;

public class AlchemyRequest {

    public AlchemyRequest() {
    }

    List instructions;

    public AlchemyRequest(List instructions) {
        this.instructions = instructions;
    }

    public List getInstructions() {
        return instructions;
    }

    public void setInstructions(List instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "AlchemyRequest{" +
                "instructions=" + instructions +
                '}';
    }
}

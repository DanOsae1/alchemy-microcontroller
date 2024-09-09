package com.osaebros.view.functions;

import com.osaebros.modules.ApplicationFXModuleController;

public class JSFunctionMapper {

    private final ApplicationFXModuleController controller;

    public JSFunctionMapper(ApplicationFXModuleController controller) {
        this.controller = controller;
    }
    public String call(String functionName, String argsJson) {
        // This method will be called from JavaScript
        // Implement your logic here to handle different function calls
        System.out.println("Function called: " + functionName + " with args: " + argsJson);
        return null; // Return appropriate result if needed
    }

    public void log(String message) {
        System.out.println("JS Console: " + message);
    }

}

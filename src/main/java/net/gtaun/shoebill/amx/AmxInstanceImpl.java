package net.gtaun.shoebill.amx;

import net.gtaun.shoebill.SampNativeFunction;
import net.gtaun.shoebill.amx.types.ReturnType;

import java.util.HashMap;
import java.util.function.Function;

public class AmxInstanceImpl implements AmxInstance {

    private final int handle;
    private HashMap<String, Function<Object[], Integer>> registeredFunctions;

    public AmxInstanceImpl(int handle) {
        this.handle = handle;
        this.registeredFunctions = new HashMap<>();
    }

    public int getHandle() {
        return handle;
    }

    @Override
    public AmxCallable getPublic(String name, ReturnType returnType) {
        int funcHandle = SampNativeFunction.getPublic(handle, name);
        if (funcHandle == AmxCallable.INVALID_CALLABLE)
            return null;
        return new AmxCallableImpl(this, funcHandle, name, AmxCallableType.PUBLIC, returnType);
    }

    @Override
    public AmxCallable getNative(String name, ReturnType returnType) {
        int id = SampNativeFunction.getNative(name);
        if(id != 0)
            return new AmxCallableImpl(this, id, name, AmxCallableType.NATIVE, returnType);
        else
            return null;
    }

    @Override
    public boolean registerFunction(String s, Function<Object[], Integer> function, Class... types) {
        if(registeredFunctions.containsKey(s))
            return false;
        boolean result = SampNativeFunction.registerFunction(handle, s, types);
        if(result) registeredFunctions.put(s, function);
        return result;
    }

    @Override
    public boolean unregisterFunction(String s) {
        if(!registeredFunctions.containsKey(s))
            return false;
        boolean result = SampNativeFunction.unregisterFunction(handle, s);
        if(result) registeredFunctions.remove(s);
        return result;
    }

    @Override
    public boolean hasRegisteredFunction(String name) {
        return registeredFunctions.containsKey(name);
    }

    @Override
    public int callRegisteredFunction(String name, Object... parameters) {
        if(!registeredFunctions.containsKey(name))
            return -1;
        return registeredFunctions.get(name).apply(parameters);
    }


}

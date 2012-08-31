package org.dynjs.runtime.builtins.types;

import org.dynjs.runtime.AbstractNativeFunction;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.GlobalObject;
import org.dynjs.runtime.JSObject;
import org.dynjs.runtime.builtins.types.regexp.DynRegExp;
import org.dynjs.runtime.builtins.types.regexp.prototype.Exec;

public class BuiltinRegExp extends AbstractNativeFunction {

    public BuiltinRegExp(final GlobalObject globalObject) {
        super(globalObject, true, "pattern", "flags");
        setPrototype(globalObject.getPrototypeFor("Function"));

        DynRegExp proto = new DynRegExp(globalObject, "", "" );
        put( null, "prototype", proto, false );
        
        proto.put( null, "constructor", this, false );
        proto.put( null, "exec", new Exec(globalObject), false );
        
    }

    @Override
    public Object call(ExecutionContext context, Object self, Object... args) {
        return null;
    }

    @Override
    public JSObject createNewObject(ExecutionContext context) {
        return new DynRegExp(context.getGlobalObject());
    }
    
    public static DynRegExp newRegExp(ExecutionContext context) {
        BuiltinRegExp ctor = (BuiltinRegExp) context.getGlobalObject().get(context, "RegExp");
        return (DynRegExp) context.construct(ctor);
    }

}
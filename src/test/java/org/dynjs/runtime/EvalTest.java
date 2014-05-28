package org.dynjs.runtime;

import static org.fest.assertions.Assertions.*;

import org.dynjs.Config;
import org.junit.Ignore;
import org.junit.Test;

public class EvalTest extends AbstractDynJSTestSupport {

    @Test
    public void testValidFunction() {
        Object result = eval("eval('var x=42');x" );
        assertThat( result ).isEqualTo(42L);
    }

    @Test
    @Ignore
    public void testJIT() {
        eval("function hello(){ return 1; }; for(var i = 0; i < 1000000000; i++) { hello(); }");
    }

    @Override
    protected Config createConfig() {
        final Config cfg = super.createConfig();
        cfg.setCompileMode(Config.CompileMode.IR);
        cfg.setJitEnabled(true);
        cfg.setJitThreshold(1);
        return cfg;
    }
}

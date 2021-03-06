package org.dynjs.ir.instructions;

import org.dynjs.ir.Operand;
import org.dynjs.ir.Operation;
import org.dynjs.ir.operands.Label;

/**
 * Branch EQual
 */
public class BEQ extends Branch {
    private Operand arg1;
    private Operand arg2;

    // FIXME: Add more specialized branches here
    public static Branch create(Operand arg1, Operand arg2, Label target) {
        return new BEQ(arg1, arg2, target);
    }

    public BEQ(Operand arg1, Operand arg2, Label target) {
        super(Operation.BEQ, target);

        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public Operand getArg1() {
        return arg1;
    }

    public Operand getArg2() {
        return arg2;
    }

    public String toString() {
        return "BEQ " + arg1 + ", " + arg2 + " -> " + getTarget();
    }
}

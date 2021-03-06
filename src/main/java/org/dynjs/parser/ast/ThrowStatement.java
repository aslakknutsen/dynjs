/**
 *  Copyright 2013 Douglas Campos, and individual contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.dynjs.parser.ast;

import org.dynjs.exception.ThrowException;
import org.dynjs.parser.CodeVisitor;
import org.dynjs.parser.js.Position;
import org.dynjs.runtime.Completion;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.linker.DynJSBootstrapper;

import java.lang.invoke.CallSite;

public class ThrowStatement extends BaseStatement {

    private final Expression expr;
    private final CallSite get = DynJSBootstrapper.factory().createGet();

    public ThrowStatement(Position position, Expression expr) {
        super(position);
        this.expr = expr;
    }
    
    public Expression getExpr()  {
        return this.expr;
    }
    
    public int getSizeMetric() {
        return this.expr.getSizeMetric() + 1;
    }

    @Override
    public Completion interpret(ExecutionContext context) {
        Object throwable = getValue(this.get, context, getExpr().interpret(context));
        // if ( throwable instanceof Throwable ) {
        // ((Throwable) throwable).printStackTrace();
        // }
        throw new ThrowException(context, throwable);
    }

    public String toIndentedString(String indent) {
        return indent + "throw " + this.expr.toString();
    }

    @Override
    public Object accept(Object context, CodeVisitor visitor, boolean strict) {
        return visitor.visit( context, this, strict );
    }

}

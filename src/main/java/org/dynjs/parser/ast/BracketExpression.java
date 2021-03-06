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

import org.dynjs.parser.CodeVisitor;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.Types;
import org.dynjs.runtime.linker.DynJSBootstrapper;

import java.lang.invoke.CallSite;

/**
 * Access a property with bracket notation
 * 
 * see 11.2.1
 * 
 * @author Douglas Campos
 * @author Bob McWhirter
 */
public class BracketExpression extends AbstractBinaryExpression {

    private final CallSite lhsGet = DynJSBootstrapper.factory().createGet();
    private final CallSite rhsGet = DynJSBootstrapper.factory().createGet();

    public BracketExpression(Expression lhs, Expression rhs) {
        super( lhs, rhs, "[]" );
    }
    
    public String toString() {
        return getLhs() + "[" + getRhs() + "]";
    }

    @Override
    public Object accept(Object context, CodeVisitor visitor, boolean strict) {
        return visitor.visit( context, this, strict );
    }

    @Override
    public Object interpret(ExecutionContext context) {
        Object baseRef = getLhs().interpret(context);
        Object baseValue = getValue(this.lhsGet, context, baseRef);
        Object identifier = getValue(this.rhsGet, context, getRhs().interpret(context));

        Types.checkObjectCoercible(context, baseValue);

        String propertyName = Types.toString(context, identifier);

        return(context.createPropertyReference(baseValue, propertyName));
    }
}

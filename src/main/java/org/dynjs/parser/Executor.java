/**
 *  Copyright 2011 dynjs contributors
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
package org.dynjs.parser;

import me.qmx.jitescript.CodeBlock;
import org.antlr.runtime.tree.CommonTree;
import org.dynjs.api.Function;
import org.dynjs.compiler.DynJSCompiler;
import org.dynjs.parser.statement.BlockStatement;
import org.dynjs.parser.statement.PrintStatement;
import org.dynjs.runtime.DynAtom;
import org.dynjs.runtime.DynFunction;
import org.dynjs.runtime.DynString;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class Executor implements Opcodes {

    private DynJSCompiler compiler = new DynJSCompiler();

    public byte[] program(final List<Statement> blockContent) {
        return new byte[]{};
    }

    public Statement printStatement(final DynAtom expression) {
        return new PrintStatement(expression);
    }

    public DynString createDynString(final CommonTree stringLiteral) {
        return new DynString(stringLiteral.getText());
    }

    public Statement block(final List<Statement> blockContent) {
        return new BlockStatement(blockContent);
    }

    public Function createFunction(List<String> args, final Statement statement) {
        DynFunction function = new DynFunction(args.toArray(new String[]{})) {
            @Override
            public CodeBlock getCodeBlock() {
                return CodeBlock.newCodeBlock(statement.getCodeBlock())
                        .aconst_null()
                        .areturn();
            }
        };
        return compiler.compile(function);
    }
}

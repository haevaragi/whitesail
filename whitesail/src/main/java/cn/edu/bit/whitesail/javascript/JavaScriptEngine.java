/**
* @(#)JavaScriptEngine.java Apr 26, 2009
*
*Copyright 2009 BaiFan
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
*/


package cn.edu.bit.whitesail.javascript;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mozilla.javascript.*;
/**
 *
 * 
 * @author baifan
 * @since 0.1
 */

public class JavaScriptEngine {

    public static class Document extends ScriptableObject{

        
        public Document(){
            
        }
        @Override
        public String getClassName() {
            return "Document";
        }
        
        public void jsFunction_write(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
         // Creates and enters a Context. The Context stores information
         // about the execution environment of a script.
         Context cx = ContextFactory.getGlobal().enterContext();
         try {
             // Initialize the standard objects (Object, Function, etc.)
             // This must be done before scripts can be executed. Returns
             // a scope object that we use in later calls.
             Scriptable scope = cx.initStandardObjects();
            try {
                ScriptableObject.defineClass(scope, Document.class);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(JavaScriptEngine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(JavaScriptEngine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(JavaScriptEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
             Scriptable document = cx.newObject(scope,"Document");
             scope.put("document", scope, document);
             // Collect the arguments into a single string.
             String s = "var xmlHttpReq = new XMLHttpRequest();";
         
             // Now evaluate the string we've colected.
             Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);

             // Convert the result to a string and print it.
             System.err.println(Context.toString(result));

         } finally {
             // Exit from the context.
             Context.exit();
         }
     }
}

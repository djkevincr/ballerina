/*
*   Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.ballerinalang.bre.bvm;

import org.ballerinalang.bre.Context;
import org.ballerinalang.util.codegen.ProgramFile;

/**
 * WorkerContext for store worker related information.
 *
 * @since 0.94
 */
public class WorkerContext extends Context {

    public Context parent;
    StackFrame parentSF;

    public WorkerContext(ProgramFile programFile, Context parent) {
        super(programFile);
        this.parent = parent;
        parentSF = parent.getControlStackNew().currentFrame;
        this.workerCounter = parent.getWorkerCounter();
        this.populateContextPropsFromParent();
    }
    
    private void populateContextPropsFromParent() {
        this.setConnectorFuture(this.parent.getConnectorFuture());
        this.setBallerinaTransactionManager(this.parent.getBallerinaTransactionManager());
        this.setServiceInfo(this.parent.getServiceInfo());
        this.setDebugInfoHolder(this.parent.getDebugInfoHolder());
        if (this.parent.getActivityID() != null) {
            this.setActivityID(this.parent.getActivityID().concat("-worker")
                    .concat(String.valueOf(this.workerCounter.getCurrentWorkerCount() + 1)));
        }
    }

}

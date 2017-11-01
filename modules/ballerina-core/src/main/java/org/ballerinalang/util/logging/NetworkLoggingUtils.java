/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.ballerinalang.util.logging;

import org.ballerinalang.bre.Context;
import org.ballerinalang.util.codegen.ActionInfo;
import org.ballerinalang.util.codegen.ResourceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Utility class to provide network logging features.
 *
 * @since 0.95.0
 */
public class NetworkLoggingUtils {

    private static final String TRANSPORT_PROVIDED_ACTIVITY_ID = "activityid.ballerina";
    private static final String BALLERINA_NETWORK_LOGGER_NAME = "networklog.ballerina";
    private static final Logger networkLogger = LoggerFactory.getLogger(BALLERINA_NETWORK_LOGGER_NAME);

    public static void generateActivityID(Context context) {
        if (context.getProperty(TRANSPORT_PROVIDED_ACTIVITY_ID) != null) {
            context.setActivityID((String) context.getProperty(TRANSPORT_PROVIDED_ACTIVITY_ID));
        } else {
            context.setActivityID(UUID.randomUUID().toString());
        }
    }

    public static void logResourceDispatch(String activityID, ResourceInfo resourceInfo) {
        if (networkLogger.isDebugEnabled()) {
            StringBuilder logString = new StringBuilder();
            logString.append("[activityid: ").append(activityID).append(" ]").append(" - ");
            logString.append("[service: ").append(resourceInfo.getServiceInfo().getName()).append(" , ")
                    .append("protocol: ").append(resourceInfo.getServiceInfo().getProtocolPkgPath()).append(" , ")
                    .append("package: ").append(resourceInfo.getServiceInfo().getPackagePath()).append(" ]");
            logString.append(" DISPATCHED TO RESOURCE ").append(resourceInfo.getName());
            networkLogger.debug(logString.toString());
        }
    }

    public static void logConnectorActionDispatch(String activityID, ActionInfo actionInfo) {
        if (networkLogger.isDebugEnabled()) {
            StringBuilder logString = new StringBuilder();
            logString.append("[activityid: ").append(activityID).append(" ]").append(" - ");
            logString.append("[connector: ").append(actionInfo.getConnectorInfo().getName()).append(" , ")
                    .append("package: ").append(actionInfo.getConnectorInfo().getPackagePath()).append(" ]");
            logString.append(" DISPATCHED TO ACTION ").append(actionInfo.getName());
            networkLogger.debug(logString.toString());
        }
    }

}

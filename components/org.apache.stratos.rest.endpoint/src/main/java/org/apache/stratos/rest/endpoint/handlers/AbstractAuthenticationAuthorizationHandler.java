package org.apache.stratos.rest.endpoint.handlers;/*
*  Copyright (c) 2005-2012, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.impl.HttpHeadersImpl;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.stratos.rest.endpoint.context.AuthenticationContext;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class AbstractAuthenticationAuthorizationHandler implements RequestHandler {




    public Response handleRequest(Message message, ClassResourceInfo classResourceInfo) {
        HttpHeaders headers = new HttpHeadersImpl(message);
        List<String> authHeader = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if(!AuthenticationContext.isAthenticated() && authHeader != null && authHeader.size() > 0 &&
                canHandle(authHeader.get(0).trim().split(" ")[0])){
             return handle(message,classResourceInfo);
        }
        // give the control to the next handler
        return null;

    }

    protected abstract boolean canHandle(String authHeaderPrefix);
    protected abstract Response handle(Message message,ClassResourceInfo classResourceInfo);
}

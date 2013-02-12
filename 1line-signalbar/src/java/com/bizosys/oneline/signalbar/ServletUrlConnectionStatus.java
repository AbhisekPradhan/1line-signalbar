/*
* Copyright 2010 Bizosys Technologies Limited
*
* Licensed to the Bizosys Technologies Limited (Bizosys) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The Bizosys licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.bizosys.oneline.signalbar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUrlConnectionStatus extends HttpServlet {

	private static final long serialVersionUID = -1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path = readInput(request, "path"); 
		int pathLen = (null == path) ? 0 : path.length();
		if ( pathLen == 0) {
			response.sendError(404, "Path [path] is missing");
			return;
		}
		
		String impagePrefix = readInput(request, "image"); 
		int impagePrefixLen = (null == impagePrefix) ? 0 : impagePrefix.length();
		if ( impagePrefixLen == 0) {
			response.sendError(404, "Image Prefix [image] is missing is missing");
			return;
		}
		
		if ( ! impagePrefix.endsWith("/")) impagePrefix = impagePrefix + "/";

		response.setContentType("text/xml");
		
		try {
			long timeBar = URLConnectionStatus.checkUrl(path);
			response.sendRedirect(response.encodeRedirectURL(impagePrefix + timeBar + ".png"));
		} catch (Exception ex) {}
		
	}

	public String readInput(HttpServletRequest request, String paramName) {
		String path = request.getParameter(paramName);
			if ( null == path) {
			if ( request.getAttribute(paramName) != null ) 
				path = request.getAttribute(paramName).toString();
		}
		return path;
	}

}

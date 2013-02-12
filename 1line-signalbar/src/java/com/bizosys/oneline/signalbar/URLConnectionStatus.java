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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @param args
 *     0 - Not able to connect 1 - slow connection :- 1 bar 2 - 3 4 5
 *     Full Bar means good connection
 * @param urlName
 * @return
 **/

public class URLConnectionStatus {

	public static int checkUrl(String urlName) {

		long startTime = System.currentTimeMillis();
		long endTime = -1;
		int statusCode = -1;

		try {
			URL url = new URL(urlName);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			statusCode = connection.getResponseCode();
		} catch (ProtocolException ex) {
			return 0;
		} catch (UnknownHostException ex) {
			return 0;
		} catch (MalformedURLException ex) {
			return 0;
		} catch (IOException ex) {
			return 0;
		} finally {
			endTime = System.currentTimeMillis();
			System.out.println("statusCode : " + statusCode);

			if (statusCode != 200) {
				return 0;
			}
		}

		long timeTaken = endTime - startTime;
		System.out.println("Response Time Taken : " + timeTaken);
		if (timeTaken < 100) return 5;
		else if (timeTaken < 300) return 4;
		else if (timeTaken < 500) return 3;
		else if (timeTaken < 1000) return 2;
		else if (timeTaken < 5000) return 1;
		else return 0;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(checkUrl("http://superspademo.appspot.com/home.html"));

	}

}

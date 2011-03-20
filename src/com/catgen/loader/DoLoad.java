package com.catgen.loader;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class DoLoad {

	public static void main( String[] argv) {

		MasterMarketLoader masterMarket = new MasterMarketLoader();
		masterMarket.LoadData(null, "http://spreadsheets.google.com/feeds/cells/p2DIlEfXXvRGM1iKwllxSaw/od6/public/basic");
		
		/*MembersLoader membersLoader = new MembersLoader();
		membersLoader.ClearExtras();
		membersLoader.AddExtras("MarketID", "market2");
		membersLoader.LoadData("http://spreadsheets.google.com/feeds/cells/p2DIlEfXXvRGop-VDBypYZg/od6/public/basic");
		
		NetworkMarketInfoLoader networkMarketInfoLoader = new NetworkMarketInfoLoader();
		networkMarketInfoLoader.ClearExtras();
		networkMarketInfoLoader.AddExtras("MarketID", "market2");
		networkMarketInfoLoader.LoadData("http://spreadsheets.google.com/feeds/cells/p2DIlEfXXvRGop-VDBypYZg/od7/public/basic");*/
	}
}


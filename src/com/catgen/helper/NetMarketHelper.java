package com.catgen.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catgen.NetworkMarket;
import com.catgen.factories.NetMarketFactory;

public class NetMarketHelper {
	public static List<NetworkMarket> getOtherNetworkMarkets(Connection conn, List<NetworkMarket> availableNetworkMarkets) throws SQLException{
		List<NetworkMarket> allNetworkMarkets = NetMarketFactory.getNetmarketMembers(conn);
		List<NetworkMarket> otherNetworkMarkets = new ArrayList<NetworkMarket>();
		if(allNetworkMarkets!=null){
			if(availableNetworkMarkets!=null && availableNetworkMarkets.size()>0){
				for(NetworkMarket oneOfAllNetworkMarket: allNetworkMarkets){
					boolean present = false;
					for(NetworkMarket oneOfAvailableNetworkMarket: availableNetworkMarkets){
						if(oneOfAvailableNetworkMarket.NetworkMarketID.equalsIgnoreCase(oneOfAllNetworkMarket.NetworkMarketID)){
							present = true;
							break;
						}
					}
					if(!present){
						otherNetworkMarkets.add(oneOfAllNetworkMarket);
					}
				}
			}else{
				return allNetworkMarkets;
			}
		}
		return otherNetworkMarkets;
	}
}

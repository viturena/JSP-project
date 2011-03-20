package com.catgen.xml;

import java.sql.Connection;
import java.sql.SQLException;

import org.w3c.dom.Element;

import com.catgen.Company;
import com.catgen.NetworkMarket;
import com.catgen.Product;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.ProductFactory;

public class NetworkMarketXml 
{
	public void AddToXml(Connection conn, String marketId, Element element) throws SQLException
	{
		Element e;

		NetworkMarket networkMarket = NetMarketFactory.getNetworkMarketByCode(conn, marketId);
		if(networkMarket != null )
		{
			e = element.getOwnerDocument().createElement("NetworkMarket");
			networkMarket.AddToXml(e);
			element.appendChild(e);
			
			Element members = element.getOwnerDocument().createElement("MarketMembers");
			element.appendChild(members);

			for(Company company: CompanyFactory.getNetmarketMembers(conn, marketId) )
			{
				e = element.getOwnerDocument().createElement("Company");
				members.appendChild(e);

				company.AddToXml(e);
			}

			Element products = element.getOwnerDocument().createElement("Products");
			element.appendChild(products);

			for(Product product: ProductFactory.getMarketProducts(conn, marketId))
			{
				e = element.getOwnerDocument().createElement("Product");
				products.appendChild(e);

				product.AddToXml(e);
			}
		}
	}
}

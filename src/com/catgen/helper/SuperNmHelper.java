package com.catgen.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catgen.Company;
import com.catgen.KeyValue;
import com.catgen.MasterMarket;
import com.catgen.NetworkMarket;
import com.catgen.Product;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.MasterMarketFactory;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.SuperNMFactory;
import com.catgen.factories.ProductFactory;

public class SuperNmHelper {
	private List<String> fetched;
	private List<String> fresh;
	
	public static boolean isSuperNm(Connection conn, String marketId) throws SQLException{
		MasterMarket masterMarket = MasterMarketFactory.getMasterMarketByCode(conn, marketId);
		if(masterMarket!=null && masterMarket.IsSuperNm){
			return true;
		}
		return false;
	}
	
	public List<String> findAllContainedNMBySuperMarketId(Connection conn, String marketId) throws SQLException{
		fetched = new ArrayList<String>();
		fresh = new ArrayList<String>();
		fresh.add(marketId.toLowerCase());
		while(fresh.size()>0){
			fetched.add(fresh.get(0).toLowerCase());
			fresh.remove(0);
			List<String> tempList = SuperNMFactory.getContainedNmByContainerNm(conn, fetched.get(fetched.size()-1));
			if(tempList!=null){
				for(int i=0;i<tempList.size();i++){
					String current = tempList.get(i).toLowerCase();
					if(!fetched.contains(current) && !fresh.contains(current)){
						fresh.add(current);
					}
				}
			}
		}
		return fetched;
	}
	
	public static List<NetworkMarket> findAllContainedNMObjBySuperMarketId(Connection conn, String superMarketId) throws SQLException{
		List<String> containedNMStringList = new SuperNmHelper().findAllContainedNMBySuperMarketId(conn, superMarketId);
		List<NetworkMarket> networkMarkets = new ArrayList<NetworkMarket>();
		if(containedNMStringList!=null && containedNMStringList.size()>0){
			for(String marketId: containedNMStringList){
				NetworkMarket networkMarket = NetMarketFactory.getNetworkMarketByCode(conn, marketId);
				networkMarkets.add(networkMarket);
			}
		}
		return networkMarkets;
	}
	
	public static List<Company> findAllContainedCompaniesBySuperMarketId(Connection conn, String marketId) throws SQLException{
		List<String> containedNMList = new SuperNmHelper().findAllContainedNMBySuperMarketId(conn, marketId);
		List<Company> containedCompanyList = new ArrayList<Company>();
		for(String subMarketId: containedNMList){
			List<Company> companies = CompanyFactory.getAcceptedCompaniesByMarketId(conn, subMarketId);
			if(companies!=null){
				for(Company company: companies){
					if(!containedCompanyList.contains(company)){
						containedCompanyList.add(company);
					}
				}
			}
		}
		return containedCompanyList;
	}
	
	public static List<Product> findAllContainedProductsBySuperMarketId(Connection conn, String marketId) throws SQLException{
		List<Company> companies = findAllContainedCompaniesBySuperMarketId(conn, marketId);
		List<Product> allContainedProducts = new ArrayList<Product>();
		if(companies!=null){
			for(Company company: companies){
				List<Product> singleCompanyProducts = ProductFactory.getProducts(conn, company.Code);
				if(singleCompanyProducts!=null && singleCompanyProducts.size()>0){
					for(Product product: singleCompanyProducts){
						allContainedProducts.add(product);
					}
				}
			}
		}
		return allContainedProducts;
	}
	
	public static List<Product> findProductsWithKeywordBySuperMarketId(Connection conn, String marketId, String keyword, String companyCode) throws SQLException{
		List<Company> companies = new ArrayList<Company>();
		if(companyCode!=null && companyCode.length()>0){
			companies.add(CompanyFactory.getCompanyByCode(conn, companyCode));
		}else{
			companies = findAllContainedCompaniesBySuperMarketId(conn, marketId);
		}
		List<Product> allContainedProducts = new ArrayList<Product>();
		if(companies!=null){
			for(Company company: companies){
				List<Product> singleCompanyProducts = ProductFactory.getCompanyProductsWithMatchingKeyword(conn, company.Code, keyword);
				if(singleCompanyProducts!=null && singleCompanyProducts.size()>0){
					for(Product product: singleCompanyProducts){
						allContainedProducts.add(product);
					}
				}
			}
		}
		return allContainedProducts;
	}
	
	public static List<Product> findProductsWithParametersBySuperMarketId(Connection conn, String marketId, ArrayList<KeyValue> parameters, String companyCode) throws SQLException{
		List<Company> companies = new ArrayList<Company>();
		if(companyCode!=null && companyCode.length()>0){
			companies.add(CompanyFactory.getCompanyByCode(conn, companyCode));
		}else{
			companies = findAllContainedCompaniesBySuperMarketId(conn, marketId);
		}
		List<Product> searchedProducts = new ArrayList<Product>();
		for(Company company: companies){
			List<Product> singleCompanyProducts = ProductFactory.getProductsByCompanyCodeAndParameters(conn, company.Code, parameters);
			if(singleCompanyProducts!=null && singleCompanyProducts.size()>0){
				for(Product product: singleCompanyProducts){
					searchedProducts.add(product);
				}
			}
		}
		return searchedProducts;
	}
	
	public static List<String> getCountryListBySuperMarketId(Connection conn, String marketId) throws SQLException{
		List<Company> companies = findAllContainedCompaniesBySuperMarketId(conn, marketId);
		List<String> countryList = new ArrayList<String>();
		if(companies!=null){
			for(Company company: companies){
				if(company.Country!=null && company.Country.length()>0){
					boolean alreadyPresentInList = false;
					for(String country: countryList){
						if(country.trim().equalsIgnoreCase(company.Country.trim())){
							alreadyPresentInList = true;
							break;
						}
					}
					if(!alreadyPresentInList){
						countryList.add(company.Country);
					}
				}
			}
		}
		return countryList;
	}
	
	public static List<NetworkMarket> getAllSuperNMList(Connection conn) throws SQLException{
		List<MasterMarket> masterMarkets = MasterMarketFactory.getMasterMembers(conn);
		List<NetworkMarket> networkMarkets = new ArrayList<NetworkMarket>();
		for(MasterMarket masterMarket: masterMarkets){
			if(masterMarket.IsSuperNm){
				NetworkMarket networkMarket = NetMarketFactory.getNetworkMarketByCode(conn, masterMarket.NetworkMarketID);
				networkMarkets.add(networkMarket);
			}
		}
		return networkMarkets;
	}
}

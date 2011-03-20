package com.catgen.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.catgen.Product;
import com.catgen.Utils;

public class ProductFilterThread extends Thread{
	
	private Product product;
	private static List<Product> products = null;
	private static List<Product> productsWithNoImage = null;
	
	public ProductFilterThread(Product product){
		this.product = product;
	}
	
	public synchronized void addProduct(Product product){
		getProductsWithNoImage().add(product);
	}
	
	public synchronized List<Product> getProductsWithNoImage(){
		return productsWithNoImage;
	}
	
	public void run(){
		if(this.product.ImageURL==null || this.product.ImageURL.length()==0){
			addProduct(this.product);
		}else if(this.product.ImageURL.indexOf("ggpht.com")<1){
			//ignoring picasa image check
			boolean imageExists = Utils.fileExists(this.product.ImageURL);
			if(!imageExists){
				addProduct(this.product);
			}
		}
	}
	
	public static List<Product> findProductsWithNoImage(List<Product> prods){
		products = prods;
		productsWithNoImage = new ArrayList<Product>();
		try{
			List<ProductFilterThread> threads = new ArrayList<ProductFilterThread>();
			for(Product product: products){
				ProductFilterThread pft = new ProductFilterThread(product);
				pft.start();
				threads.add(pft);
			}
			Iterator<ProductFilterThread> threadsIter = threads.iterator();
			while(threadsIter.hasNext()){
				ProductFilterThread thread = threadsIter.next();
				thread.join();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return productsWithNoImage;
	}
}

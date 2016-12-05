package com.gzu.simpleSQL;

import java.util.Scanner;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BPTree bTree = new BPTree(6);
		try {
			bTree.creatRecords();
			BPTree.readRecords(bTree);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean exit = false;
		while(!exit){
			System.out.println("***************************************");
			System.out.println("**  1.Search By Value;              ***");
			System.out.println("**  2.Insert A Value;               ***");
			System.out.println("**  3.Delete A Value;               ***");
			System.out.println("**  4.Exit The System.              ***");
			System.out.println("***************************************");
			int choice = 0;
			Scanner scanner = new Scanner(System.in);
			choice = scanner.nextInt();
			int key;
			Object object;
			switch(choice){
			case 1:
				System.out.println("Input the key: ");
				key = scanner.nextInt();
				object =  bTree.get(key);
				if(object == null)
				{
					System.out.println("Tere is no key for " + key);
				}
				else{
					System.out.println("Searching successfully");
				}
				break;
			case 2:
				System.out.println("Input the key: ");
				key = scanner.nextInt();
				bTree.insertOrUpdate(key, key);
				System.out.println("Insert successfully");
				break;
			case 3:
				System.out.println("Input the key: ");
				key = scanner.nextInt();
				if(bTree.remove(key)){
					System.out.println("Delete successfully");
				}
				else
				{
					System.out.println("There is no key of "+key+" to delete");
				}
				break;
			case 4:
				exit = true;
				break;
			default:
				exit = true;
				break;
			}
		}
		
	}

}

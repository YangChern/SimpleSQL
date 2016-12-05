package com.gzu.simpleSQL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class BPTree implements Tree {
	
	private static int size = 100000;//�ܼ�¼��100,000
	private static int sizePerBlock = 40;//���ڴ�����ÿ��block��С��4KB,ÿ����¼��С100B,����ÿ��block�ϼ�¼����Ϊ40
	private static int blockSize = (size%sizePerBlock)==0?(size/sizePerBlock):(size/sizePerBlock)+1; //�ܵĿ���
	private static String fileDirectory = "F:\\Records\\";
	private static String recordFile = fileDirectory+"record.txt";
	
	/** ���ڵ� */
	protected Node root;
	
	/** ������Mֵ */
	protected int order;
	
	/** Ҷ�ӽڵ������ͷ*/
	protected Node head;
	
	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public Object get(Comparable<Integer> key) {
		return root.get(key);
	}

	@Override
	public boolean remove(Comparable<Integer> key) {
		return root.remove(key, this);

	}

	@Override
	public void insertOrUpdate(Comparable<Integer> key, Object obj) {
		root.insertOrUpdate(key, obj, this);

	}
	
	public BPTree(int order){
		if (order < 3) {
			System.out.print("order must be greater than 2");
			System.exit(0);
		}
		this.order = order;
		root = new Node(true, true);
		head = root;
	}
	
	public void creatRecords() throws Exception{
		
		File file = new File(recordFile);
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		System.out.println("Creating records...");
		for(int j = 0;j<blockSize;j++){
			for(int i =0;i<sizePerBlock;i++){
				if(!(i == 0 && j == 0)){
					out.newLine();
				}
				out.write(new Record().getRecordString());
			}
		}
		out.close();
	}
	public static void readRecords(BPTree btree) throws Exception{
		BufferedReader in = new BufferedReader( new FileReader(recordFile));
		String line;
		System.out.println("Building B+ Tree...");
		for(int j = 0;j<blockSize;j++){
			for(int i =0;i<sizePerBlock;i++){
				line = in.readLine();
				Record r = new Record(line);
				int key = r.getA();
				btree.insertOrUpdate(key, key);
			}
		}
		in.close();
	}

}

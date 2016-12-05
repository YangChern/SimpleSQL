package com.gzu.simpleSQL;

public interface Tree {
	public Object get(Comparable<Integer> key);   //��ѯ
	public boolean remove(Comparable<Integer> key);    //�Ƴ�
	public void insertOrUpdate(Comparable<Integer> key, Object obj); //������߸��£�����Ѿ����ڣ����£��������
}
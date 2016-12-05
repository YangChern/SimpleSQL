package com.gzu.simpleSQL;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class Node {
	
	/** �Ƿ�ΪҶ�ӽڵ� */
	protected boolean isLeaf;
	
	/** �Ƿ�Ϊ���ڵ�*/
	protected boolean isRoot;

	/** ���ڵ� */
	protected Node parent;
	
	/** Ҷ�ڵ��ǰ�ڵ�*/
	protected Node previous;
	
	/** Ҷ�ڵ�ĺ�ڵ�*/
	protected Node next;	
	
	/** �ڵ�Ĺؼ��� */
	protected List<Entry<Comparable, Object>> entries;
	
	/** �ӽڵ� */
	protected List<Node> children;
	
	public Node(boolean isLeaf) {
		this.isLeaf = isLeaf;
		entries = new ArrayList<Entry<Comparable, Object>>();
		
		if (!isLeaf) {
			children = new ArrayList<Node>();
		}
	}

	public Node(boolean isLeaf, boolean isRoot) {
		this(isLeaf);
		this.isRoot = isRoot;
	}
	
	public Object get(Comparable key) {
		
		//�����Ҷ�ӽڵ�
		if (isLeaf) {
			for (Entry<Comparable, Object> entry : entries) {
				if (entry.getKey().compareTo(key) == 0) {
					//�����ҵ��Ķ���
					return entry.getValue();
				}
			}
			//δ�ҵ���Ҫ��ѯ�Ķ���
			return null;
			
		//�������Ҷ�ӽڵ�
		}else {
			//���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ��������
			if (key.compareTo(entries.get(0).getKey()) <= 0) {
				return children.get(0).get(key);
			//���key���ڽڵ����ұߵ�key�������һ���ӽڵ��������
			}else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
				return children.get(children.size()-1).get(key);
			//�����ر�key���ǰһ���ӽڵ��������
			}else {
				for (int i = 0; i < entries.size(); i++) {
					if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) {
						return children.get(i).get(key);
					}
				}	
			}
		}
		
		return null;
	}
	
	public void insertOrUpdate(Comparable key, Object obj, BPTree tree){
		//�����Ҷ�ӽڵ�
		if (isLeaf){
			//����Ҫ���ѣ�ֱ�Ӳ�������
			if (contains(key) || entries.size() < tree.getOrder()){
				insertOrUpdate(key, obj);
				if (parent != null) {
					//���¸��ڵ�
					parent.updateInsert(tree);
				}

			//��Ҫ����	
			}else {
				//���ѳ����������ڵ�
				Node left = new Node(true);
				Node right = new Node(true);
				//��������
				if (previous != null){
					previous.setNext(left);
					left.setPrevious(previous);
				}
				if (next != null) {
					next.setPrevious(right);
					right.setNext(next);
				}
				if (previous == null){
					tree.setHead(left);
				}

				left.setNext(right);
				right.setPrevious(left);
				previous = null;
				next = null;
				
				//���������ڵ�ؼ��ֳ���
				int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2; 
				int rightSize = (tree.getOrder() + 1) / 2;
				//����ԭ�ڵ�ؼ��ֵ����ѳ������½ڵ�
				insertOrUpdate(key, obj);
				for (int i = 0; i < leftSize; i++){
					left.getEntries().add(entries.get(i));
				}
				for (int i = 0; i < rightSize; i++){
					right.getEntries().add(entries.get(leftSize + i));
				}
				
				//������Ǹ��ڵ�
				if (parent != null) {
					//�������ӽڵ��ϵ
					int index = parent.getChildren().indexOf(this);
					parent.getChildren().remove(this);
					left.setParent(parent);
					right.setParent(parent);
					parent.getChildren().add(index,left);
					parent.getChildren().add(index + 1, right);
					setEntries(null);
					setChildren(null);
					
					//���ڵ�������¹ؼ���
					parent.updateInsert(tree);
					setParent(null);
				//����Ǹ��ڵ�	
				}else {
					isRoot = false;
					Node parent = new Node(false, true);
					tree.setRoot(parent);
					left.setParent(parent);
					right.setParent(parent);
					parent.getChildren().add(left);
					parent.getChildren().add(right);
					setEntries(null);
					setChildren(null);
					
					//���¸��ڵ�
					parent.updateInsert(tree);
				}
				

			}
			
		//�������Ҷ�ӽڵ�
		}else {
			//���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ��������
			if (key.compareTo(entries.get(0).getKey()) <= 0) {
				children.get(0).insertOrUpdate(key, obj, tree);
			//���key���ڽڵ����ұߵ�key�������һ���ӽڵ��������
			}else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
				children.get(children.size()-1).insertOrUpdate(key, obj, tree);
			//�����ر�key���ǰһ���ӽڵ��������
			}else {
				for (int i = 0; i < entries.size(); i++) {
					if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) {
						children.get(i).insertOrUpdate(key, obj, tree);
						break;
					}
				}	
			}
		}
	}
	
	/** ����ڵ���м�ڵ�ĸ��� */
	protected void updateInsert(BPTree tree){

		validate(this, tree);
		
		//����ӽڵ�����������������Ҫ���Ѹýڵ�	
		if (children.size() > tree.getOrder()) {
			//���ѳ����������ڵ�
			Node left = new Node(false);
			Node right = new Node(false);
			//���������ڵ�ؼ��ֳ���
			int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;
			int rightSize = (tree.getOrder() + 1) / 2;
			//�����ӽڵ㵽���ѳ������½ڵ㣬�����¹ؼ���
			for (int i = 0; i < leftSize; i++){
				left.getChildren().add(children.get(i));
				left.getEntries().add(new SimpleEntry(children.get(i).getEntries().get(0).getKey(), null));
				children.get(i).setParent(left);
			}
			for (int i = 0; i < rightSize; i++){
				right.getChildren().add(children.get(leftSize + i));
				right.getEntries().add(new SimpleEntry(children.get(leftSize + i).getEntries().get(0).getKey(), null));
				children.get(leftSize + i).setParent(right);
			}
			
			//������Ǹ��ڵ�
			if (parent != null) {
				//�������ӽڵ��ϵ
				int index = parent.getChildren().indexOf(this);
				parent.getChildren().remove(this);
				left.setParent(parent);
				right.setParent(parent);
				parent.getChildren().add(index,left);
				parent.getChildren().add(index + 1, right);
				setEntries(null);
				setChildren(null);
				
				//���ڵ���¹ؼ���
				parent.updateInsert(tree);
				setParent(null);
			//����Ǹ��ڵ�	
			}else {
				isRoot = false;
				Node parent = new Node(false, true);
				tree.setRoot(parent);
				left.setParent(parent);
				right.setParent(parent);
				parent.getChildren().add(left);
				parent.getChildren().add(right);
				setEntries(null);
				setChildren(null);
				
				//���¸��ڵ�
				parent.updateInsert(tree);
			}
		}
	}
	
	/** �����ڵ�ؼ���*/
	protected static void validate(Node node, BPTree tree) {
		
		// ����ؼ��ָ������ӽڵ������ͬ
		if (node.getEntries().size() == node.getChildren().size()) {
			for (int i = 0; i < node.getEntries().size(); i++) {
				Comparable key = node.getChildren().get(i).getEntries().get(0).getKey();
				if (node.getEntries().get(i).getKey().compareTo(key) != 0) {
					node.getEntries().remove(i);
					node.getEntries().add(i, new SimpleEntry(key, null));
					if(!node.isRoot()){
						validate(node.getParent(), tree);
					}
				}
			}
			// ����ӽڵ��������ڹؼ��ָ������Դ���M / 2����С��M�����Ҵ���2
		} else if (node.isRoot() && node.getChildren().size() >= 2 
				||node.getChildren().size() >= tree.getOrder() / 2 
				&& node.getChildren().size() <= tree.getOrder()
				&& node.getChildren().size() >= 2) {
			node.getEntries().clear();
			for (int i = 0; i < node.getChildren().size(); i++) {
				Comparable key = node.getChildren().get(i).getEntries().get(0).getKey();
				node.getEntries().add(new SimpleEntry(key, null));
				if (!node.isRoot()) {
					validate(node.getParent(), tree);
				}
			}
		}
	}
	
	/** ɾ���ڵ���м�ڵ�ĸ���*/
	protected void updateRemove(BPTree tree) {
		
		validate(this, tree);

		// ����ӽڵ���С��M / 2����С��2������Ҫ�ϲ��ڵ�
		if (children.size() < tree.getOrder() / 2 || children.size() < 2) {
			if (isRoot) {
				// ����Ǹ��ڵ㲢���ӽڵ������ڵ���2��OK
				if (children.size() >= 2) {
					return;
				// �������ӽڵ�ϲ�
				} else {
					Node root = children.get(0);
					tree.setRoot(root);
					root.setParent(null);
					root.setRoot(true);
					setEntries(null);
					setChildren(null);
				}
			} else {
				//����ǰ��ڵ� 
				int currIdx = parent.getChildren().indexOf(this);
				int prevIdx = currIdx - 1;
				int nextIdx = currIdx + 1;
				Node previous = null, next = null;
				if (prevIdx >= 0) {
					previous = parent.getChildren().get(prevIdx);
				}
				if (nextIdx < parent.getChildren().size()) {
					next = parent.getChildren().get(nextIdx);
				}
				
				// ���ǰ�ڵ��ӽڵ�������M / 2���Ҵ���2������䴦�貹
				if (previous != null 
						&& previous.getChildren().size() > tree.getOrder() / 2
						&& previous.getChildren().size() > 2) {
					//ǰҶ�ӽڵ�ĩβ�ڵ���ӵ���λ
					int idx = previous.getChildren().size() - 1;
					Node borrow = previous.getChildren().get(idx);
					previous.getChildren().remove(idx);
					borrow.setParent(this);
					children.add(0, borrow);
					validate(previous, tree);
					validate(this, tree);
					parent.updateRemove(tree);
					
				// �����ڵ��ӽڵ�������M / 2���Ҵ���2������䴦�貹
				} else if (next != null	
						&& next.getChildren().size() > tree.getOrder() / 2
						&& next.getChildren().size() > 2) {
					//��Ҷ�ӽڵ���λ��ӵ�ĩβ
					Node borrow = next.getChildren().get(0);
					next.getChildren().remove(0);
					borrow.setParent(this);
					children.add(borrow);
					validate(next, tree);
					validate(this, tree);
					parent.updateRemove(tree);
					
				// ������Ҫ�ϲ��ڵ�
				} else {
					// ͬǰ��ڵ�ϲ�
					if (previous != null 
							&& (previous.getChildren().size() <= tree.getOrder() / 2 || previous.getChildren().size() <= 2)) {
						
						for (int i = previous.getChildren().size() - 1; i >= 0; i--) {
							Node child = previous.getChildren().get(i);
							children.add(0, child);
							child.setParent(this);
						}
						previous.setChildren(null);
						previous.setEntries(null);
						previous.setParent(null);
						parent.getChildren().remove(previous);
						validate(this, tree);
						parent.updateRemove(tree);
						
					// ͬ����ڵ�ϲ�
					} else if (next != null	
							&& (next.getChildren().size() <= tree.getOrder() / 2 || next.getChildren().size() <= 2)) {

						for (int i = 0; i < next.getChildren().size(); i++) {
							Node child = next.getChildren().get(i);
							children.add(child);
							child.setParent(this);
						}
						next.setChildren(null);
						next.setEntries(null);
						next.setParent(null);
						parent.getChildren().remove(next);
						validate(this, tree);
						parent.updateRemove(tree);
					}
				}
			}
		}
	}
	
	public boolean remove(Comparable key, BPTree tree){
		//�����Ҷ�ӽڵ�
		boolean foud = false;
		if (isLeaf){
			
			//����������ùؼ��֣���ֱ�ӷ���
			if (!contains(key)){
				return false;
			}
			
			//�������Ҷ�ӽڵ����Ǹ��ڵ㣬ֱ��ɾ��
			if (isRoot) {
				if(remove(key)){
					foud = true;
				}
			}else {
				//����ؼ���������M / 2��ֱ��ɾ��
				if (entries.size() > tree.getOrder() / 2 && entries.size() > 2) {
					if(remove(key)){
						foud = true;
					}
				}else {
					//�������ؼ�����С��M / 2������ǰ�ڵ�ؼ���������M / 2������䴦�貹
					if (previous != null 
							&& previous.getEntries().size() > tree.getOrder() / 2
							&& previous.getEntries().size() > 2
							&& previous.getParent() == parent) {
						int size = previous.getEntries().size();
						Entry<Comparable, Object> entry = previous.getEntries().get(size - 1);
						previous.getEntries().remove(entry);
						//��ӵ���λ
						entries.add(0, entry);
						if(remove(key)){
							foud = true;
						}
					//�������ؼ�����С��M / 2�����Һ�ڵ�ؼ���������M / 2������䴦�貹	
					}else if (next != null 
							&& next.getEntries().size() > tree.getOrder() / 2
							&& next.getEntries().size() > 2
							&& next.getParent() == parent) {
						Entry<Comparable, Object> entry = next.getEntries().get(0);
						next.getEntries().remove(entry);
						//��ӵ�ĩβ
						entries.add(entry);
						if(remove(key)){
							foud = true;
						}
					//������Ҫ�ϲ�Ҷ�ӽڵ�	
					}else {
						//ͬǰ��ڵ�ϲ�
						if (previous != null 
								&& (previous.getEntries().size() <= tree.getOrder() / 2 || previous.getEntries().size() <= 2)
								&& previous.getParent() == parent) {
							for (int i = previous.getEntries().size() - 1; i >=0; i--) {
								//��ĩβ��ʼ��ӵ���λ
								entries.add(0, previous.getEntries().get(i));
							}
							if(remove(key)){
								foud = true;
							}
							previous.setParent(null);
							previous.setEntries(null);
							parent.getChildren().remove(previous);
							//��������
							if (previous.getPrevious() != null) {
								Node temp = previous;
								temp.getPrevious().setNext(this);
								previous = temp.getPrevious();
								temp.setPrevious(null);
								temp.setNext(null);							
							}else {
								tree.setHead(this);
								previous.setNext(null);
								previous = null;
							}
						//ͬ����ڵ�ϲ�	
						}else if(next != null 
								&& (next.getEntries().size() <= tree.getOrder() / 2 || next.getEntries().size() <= 2)
								&& next.getParent() == parent){
							for (int i = 0; i < next.getEntries().size(); i++) {
								//����λ��ʼ��ӵ�ĩβ
								entries.add(next.getEntries().get(i));
							}
							if(remove(key)){
								foud = true;
							}
							next.setParent(null);
							next.setEntries(null);
							parent.getChildren().remove(next);
							//��������
							if (next.getNext() != null) {
								Node temp = next;
								temp.getNext().setPrevious(this);
								next = temp.getNext();
								temp.setPrevious(null);
								temp.setNext(null);
							}else {
								next.setPrevious(null);
								next = null;
							}
						}
					}
				}
				parent.updateRemove(tree);
			}
		//�������Ҷ�ӽڵ�	
		}else {
			//���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ��������
			if (key.compareTo(entries.get(0).getKey()) <= 0) {
				if(children.get(0).remove(key, tree)){
					foud = true;
				}
			//���key���ڽڵ����ұߵ�key�������һ���ӽڵ��������
			}else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
				if(children.get(children.size()-1).remove(key, tree)){
					foud = true;
				}
			//�����ر�key���ǰһ���ӽڵ��������
			}else {
				for (int i = 0; i < entries.size(); i++) {
					if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) {
						if(children.get(i).remove(key, tree)){
							foud = true;
						}
					}
				}
			}
		}
		if(foud){
			return true;
		}else{
			return false;
		}
	}
	
	/** �жϵ�ǰ�ڵ��Ƿ�����ùؼ���*/
	protected boolean contains(Comparable key) {
		for (Entry<Comparable, Object> entry : entries) {
			if (entry.getKey().compareTo(key) == 0) {
				return true;
			}
		}
		return false;
	}
	
	/** ���뵽��ǰ�ڵ�Ĺؼ�����*/
	protected void insertOrUpdate(Comparable key, Object obj){
		Entry<Comparable, Object> entry = new SimpleEntry<Comparable, Object>(key, obj);
		//����ؼ����б���Ϊ0����ֱ�Ӳ���
		if (entries.size() == 0) {
			entries.add(entry);
			return;
		}
		//��������б�
		for (int i = 0; i < entries.size(); i++) {
			//����ùؼ��ּ�ֵ�Ѵ��ڣ������
			if (entries.get(i).getKey().compareTo(key) == 0) {
				entries.get(i).setValue(obj);
				return;
			//�������	
			}else if (entries.get(i).getKey().compareTo(key) > 0){
				//���뵽����
				if (i == 0) {
					entries.add(0, entry);
					return;
				//���뵽�м�
				}else {
					entries.add(i, entry);
					return;
				}
			}
		}
		//���뵽ĩβ
		entries.add(entries.size(), entry);
	}
	
	/** ɾ���ڵ�*/
	protected boolean remove(Comparable key){
		int index = -1;
		boolean foud = false;
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getKey().compareTo(key) == 0) {
				index = i;
				foud = true;
				break;
			}
		}
		if (index != -1) {
			entries.remove(index);
		}
		if(foud){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Entry<Comparable, Object>> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry<Comparable, Object>> entries) {
		this.entries = entries;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("isRoot: ");
		sb.append(isRoot);
		sb.append(", ");
		sb.append("isLeaf: ");
		sb.append(isLeaf);
		sb.append(", ");
		sb.append("keys: ");
		for (Entry entry : entries){
			sb.append(entry.getKey());
			sb.append(", ");
		}
		sb.append(", ");
		return sb.toString();
		
	}

}

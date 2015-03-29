package com.bh.contacts.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class ItemListAdapter<T> extends ArrayAdapter<T> {
	private LayoutInflater inflater;

	private final ItemBinder<T> binder;

	private final int itemLayoutId;

	public ItemListAdapter(Context context, List<T> items, int itemLayoutId, ItemBinder<T> binder) {
		super(context, 0, items);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.itemLayoutId = itemLayoutId;
		this.binder = binder;
	}
	
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		T item = getItem(position);
		
		if(convertView == null){
			convertView = inflater.inflate(itemLayoutId, parent, false);
		}
		
		if(binder != null){
			binder.bind(position, convertView, parent, item);
		}
				
		return convertView;
	}

	
	public interface ItemBinder<T> {
		public void bind(int position, View convertView, ViewGroup parent, T item);
	}
}

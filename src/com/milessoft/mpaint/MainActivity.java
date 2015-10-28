package com.milessoft.mpaint;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private boolean isMore = false;// menu�˵���ҳ����

	AlertDialog menuDialog;// menu�˵�Dialog
	GridView menuGrid;
	View menuView;
	private final int ITEM_Clear = 0;// ����
	private final int ITEM_Color = 1;// �ļ�����
	private final int ITEM_Add_Stroke = 2;// ���ع���
	private final int ITEM_Minus_Stroke = 3;// ȫ��
	private final int ITEM_MORE = 11;// �˵�
	/** �˵�ͼƬ **/
	int[] menu_image_array = { R.drawable.menu_delete,
			R.drawable.menu_color, R.drawable.menu_downmanager,
			R.drawable.menu_fullscreen, R.drawable.menu_inputurl,
			R.drawable.menu_bookmark, R.drawable.menu_bookmark_sync_import,
			R.drawable.menu_sharepage, R.drawable.menu_quit,
			R.drawable.menu_nightmode, R.drawable.menu_refresh,
			R.drawable.menu_more };
	/** �˵����� **/
	String[] menu_name_array = { "���", "��ɫ", "+", "-", "��ַ", "��ǩ",
			"������ǩ", "����ҳ��", "�˳�", "ҹ��ģʽ", "ˢ��", "����" };
	/** �˵�ͼƬ2 **/
	int[] menu_image_array2 = { R.drawable.menu_auto_landscape,
			R.drawable.menu_penselectmodel, R.drawable.menu_page_attr,
			R.drawable.menu_novel_mode, R.drawable.menu_page_updown,
			R.drawable.menu_checkupdate, R.drawable.menu_checknet,
			R.drawable.menu_refreshtimer, R.drawable.menu_syssettings,
			R.drawable.menu_help, R.drawable.menu_about, R.drawable.menu_return };
	/** �˵�����2 **/
	String[] menu_name_array2 = { "�Զ�����", "��ѡģʽ", "�Ķ�ģʽ", "���ģʽ", "��ݷ�ҳ",
			"������", "�������", "��ʱˢ��", "����", "����", "����", "����" };

	private SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		 setContentView(R.layout.activity_main);
		final BrushView view = new BrushView(this);
		setContentView(view);
		
		menuView = View.inflate(this, R.layout.gridview_menu, null);
		// ����AlertDialog
		menuDialog = new AlertDialog.Builder(this).create();
		menuDialog.setView(menuView);
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// ��������
					dialog.dismiss();
				return false;
			}
		});

		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		/** ����menuѡ�� **/
		menuGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case ITEM_Clear:// ���
					view.path.reset();
					// invalidate the view
					view.postInvalidate();
					break;
				case ITEM_Color:// ��ɫ
					view.chooseColor(MainActivity.this);
					break;
				case ITEM_Add_Stroke:// ���ع���
					view.addStrokeWidth();
					break;
				case ITEM_Minus_Stroke:// ȫ��
					view.minusStrokeWidth();	
					break;
				case ITEM_MORE:// ��ҳ
					if (isMore) {
						menuGrid.setAdapter(getMenuAdapter(menu_name_array2,
								menu_image_array2));
						isMore = false;
					} else {// ��ҳ
						menuGrid.setAdapter(getMenuAdapter(menu_name_array,
								menu_image_array));
						isMore = true;
					}
					menuGrid.invalidate();// ����menu
					menuGrid.setSelection(ITEM_MORE);
					break;
				}

			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu11");// ���봴��һ��
		return super.onCreateOptionsMenu(menu);
	}
	@Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menuDialog == null) {
            menuDialog = new AlertDialog.Builder(this).setView(menuView).show();
        } else {
            menuDialog.show();
        }
        return false;// ����Ϊtrue ����ʾϵͳmenu
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}
}

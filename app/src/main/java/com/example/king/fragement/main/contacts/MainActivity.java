package com.example.king.fragement.main.contacts;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.king.fragement.R;
import com.example.king.fragement.main.contacts.adapter.SortAdapter;
import com.example.king.fragement.main.contacts.model.ContactUser;
import com.example.king.fragement.main.contacts.model.SortModel;
import com.example.king.fragement.main.contacts.util.PinYinKit;
import com.example.king.fragement.main.contacts.util.PinyinComparator;
import com.example.king.fragement.main.contacts.views.SearchEditText;
import com.example.king.fragement.main.contacts.views.SideBar;

public class MainActivity extends AppCompatActivity
{
	public PinyinComparator comparator = new PinyinComparator();
	private static final String[] PHONES_PROJECTION = new String[] {
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };
	private ImageView groupImg;
	private ImageView backImg;
	private TextView userListNumTxt;
	private String userListNumStr;
	
	private SideBar sideBar;
	private ListView sortListView;
	private TextView dialogTxt;
	private SearchEditText mSearchEditText;
	private SortAdapter adapter;
	private ContactUser user;
	private List<SortModel> sortModelList;

	/**联系人显示名称**/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/**电话号码**/
	private static final int PHONES_NUMBER_INDEX = 1;

	/**头像ID**/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/**联系人的ID**/
	private static final int PHONES_CONTACT_ID_INDEX = 3;


	/**联系人名称**/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/**联系人头像**/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	/**联系人头像**/
	private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_contacts);
		this.userListNumTxt = (TextView) findViewById(R.id.txt_user_list_user_num);

		Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
// wrong
		setSupportActionBar(bar);
        /*
        * 标题栏解释
        * */
		bar.setTitle("Contacts");
        /*
        * 左上方返回到主界面
        * */
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		sideBar = (SideBar) findViewById(R.id.sild_bar);
		dialogTxt = (TextView) findViewById(R.id.txt_dialog);
		sideBar.setmTextDialog(dialogTxt);
		
		// on touching listener of side bar
		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener()
		{
			
			public void onTouchingLetterChanged(String str)
			{
				int position =  adapter.getPositionForSection(str.charAt(0));
				if (position != -1)
					sortListView.setSelection(position);
			}
		});
		
		sortListView = (ListView) findViewById(R.id.list_view_user_list);
		sortListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		sortListView.setOnItemClickListener(new OnItemClickListener()
		{
	
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id)
			{
				Intent it = new Intent();
				it.setClass(MainActivity.this,DetailActivity.class);
				it.putExtra("name",((SortModel)adapter.getItem(position)).getName());
				it.putExtra("number",((SortModel)adapter.getItem(position)).getInfo());
				startActivity(it);
//				Toast.makeText(getApplicationContext(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
			}
		});
		
		
		// call filledData to get datas
		try
		{
			//TODO  ��contacts����ȡ��name�ŵ�����
//			sortModelList =  filledData(getResources().getStringArray(R.array.date));
			getPhoneContacts();
			sortModelList =  filledData(mContactsName,mContactsNumber);
		} catch (BadHanyuPinyinOutputFormatCombination e1)
		{
			e1.printStackTrace();
		}

		userListNumTxt.setText("全部："+"\t"+sortModelList.size()+"个联系人");
		
		// sort by a-z
		Collections.sort(sortModelList, comparator);
		adapter = new SortAdapter(getApplicationContext(), sortModelList);
		sortListView.setAdapter(adapter);
		
		
		// search 
		mSearchEditText = (SearchEditText) findViewById(R.id.txt_filter_edit);
		
		// filter 
		mSearchEditText.addTextChangedListener(new TextWatcher()
		{
			
			public void onTextChanged(CharSequence str, int arg1, int arg2, int arg3)
			{
				try
				{
					filerData(str.toString());
				} 
				catch (BadHanyuPinyinOutputFormatCombination e)
				{
					e.printStackTrace();
				}
			}
			
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
			}
			
			public void afterTextChanged(Editable arg0)
			{
			}
		});
	}

	private List<SortModel> filledData(String [] date) throws BadHanyuPinyinOutputFormatCombination{
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//����ת����ƴ��
			String pinyin = PinYinKit.getPingYin(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	private List<SortModel> filledData(List<String> name,List<String> number) throws BadHanyuPinyinOutputFormatCombination{
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for(int i=0; i<name.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(name.get(i));
			sortModel.setInfo(number.get(i));
			//����ת����ƴ��
			String pinyin = PinYinKit.getPingYin(name.get(i));
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	private void filerData(String str) throws BadHanyuPinyinOutputFormatCombination
	{
		List<SortModel> fSortModels = new ArrayList<SortModel>();
		
		if (TextUtils.isEmpty(str))
			fSortModels = sortModelList;
		else 
		{
			fSortModels.clear();
			for (SortModel sortModel : sortModelList)
			{
				String name = sortModel.getName();
				if (name.indexOf(str.toString()) != -1 || 
						PinYinKit.getPingYin(name).startsWith(str.toString()) || PinYinKit.getPingYin(name).startsWith(str.toUpperCase().toString()))
				{
					fSortModels.add(sortModel);
				}
			}
			
		}
		Collections.sort(fSortModels, comparator);
		adapter.updateListView(fSortModels);
	}
	
	public void changeDatas(List<SortModel> mSortList , String str)
	{
		userListNumTxt.setText(str+"��"+"\t"+mSortList.size()+"����ϵ��");
		
		Collections.sort(mSortList, comparator);
		adapter.updateListView(mSortList);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private void getPhoneContacts() {
		ContentResolver resolver = getContentResolver();

// 获取手机联系人
		Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);


		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				//得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				//当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;

				//得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

				//得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

				//得到联系人头像ID
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

				//得到联系人头像Bitamp
				Bitmap contactPhoto = null;

				//photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				if(photoid > 0 ) {
					Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);
					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				}else {
					contactPhoto = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
				}

				mContactsName.add(contactName);
				mContactsNumber.add(phoneNumber);
				mContactsPhonto.add(contactPhoto);
			}

			phoneCursor.close();
		}
	}

}

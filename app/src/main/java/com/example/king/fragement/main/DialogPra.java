package com.example.king.fragement.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.fragement.R;


public class DialogPra extends Activity {
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);

		button1 = (Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				LogWrap.d("点击了");
				final TextView float_letter = (TextView) findViewById(R.id.float_letter);
				final SlideView mSlideView = new SlideView(DialogPra.this);

//				final EditText et = (EditText) findViewById(R.id.et);
//				AlertDialog dialog = new AlertDialog.Builder(DialogPra.this)
//						.create();
//				dialog.setTitle("Test");
//				dialog.setMessage("Message");
//				dialog.setButton("positive", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//					}
//				});
//				dialog.show();
				/*
				* 第二种写法，没那么繁琐
				* */
//				new AlertDialog.Builder(DialogPra.this)
//						.setMessage("Message")
//						.setTitle("Title")
//						.setIcon(android.R.drawable.btn_star)
//						.setPositiveButton("positive", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								LogWrap.d("positive", String.valueOf(which));
//							}
//						})
//						.setNeutralButton("neutral", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								Log.d("neutral", String.valueOf(which));
//							}
//						})
//						.setNegativeButton("nagative", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								Log.d("negative", String.valueOf(which));
//							}
//						})
//
//				.create()
//						.show()
//				;
				/*
				* 这里必须要layoutinflater
				* 否则会直接从layout.main中招lv这个所以找不到所以是null pointer
				* 同理rl也找不到，只不过不会nullpointer，是直接不显示界面
				* */
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.dialog_layout,null);
				LinearLayout rl = (LinearLayout) view.findViewById(R.id.rl);
				final ListView listView = (ListView) view.findViewById(R.id.lv);
//				LinearLayout linearLayoutMain = new LinearLayout(DialogPra.this);//自定义一个布局文件
//				linearLayoutMain.setLayoutParams(new AbsListView.LayoutParams(
//						AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
//				final ListView listView = new ListView(DialogPra.this);//this为获取当前的上下文
//				listView.setFadingEdgeLength(0);

				final List<Map<String, String>> nameList = new ArrayList<Map<String,String>>(Cheeses.sCheeseStrings.length);//建立一个数组存储listview上显示的数据
				for (int m = 0; m < Cheeses.sCheeseStrings.length; m++)
//				for (int m = 0; m <nameList.size(); m++)
				{
					Map<String,String> nameMap = new HashMap<String, String>();
					nameMap.put("name", Cheeses.sCheeseStrings[m]);
					nameMap.put("info", "Jarlsberg lancashire edam. Dolcelatte hard cheese brie st. agur blue\n" +
							"    cheese caerphilly bavarian bergkase cheese and biscuits mascarpone. Cheeseburger swiss bavarian\n" +
							"    bergkase cream cheese fromage frais cheesy feet port-salut airedale. St. agur blue cheese rubber\n" +
							"    cheese caerphilly cheddar cheesecake cream cheese manchego lancashire. Roquefort squirty cheese\n" +
							"    the big cheese.");
					nameList.add(nameMap);
//					nameList.add(nameMap1);
				}

//				Map<String,String> nameMap = new HashMap<String, String>();
//				nameMap.put("name", Cheeses.sCheeseStrings[0]);
//				nameMap.put("info", "Jarlsberg lancashire edam. Dolcelatte hard cheese brie st. agur blue\n" +
//						"    cheese caerphilly bavarian bergkase cheese and biscuits mascarpone. Cheeseburger swiss bavarian\n" +
//						"    bergkase cream cheese fromage frais cheesy feet port-salut airedale. St. agur blue cheese rubber\n" +
//						"    cheese caerphilly cheddar cheesecake cream cheese manchego lancashire. Roquefort squirty cheese\n" +
//						"    the big cheese.");
//				nameList.add(nameMap);
				SimpleAdapter adapter = new SimpleAdapter(DialogPra.this,
						nameList, R.layout.simpleadapter,
						new String[] { "name" ,"info" },
						new int[]{R.id.name,R.id.value});
//				SimpleAdapter adapter = new SimpleAdapter(DialogPra.this,
//						nameList, R.layout.activity_detail,
//						new String[] { "name" },
//						new int[]{R.id.info});
				listView.setAdapter(adapter);

//				linearLayoutMain.addView(listView);//往这个布局中加入listview
//				linearLayoutMain.addView(mSlideView);//往这个布局中加入listview

				final AlertDialog dialog = new AlertDialog.Builder(DialogPra.this,AlertDialog.THEME_HOLO_LIGHT)
						.setTitle("选择患者姓名").setView(rl)//在这里把写好的这个listview的布局加载dialog中
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						}).create();
				dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
				Window window = dialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.alpha = 0.8f;
				window.setAttributes(lp);
				dialog.show();
				mSlideView
						.setOnTouchLetterChangeListenner(new SlideView.OnTouchLetterChangeListenner() {

							@Override
							public void onTouchLetterChange(MotionEvent event, String s) {

								float_letter.setText(s);
								switch (event.getAction()) {
									case MotionEvent.ACTION_DOWN:
									case MotionEvent.ACTION_MOVE:
										float_letter.setVisibility(View.VISIBLE);
										break;

									case MotionEvent.ACTION_UP:
									default:
										float_letter.postDelayed(new Runnable() {

											@Override
											public void run() {
												float_letter.setVisibility(View.GONE);
											}
										}, 100);
										break;
								}
								int position = nameList.indexOf(s);//这个array就是传给自定义Adapter的
								listView.setSelection(position);//调用ListView的setSelection()方法就可实现了
							}
						});
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件
//加入et后不能生效
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
											long arg3) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) arg1
								.findViewById(R.id.name);//取得每条item中的textview控件
						TextView tv1 = (TextView) arg1
								.findViewById(R.id.value);//取得每条item中的textview控件
//						et.setText(tv.getText().toString());
						Toast.makeText(DialogPra.this,tv.getText().toString()+tv1.getText().toString(),Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});

				;

			}
		});

		// ////////////////////

		button2 = (Button) this.findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

//				AlertDialog dialog = new AlertDialog.Builder(DialogPra.this).create();
//				dialog.setMessage("������Ϣ����");
//				dialog.setIcon(android.R.drawable.btn_star);
//				dialog.setTitle("����");
//				dialog.setButton("ȷ��", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface arg0, int arg1) {
//						Log.v("-----------", "�����2����ť�Ի����е�ȷ��");
//					}
//				});
//				dialog.setButton2("ȡ��", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface arg0, int arg1) {
//						Log.v("-----------", "�����2����ť�Ի����е�ȡ��");
//					}
//				});
//				dialog.show();
				AlertDialog dialog = new AlertDialog.Builder(DialogPra.this)
						.create();
				dialog.setTitle("Title");
				dialog.setIcon(android.R.drawable.btn_star);
				dialog.setMessage("Message");
				dialog.setIcon(android.R.drawable.btn_star);
				dialog.setButton("positive", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				dialog.setButton3("cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				dialog.setButton2("meutral", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
//				点击对话框外不可以取消
				dialog.setCanceledOnTouchOutside(false);

				dialog.show();
			}
		});

		// ////////////////////

		button3 = (Button) this.findViewById(R.id.button3);
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

//				LayoutInflater inflater = DialogPra.this.getLayoutInflater();
//				View twoEditTextLayoutRef = inflater.inflate(
//						R.layout.dialogtwoedittext, null);
//
//				final EditText editText1 = (EditText) twoEditTextLayoutRef
//						.findViewById(R.id.editText1);
//				final EditText editText2 = (EditText) twoEditTextLayoutRef
//						.findViewById(R.id.editText2);
//				editText1.setText("username1");
//				editText2.setText("username2");
//
//				AlertDialog dialog = new AlertDialog.Builder(DialogPra.this).create();
//				dialog.setView(twoEditTextLayoutRef);
//				dialog.setTitle("����");
//				dialog.setIcon(android.R.drawable.btn_star);
//
//				dialog.setButton("ȡֵ", new DialogInterface.OnClickListener() {
//
//					public void onClick(DialogInterface arg0, int arg1) {
//						Log.v("***********", editText1.getText().toString()
//								+ "  " + editText2.getText().toString());
//					}
//
//				});
//
//				dialog.show();
//				LayoutInflater inflater = DialogPra.this.getLayoutInflater();
//				View editText = inflater.inflate(R.layout.dialogtwoedittext, null);
//				EditText et1 = (EditText) editText.findViewById(R.id.editText1);
//				EditText et2 = (EditText) editText.findViewById(R.id.editText2);
//				et1.setHint("username");
//				et2.setHint("username");
//				AlertDialog dialog = new AlertDialog.Builder(DialogPra.this).create();
//				dialog.setView(editText);
//				dialog.setTitle("Title");
//				dialog.setIcon(android.R.drawable.btn_star);
//				dialog.setMessage("message");
//				dialog.setIcon(android.R.drawable.btn_star);
//				dialog.setButton("positive", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//					}
//				});
//				dialog.show();
				LayoutInflater inflater = DialogPra.this.getLayoutInflater();
				View editLayout = inflater.inflate(R.layout.dialogtwoedittext, null);
				EditText et1 = (EditText) editLayout.findViewById(R.id.editText1);
				EditText et2 = (EditText) editLayout.findViewById(R.id.editText2);
				et1.setHint("username");
				et2.setHint("password");
				new AlertDialog.Builder(DialogPra.this)
						.setView(editLayout)
						.setMessage("Message")
						.setTitle("Title")
						.setIcon(android.R.drawable.btn_star)
						.setPositiveButton("positive", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								LogWrap.d( String.valueOf(which));
							}
						})
						.setNeutralButton("neutral", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								LogWrap.d( String.valueOf(which));
							}
						})
						.setNegativeButton("nagative", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								LogWrap.d( String.valueOf(which));
							}
						}).create().show();


			}
		});

		// ////////////////////

		button4 = (Button) this.findViewById(R.id.button4);
		button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

				final String[] userInfoArray = new String[] { "我是A", "我是B",
						"我是C", "我是D" };

//				AlertDialog dialog = new AlertDialog.Builder(DialogPra.this)
//						.setSingleChoiceItems(userInfoArray, 1,
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface arg0,
//											int arg1) {
//										Log.v("��ѡ����:", userInfoArray[arg1]);
//										arg0.dismiss();
//									}
//								}).create();
				AlertDialog dialog = new AlertDialog.Builder(DialogPra.this)
						.setSingleChoiceItems(userInfoArray, 0, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(DialogPra.this, "you have selected " + userInfoArray[which], Toast.LENGTH_SHORT).show();
								dialog.dismiss();
							}
						}).create();
//				不生效
				for (int i = 0; i < userInfoArray.length; i++) {
					dialog.getListView().setItemChecked(i,true);
				}
				dialog.show();
//				dialog.show();
			}
		});

		// ////////////////////

		button5 = (Button) this.findViewById(R.id.button5);
		button5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

				final String[] userInfoArray = new String[] { "我是1", "我是2",
						"我是3", "我是4", "我是5", "我是6", "我是7", "我是8", "我是9", "我是10" };

//				final AlertDialog dialog = new AlertDialog.Builder(DialogPra.this)
//						.setMultiChoiceItems(userInfoArray, null,
//								new OnMultiChoiceClickListener() {
//									public void onClick(DialogInterface arg0,
//											int arg1, boolean arg2) {
//										Log.v("zzzzzzzzzzzz", "" + arg1 + "  "
//												+ arg2);
//									}
//								}).create();
				final AlertDialog dialog = new AlertDialog.Builder(DialogPra.this).setMultiChoiceItems(userInfoArray, null, new OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {

					}
				}).create();
				boolean[] defaultValueBooleanArray = new boolean[] { true,
						false, true, false, true, false, true, false, true,
						false };
				dialog.setButton("全选", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

//						Object object = dialog.getListView().getChildAt(1);
//
//						ListView lv = dialog.getListView();
//
//						for (int i = 0; i < lv.getCount(); i++) {
//							lv.setItemChecked(i, true);
//						}
//						try {
//							Field field = dialog.getClass().getSuperclass()
//									.getDeclaredField("mShowing");
//							field.setAccessible(true);
//							// ��mShowing������Ϊfalse����ʾ�Ի����ѹر�
//							field.set(dialog, false);
//						} catch (Exception e) {
//
//						}
						Object object = dialog.getListView().getChildAt(1);
						LogWrap.d(String.valueOf(object.toString()));
						ListView lv = dialog.getListView();
						LogWrap.d(String.valueOf(lv));
						for (int i = 0; i < lv.getCount(); i++) {
							lv.setItemChecked(i,true);
						}
						try{
							Field field = dialog.getClass().getSuperclass()
									.getDeclaredField("mShowing");
							field.setAccessible(true);
							// 将mShowing变量设为false，表示对话框已关闭
							field.set(dialog,false);

						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}

					}
				});


				dialog.setButton3("反选", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

//						SparseBooleanArray sbaRef = dialog.getListView()
//								.getCheckedItemPositions();
//
//						ListView lv = dialog.getListView();
//
//						for (int i = 0; i < lv.getCount(); i++) {
//							lv.setItemChecked(i, !sbaRef.get(i));
//						}
						SparseBooleanArray sba = dialog.getListView().getCheckedItemPositions();

						ListView lv = dialog.getListView();

						for (int i = 0; i < lv.getCount(); i++) {
							lv.setItemChecked(i,!sba.get(i));
						}
						try {
//							Field field = dialog.getClass().getSuperclass()
//									.getDeclaredField("mShowing");
//							field.setAccessible(true);
//							// 将mShowing变量设为false，表示对话框不关闭
////							点击反选后不会消失对话框
//							field.set(dialog, false);
							Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
							field.setAccessible(true);
							field.set(dialog,false);
						} catch (Exception e) {

						}

					}
				});
				dialog.setButton2("positive", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

						ListView lv = dialog.getListView();

						for (int i = 0; i < lv.getCount(); i++) {
							if (lv.getCheckedItemPositions().get(i)) {
								LogWrap.v("selected"+""
										+lv.getAdapter().getItemId(i) +""
										+lv.getAdapter().getItem(i));
							}
						}

						try {
							Field field = dialog.getClass().getSuperclass()
									.getDeclaredField("mShowing");
							field.setAccessible(true);
							// 将mShowing变量设为true，表示对话框关闭
							field.set(dialog, true);
							dialog.dismiss();
						} catch (Exception e) {

						}
					}
				});

				dialog.show();
//				？为什么dialog已经show了但是现在才赋值默认值也不影响？
				for (int i = 0; i < defaultValueBooleanArray.length; i++) {
					dialog.getListView().setItemChecked(i,
							defaultValueBooleanArray[i]);
				}
			}

		});

//	auto-close dialog

		button6 = (Button) this.findViewById(R.id.button6);
		button6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

				final String[] userInfoArray = new String[] { "我是1", "我是2",
						"我是3", "我是4" };
				final AlertDialog dialog = new AlertDialog.Builder(DialogPra.this)
						.setItems(userInfoArray,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,
											int arg1) {
										LogWrap.v( userInfoArray[arg1]);
										arg0.dismiss();
									}
								}).create();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						dialog.dismiss();
					}
				},3000);




//				不生效
				for (int i = 0; i < userInfoArray.length; i++) {
					dialog.getListView().setItemChecked(i,true);
				}
				dialog.show();
			}
		});

	}
}
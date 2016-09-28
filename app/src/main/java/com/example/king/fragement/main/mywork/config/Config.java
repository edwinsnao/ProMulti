package com.example.king.fragement.main.mywork.config;

public class Config {
	//状态码
	public static final int SUCCESS = 200 ;//成功
	public static final int ERROR = 1000;//系统错误
	public static final int OUT_OF_INDEX = 500 ;//超出页码
	public static final int ACCOUNT_EXIST = 501;//账号已存在
	public static final int ACCOUNT_NOT_EXIST = 502;//账号不存在
	public static final int PWD_ERROR = 503;//密码错误
	public static final int PARAMSERROR = 100;//参数错误
	public static final int NOTENROLL = 101;//没有报名
	public static final int NONEARBY = 102;//没有定位参数
	public static final int DATEFORMATTER = 103;//日期格式错误
	public static final int OUTOFDATE = 104;//开始时间不能超过结束时间
	public static final int DATENOTNULL = 105;//时间不能为空
	public static final int ENROLLREPEAT = 106;//重复报名
	public static final int SUREREPEAT = 106;//重复确认
	public static final int ACTIVITYNOEXIST = 107;//活动不存在
	public static final int CANNOTCANCEL = 108;//不能取消
	public static final int ENLISTSQUALIFICATION = 109;//没有报名资格
	public static final int ENROLLFINISH = 110;//活动已结束报名
	public static final int NOTCOLLECTIONBYMYSELF = 111;//不能收藏自己创建的活动
	public static final int BREAKRULE = 112;//不符合规则
	public static final int NOTCREATER = 113;//不是创建者
	public static final int NOTCOLLECTION = 114;//没有收藏活动
	public static final int NOTUNLIKE = 115;//不能不喜欢
	public static final int START = 116;//已经开始
	public static final int FAILURE = 117;//失败
	public static final int FINISH = 118;//结束
	public static final int LACKIMAGE = 119;//缺少图片
	//跳转请求码
	public static final int CODE_REGISTER_REQUEST = 1;

	//返回状态码
	public static final int CODE_REGISTER_RETURN = 1;


	//活动状态
	public static final int ACTIVITYSTART = 1;//活动开始阶段
	public static final int  REGISTRATIONPHASE = 0;//报名阶段
	public static final int ACTIVITYFAILURE = 2; //活动失败
	public static final int ACTIVITYFINISH =3;//活动结束

	public static final int TRUE = 1;
	public static final int FALSE = 0;

	//活动参与状态
	public static final int CREATE = 0;//创建者
	public static final int NOENROLL = 1;//没有报名
	public static final int ENROLL = 2;//已报名
	public static final int PASS = 3;//已通过


	//分页
	public static final int ACTIVITY_PAGE = 20 ;

	//活动类型,付款方式
	public static final int PAY_ME = 1;
	public static final int PAY_YOU = 2;
	public static final int PAY_WE = 3;

	public static String head_auto = "http://image-social.oss-cn-qingdao.aliyuncs.com/head1.jpg";

	//版本更新对应的5种状态，在Message使用
	public static final int UPDATA_NONEED = 0;
	public static final int UPDATA_CLIENT = 1;
	public static final int GET_UNDATAINFO_ERROR = 2;
	public static final int SDCARD_NOMOUNTED = 3;
	public static final int DOWN_ERROR = 4;

	//sms错误码
	public static final int signature = 455;//签名无效	签名检验
	public static final int phone_null= 456;//手机号码为空	提交的手机号码或者区号为空。
	public static final int phone_format_error = 457;//手机号码格式错误	提交的手机号格式不正确（包括手机的区号）。
	public static final int phone_in_blacklist = 458;//手机号码在黑名单中	手机号码在发送黑名单中。
	public static final int area_not_support = 461;//不支持该地区发送短信	没有开通给当前地区发送短信的功能。
	public static final int exceed_minute = 462;//每分钟发送次数超限	每分钟发送短信的数量超过限制。
	public static final int exceed_day = 465;//号码在App中每天发送短信的次数超限	手机号码在APP中每天发送短信的数量超限。
	public static final int code_null = 466;//校验的验证码为空	提交的校验验证码为空。
	public static final int code_frequently = 467;//校验验证码请求频繁	5分钟内校验错误超过3次，验证码失效。
	public static final int code_error = 468;//需要校验的验证码错误	用户提交校验的验证码错误。
	public static final int no_money = 470;//账户余额不足	账户的短信余额不足。
	public static final int request_sms_frequently = 472;//客户端请求发送短信验证过于频繁	客户端请求发送短信验证过于频繁
	public static final int exceed_sum =  478;//当前手机号在当前应用内发送超过限额	当前手机号码在当前应用下12小时内最多可发送文本验证码5条
	public static final int sms_error = 500;//服务器内部错误	服务端程序报错。
}

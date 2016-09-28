package com.example.king.fragement.main.mywork.config;

/**
 * Created by Administrator on 2016/3/19.
 */
public class Constance {
    /**
     * 登录
     * 请求参数：
     * phone	账号
     * pwd		密码
     */
    public static final String LOGIN_URL = "http://121.42.202.164:8080/bph_social/userController/login";
    /**
     * 注册
     * phone			账号
     * pwd			密码
     * sms				短信
     * nickName		昵称
     * recommend		推荐人（手机号码）
     */
    public static final String REGISTER_URL = "http://121.42.202.164:8080/bph_social/userController/register";
    /**
     * banner轮播图
     */
    public static final String ACTIVEBANNER_URL = "http://121.42.202.164:8080/bph_social/activityController/selectShowBanner";
    /**
     * 活动列表
     * userId  		用户id
     * token			验证值
     * pageNo			页数，从一开始
     * time   		请求时间，页码为1时可以不发送此参数。请求时间为页码为1时的请求时间，
     * 防止重复数据，格式为yyyy-MM-dd HH:mm:ss，下拉刷新列表时更新该值
     */
    public static final String ACTIVELIST_URL = "http://121.42.202.164:8080/bph_social/activityController/activityList?pageNo=1";
    /**
     * 创建活动
     * userId	用户id
     * token	用户验证
     * title	活动标题
     * address	活动地址
     * reason	发起活动的原因
     * labels	活动标签(字符串以逗号隔开)
     * type	活动的付费方式
     * startTime	开始时间(yyyy-MM-dd)
     * endTime	结束时间(yyyy-MM-dd)
     * files		上传的图片名，直接上传图片即可，不用修改文件名
     * isFans		是否选择指定的人
     * isStranger	是否选择陌生人
     * isAll		是否选择所有人
     * limitNumber		活动限制人数
     */
    public static final String ADD_ACTIVITY_URL = "http://121.42.202.164:8080/bph_social/activityController/saveActivity";
    /**
     * 活动报名
     * activityId			活动id
     * userId			用户id
     * token			验证
     */
    public static final String ENROLL_URL = "http://121.42.202.164:8080/bph_social/activityController/enroll";
    /**
     * 查看收藏
     * userId		用户id
     * token		验证
     * pageNo		页码
     */
    public static final String SELECT_COLLECTION_URL = "http://121.42.202.164:8080/bph_social/activityController/selectCollection";
    /**
     * 查询活动报名列表
     * userId		用户id
     * token		验证
     * activityId	活动id
     * pageNo		页码，从一开始
     * time		分页的时间点（规则同上）
     */
    public static final String SELECT_ENROLL_URL = "http://121.42.202.164:8080/bph_social/activityController/selectEnroll";
    /**
     * 通过活动报名请求
     * userId		用户id
     * token		验证
     * activityId	活动id
     * userIdPass	通过的用户id
     */
    public static final String PASS_ACTIVITY_ENROLL_URL = "http://121.42.202.164:8080/bph_social/activityController/passActivityEnroll";
    /**
     * 查看允许参加活动的用户列表
     * userId		用户Id
     * token		验证
     * activityId	活动id
     * pageNo		页码
     */
    public static final String SELECT_PASS_PAGE_URL = "http://121.42.202.164:8080/bph_social/activityController/selectPassPage";
    /**
     * 取消活动报名
     * userId		用户id
     * token		验证
     * activityId	活动id
     */
    public static final String CANCEL_ENROLL_URL = "http://121.42.202.164:8080/bph_social/activityController/cancelEnroll";
    /**
     * 添加收藏
     * userId		用户id
     * token		验证
     * activityId	活动Id
     */
    public static final String ADD_COLLECTION_URL = "http://121.42.202.164:8080/bph_social/activityController/addCollection";
    /**
     * 取消收藏
     * userId		用户id
     * token		验证
     * activityId	活动id
     */
    public static final String CANCEL_COLLECTION_URL = "http://121.42.202.164:8080/bph_social/activityController/cancelCollection";
    /**
     * 添加不感兴趣的活动
     * userId		用户id
     * token	验证
     * activityId	活动id
     */
    public static final String ADD_UNLIKE_URL = "http://121.42.202.164:8080/bph_social/activityController/addUnlike";
    /**
     * 开始活动
     * userId		用户id
     * token	验证
     * activityId	活动id
     */
    public static final String START_ACTIVITY_URL = "http://121.42.202.164:8080/bph_social/activityController/startActivity";
    /**
     * 查看自己发布的活动
     * userId		用户id
     * token	验证
     * pageNo	页码
     */
    public static final String MY_PUBLISH_ACTIVITY_URL = "http://121.42.202.164:8080/bph_social/activityController/myPublishActivity";
    /**
     * 查看我参加的活动
     * userId		用户id
     * token	验证
     * pageNo	页码
     * pageTime	当前页码的时间
     */
    public static final String MY_JOIN_URL = "http://121.42.202.164:8080/bph_social/activityController/myJoinActivity";
    /**
     * 点击活动时添加活动人气
     * userId		用户id
     * token	验证
     * activityId	活动id
     */
    public static final String POPULARITY_URL = "http://121.42.202.164:8080/bph_social/activityController/popularity";
    /**
     * 取消参与活动的人(单个取消)
     * userId		用户id
     * targetId	目标id
     * cancelUserId	活动id
     */
    public static final String CANCEL_PASS_URL = "http://121.42.202.164:8080/bph_social/activityController/cancelPassActivity";
}

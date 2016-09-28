package com.example.king.fragement;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCache
//  extends android.support.multidex.MultiDexApplication
{
  private Map<String, SoftReference<List<Map<String, Object>>>> cacheDoc;
  
  public HashMap<String, SoftReference<List<Map<String, Object>>>> cache()
  {
    return new HashMap();
  }
  
  public Map<String, SoftReference<List<Map<String, Object>>>> getCacheDoc()
  {
    return this.cacheDoc;
  }
  
  public void onCreate()
  {
//    super.onCreate();
    this.cacheDoc = cache();
  }
  
  public void setCacheDoc(Map<String, SoftReference<List<Map<String, Object>>>> paramMap)
  {
    this.cacheDoc = paramMap;
  }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\MyCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
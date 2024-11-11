package car;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import java.util.HashMap;	
public class BaseModel {
    //设置APPID/AK/SK /*替换为自己的*/  去"百度智能云"注册
    public static final String APP_ID = "454352324124";
    public static final String API_KEY = "fdvcddsvd42343sdddw23sdwd";
    public static final String SECRET_KEY = "4f2v89wedwv79frecssqws8080da";
	public static String path = "";   //初始化随便选一个路径
				    
	// 初始化一个AipOcr		   
	public static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
	
	public static String licensePlate( String path) {
		  // 传入可选参数调用接口		        
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("multi_detect", "true");//设置键值对
		// 参数path为本地图片路径
		JSONObject res = client.plateLicense(path, options); 
		return(res.toString());
		}				
	}
	
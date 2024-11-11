package car;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import java.util.HashMap;	
public class BaseModel {
    //设置APPID/AK/SK /*替换为自己的*/  去"百度智能云"注册
	public static final String APP_ID = "56513099877fff";
	public static final String API_KEY = "dshjkdn78dsa9d7vd96a97ds9ad";
	public static final String SECRET_KEY = "679d6as9d6a6d769asd7v9zdsv8sd0v7sv077vsd";
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
	
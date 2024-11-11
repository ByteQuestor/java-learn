package car;
	import com.baidu.aip.ocr.AipOcr;
	import org.json.JSONObject;

	import java.util.HashMap;
		/**
		 * 图像识别sdk
		 */
	public class BaseModel001 {
		    //设置APPID/AK/SK  替换为自己的 去"百度智能云"注册
			public static final String APP_ID = "56513099877fff";
			public static final String API_KEY = "dshjkdn78dsa9d7vd96a97ds9ad";
			public static final String SECRET_KEY = "679d6as9d6a6d769asd7v9zdsv8sd0v7sv077vsd";
		    public static String path = "D:\\6.jpg";   //初始化随便选一个路径
		    // 初始化一个AipOcr
		    public static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);


		    public static void main(String[] args) {
		        client.setConnectionTimeoutInMillis(2000);//建立链接的超时时间（毫秒）
		        client.setSocketTimeoutInMillis(60000);//通过打开的链接传输数据的超时时间（毫秒）
		        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
		        // 也可以直接通过jvm启动参数设置此环境变量
//		        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

		        System.out.println("身份证***********************************");
		        idCard("car//1.jpg");

		        System.out.println("车牌***********************************");
		        licensePlate("car/2.png");

		    }



		    public static String idCard(String path) {
		        // 传入可选参数调用接口
		        HashMap<String, String> options = new HashMap<String, String>();
		        options.put("detect_direction", "true");
		        options.put("detect_risk", "false");

		        String idCardSide = "back";

		        // 参数为本地图片路径
//		        String image = "";
		        JSONObject res = client.idcard(path, idCardSide, options);
		        System.out.println(res.toString(2));
		        return res.toString(2);
		    }



		    public static String licensePlate( String path) {
		        // 传入可选参数调用接口
		        HashMap<String, String> options = new HashMap<String, String>();
		        options.put("multi_detect", "true");//是否检测多张车牌，默认为false，当置为true的时候可以对一张图片内的多张车牌进行识别

		        // 参数为本地图片路径
		        JSONObject res = client.plateLicense(path, options);
		        System.out.println(res.toString(2));
		        return res.toString(2);

		    }



		}
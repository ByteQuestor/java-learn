package car;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import java.util.HashMap;	
public class BaseModel {
    //����APPID/AK/SK /*�滻Ϊ�Լ���*/  ȥ"�ٶ�������"ע��
	public static final String APP_ID = "56513099877fff";
	public static final String API_KEY = "dshjkdn78dsa9d7vd96a97ds9ad";
	public static final String SECRET_KEY = "679d6as9d6a6d769asd7v9zdsv8sd0v7sv077vsd";
	public static String path = "";   //��ʼ�����ѡһ��·��
				    
	// ��ʼ��һ��AipOcr		   
	public static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
	
	public static String licensePlate( String path) {
		  // �����ѡ�������ýӿ�		        
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("multi_detect", "true");//���ü�ֵ��
		// ����pathΪ����ͼƬ·��
		JSONObject res = client.plateLicense(path, options); 
		return(res.toString());
		}				
	}
	
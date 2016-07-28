package utilities;

/**
 * Created by mourad on 6/6/2016.
 */
public class MasterAdmin {

    public static boolean signIn(String username,String password){
        String default_username = ApplicationConfiguration.getInstance().getProperties().get("admin_username");
        String default_password = ApplicationConfiguration.getInstance().getProperties().get("admin_password");

        System.out.println(default_username+"  "+default_password);
        boolean username_ok = username!=null&&default_username!=null?username.equals(default_username):false;
        boolean password_ok = password!=null&&default_password!=null?password.equals(default_password):false;

        return username_ok&&password_ok;
    }

    public static String getPassword(){
        return ApplicationConfiguration.getInstance().getProperties().get("admin_password");
    }

    public static boolean setPassword(String password){
        if(password==null) return false;
        ApplicationConfiguration.getInstance().setProperty("admin_password",password);
        return true;
    }
}
